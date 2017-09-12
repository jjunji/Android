# Runtime Permission

![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/Permission.PNG)

**1. Runtime Permission**

안드로이드는 마시멜로우(6.0) Api Level 23 이상 부터는 사용자가 접근 권한이 필요한 기능을 수행할 때, 사용자로 하여금 해당 권한을 앱에 허락 할 것인지 묻고,
개발자가 아닌 사용자가 자신의 디바이스의 접근 권한을 결정하는 형태로 권한 설정 구조가 바뀜.  ->  메니페스트에서 쓰는 설치권한과 다르게 소스코드로 작성해야 한다.

**2. 권한 획득 처리**

1) 권한 획득하기 전 권한 유효성 체크
```
checkSelfPermission(String) != packageManager.PERMISSION_GRANTED
```
현재 앱이 특정 권한을 갖고 있는지 확인 가능

2) 설명이 필요할 경우 처리
```
shouldShowRequestPermissionRationale(String)
```
* 안해줘도 되는데 사용자가 필요한 권한에 대해 설명을 해주는 것.
* 권한 획득이 필요한 이유를 설명해야 한다면 다음 옵션을 추가하여 별도 처리가 가능.
* 사용자가 이전에 권한 요청을 거부한 경우에 true 반환.
* 이 경우, 권한 요청을 위한 대화창에는 '다시 묻지 않기' 체크박스와 함께 표시됨.
* 사용자가 이를 선택하면 이후에 앱이 requestPermissions 메서드를 호출해도 권한 요청 대화창이 표시되지 않고, 바로 사용자가 해당 권한을 거부할 때와 동일하게 콜백 함수가 호출된다.

3) 권한 획득을 위한 API
```
Activity.requestPermissions(String[], int)
```
위의 권한 중 Group과 permission 2가지를 선택적으로 던질 수 있다.
한번에 1개가 아닌 String[] 배열로 넘겨 한 번에 필요한 permission을 한 번에 획득할 수 있다.

4) 결과처리
```
onRequestPermissionResult(int, String[], int[])
```
* 3)에서 사용자에게 권한획득에 대해 물어본 다음에 그 결과에 대해 사용자가 승인 혹은 거절에 대해서 응답을 하면 결과처리 메소드(onRequestPermissionResult)가 실행이 된다.
* 권한 획득에 대항 성공/실패에 대한 정보를 담은 callback.
* 함수 내에서 배열로 전달되므로 필요한 퍼미션이 잘 받아졌는지 확인하여 이후 처리가 가능.
 
***
CheckPermissionActivity
```java
 @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        // 1. 권한 체크 - 특정 권한이 있는지 시스템에 물어본다.                         // 상수로 정의되어있음.
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }else{
            // 2. 권한이 없으면 사용자에게 권한을 달라고 요청
            String permissions[] = {Manifest.permission.READ_CONTACTS};
            requestPermissions(permissions,100); // -> 권한을 요구하는 팝업이 사용자 화면에 나타남.
        }
    }
```
requestPermissions -> 배열 형태로 전달(여러개인 경우도 있기 때문)

100 : 리퀘스트 코드를 체크해서 어떤 요청 후에 결과 처리인지 판단.

***
requestPermissions 호출 후에 호출되는 함수

```java
    // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
    @Override                                   // 100
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            // 3.1 사용자가 승인을 했음
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                run();
                // 3.2 사용자가 거절 했음
            } else {
                cancel();
            }
        }
    }
```
요청 결과에 따라 수행할 각각의 메소드 정의.
```java
    public void run(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void cancel(){
        Toast.makeText(this, "권한요청을 승인하셔야 앱을 사용할 수 있습니다.",Toast.LENGTH_SHORT).show();
        finish();
    }
```
버전 호환성 체크
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission);

        // 0. api level이 23 이상일 경우만 실행
        // Build.VERSION.SDK_INIT = 설치 안드로이드폰의 api level 가져오기
        // build
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }
        // 아니면 그냥 run
        run();
    }
```
**권한처리 순서**

권한을 체크 하고,
권한 체크 후에 권한에 대한 사용자 응답을 받은 후 특정 액티비티가 실행되면,
그 액티비티에서 최종 결과처리를 해주는 형태.

***
# ContentProvider & ContentResolver 

![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/ContentProvider_Resolver.PNG)

안드로이드는 다른 어플리케이션에서 특정 어플리케이션의 데이터베이스에 직접 접근하는 것은 불가능.
그래서 컨텐트 프로바이더(Content Provider)를 사용한다.

**ContentProvider**

어플리케이션 내의 데이터베이스를 다른 어플리케이션이 사용할 수 있는 통로를 제공한다.

**ContentResolver**

컨텐트 프로바이더를 사용하여 안드로이 시스템의 각종 설정값이나 미디어 등에 접근 가능해지는데, 이 컨텐트 프로바이더에 접근하기 위해서 사용하는 것이 컨텐트 리졸버이다.

컨텐트 리졸버는 컨텐트 프로바이더의 주소를 통해 해당 컨텐트 프로바이더에 접근하여 컨텐트 프로바이더의 데이터에 접근할 수 있도록 해주는 역할. -> 일종의 연결 다리

URI 주소만 안다면 어떤 정보라도 접근이 가능하다.
여기서 URI 는 안드로이드 시스템 자원의 주소체계를 뜻함.

```java
public List<Data> getContacts(){
        // 데이터베이스 혹은 content resolver를 통해 가져온 데이터를 적재할
        // 데이터 저장소를 먼저 정의한다.

        List<Data> datas = new ArrayList<>();  // ArrayList -> List로 쓴다는 것은 다형성을 이해하고 있다?

        // 일종의 Database 관리 툴
        // 전화번호부에 이미 만들어져 있는 Content Provider 를 통해
        // 데이터를 가져올 수 있다.

        ContentResolver resolver = getContentResolver();  // 카톡에서 주소록 가져오는 것 처럼.

        // 1. 데이터 컨텐츠 URI(자원의 주소)를 정의
        //  phonUri -> 테이블의 이름이라고 생각.
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        // 미리 정의 된 클래스

        // URI에는 여러 데이터가 들어 있기 때문에
        // 2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null); // -> 배열처럼 값이 들어있다?
        // 4. 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다.
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스 값을 조회하고
                int idIndex = cursor.getColumnIndex(projections[0]);
                // 4.2 해당 index를 사용해서 실제값을 가져온다.
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(nameIndex);

                int telIndex = cursor.getColumnIndex(projections[2]);
                String tel = cursor.getString(telIndex);

                // 5. 내가 설계한 데이터 클래스에 담아준다.
                Data data = new Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                // 6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
                datas.add(data);
            }
        }
        return datas;
    }
}
```
전화번호부에는 이미 ContentProvider 를 통해 데이터 열어 줄 수 있는 환경이 구성되 있는 것.
나는 ContentResolver 를 통해 그 데이터에 접근하여 데이터를 가져오는 것이다.

앱을 개발할 때 내가 데이터를 제공하고자 한다면 컨택트 프로바이더를 만들어야함.

Q.1 핸드폰의 기본 앱들은 모두 프로바이더를 가지고있나?
A. 아님. 데이터를 제공하지 않는 계산기 등은 필요 없음.

***

```java
Cursor cursor = resolver.query(phoneUri, projections, null, null, null);
```
![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/projec.PNG)

1번째 인자 : 주소록의 주소 값<br>
2번째 인자 : 데이터를 가져올 형태<br>
3,4,5 번째 : 조건식 ( 정렬 등)

***

```java
while(cursor.moveToNext()){
```
![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/projec2.PNG)

커서는 한줄씩 이동

한줄을 다 읽으면 data에 ID Name Number가 들어가는 것이고 다 읽은 후 밑에 줄 밑에 줄 밑에 줄 쭉쭉 이동.
