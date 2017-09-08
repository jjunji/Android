# RecyclerView

**1. 어댑터의 개념**
![](https://github.com/jjunji/Android/blob/master/Day13_0525/image/adapter.PNG)
<br>
Adapter의 역할

 - List의 셀 하나하나의 맞게 데이터를 가공한다.
 - 데이터가 통째로 바뀐다고 해도 (데이터 Title, Name, Number 가 Car number, Address) 어답터가 한번 가공하고 셀에 뿌려 질 것을 결정하기 때문에 리사이클러뷰(혹은 리스트뷰)를 그대로 사용할 수 있다.
 - 어답터가 없다면 데이터가 바뀔 때 마다 리사이클러뷰를 커스터마이징 해서 써야했을 것.


**Adapter를 구현할 때 의무적으로 구현해야하는 메소드**

 1) onCreateViewHolder( )
: 리스트의 한 셀에 보여질 뷰를 생성.(inflation), 반환 값은 Holder에 뷰를 붙인 형태.(코드 참고)

 2) onBindViewHolder( )
: ViewHolder에 설정한 View안에 있는 위젯들에 데이터를 세팅한다.

 3) getItemCount( )
: 데이터의 개수 반환.

***

**2. 리사이클러뷰의 정의**

1) RecyclerView는 View그룹의 서브클래스로, 자식 View 객체들의 리스트를 보여준다.

 2) 화면이 스크롤되면서 View가 화면을 벗어날 때 그 View를 버리지 않고 재활용한다.
***
**3. 리사이클러뷰를 정의하는 과정.**
![](https://github.com/jjunji/Android/blob/master/Day13_0525/image/recyclerView.PNG)
***
클래스 요약
	
 1. A_Data.class : 데이터 정의
 2. B_Loader.class : 데이터 로드
 3. C_Adapter.class : 어댑터 정의
 4. D_Holder.class : 뷰의 위젯 하나하나를 컨트롤하기 위한 홀더 클래스


***
리사이클러 뷰를 사용할 때 작업 순서

 1. 데이터 구조 파악 -> A_Data
 2. 데이터 가져오기 -> B_Loader
 3. 뷰 정의 - 리사이클러뷰를 사용할 xml 파일 & 리스트의 한 셀을 담당하는 xml 파일을 정의. -> ex) activity_main.xml & row_item.xml
 4. Adapter 생성 -> C_Adapter
 5. Holder 생성 -> D_Holder

***

A_Data.class
```java
public class A_Data {
    private int id;
    private String name;
    private String tel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
```
먼저 데이터를 정의한다.

전화번호부의 데이터를 가져올 것이므로 주소록으로 부터 내가 가져올 데이터를 정의한다.

id, name, tel을 사용할 것을 정의했고, 변수를 private으로 설정하여 getter & setter 를 구현했다.
***
B_Loader.class
```java
public class B_Loader {

    public static List<A_Data> getData(Context context){
        List<A_Data> datas = new ArrayList<>();
        // 로직

        // 전화번호부를 가지고 오기 위해 contentResolver를 사용. -> context가 필요함.
        // 전화번호 데이터를 가져오는 커넥터 역할.
        ContentResolver resolver = context.getContentResolver();

        // 데이터가 있는 테이블 주소 가져오기
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 테이블에서 가져올 컬럼명을 정의
        String proj[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                         ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                         ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 컨텐트 리졸버로 데이터 가져오기. 가져온 형태 -> 커서
        Cursor cursor = resolver.query(phoneUri, proj, null, null, null);

        // cursor에 데이터 존재여부
        if(cursor != null ){
            while (cursor.moveToNext()){
                int index = cursor.getColumnIndex(proj[0]);
                int id = cursor.getInt(index);

                index = cursor.getColumnIndex(proj[1]);
                String name = cursor.getString(index);

                index = cursor.getColumnIndex(proj[2]);
                String tel = cursor.getString(index);

                A_Data data = new A_Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                datas.add(data);  // 위에 정의한 데이터 저장소에 add
                // datas에 열 하나하나 단위의 데이터 클래스가 저장됨 -> 주소록만큼 datas의 개수가 생성됨.
            }
        }
        cursor.close(); // 닫지 않으면 계속 열려있음.
        //

        return datas;
    }
}
```
다음, 데이터의 구조를 정의했으니 이제 데이터를 가져와야한다. 

데이터를 가져오는 로직.(Recycler뷰의 개념 설명이므로 설명 생략)
***
C_Adapter.class
```java
// 홀더는 셀 하나하나의 뷰 컨트롤을 위해 만드는 것.
public class C_Adapter extends RecyclerView.Adapter<D_Holder>{

    List<A_Data> datas;

    public C_Adapter(List<A_Data> datas){
        this.datas = datas;
    }

    @Override
    public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);
    }

    @Override // 화면에 셀이 그려질 때 마다 호출됨.
    public void onBindViewHolder(D_Holder holder, int position) {
        // 위에서 만들어준 디홀더를 온바인드뷰홀더 함수를 통해서 시스템이 나에게 넘겨주면 이곳에서 데이터를 셋팅한다.
        A_Data data = datas.get(position); // 가져온 데이터의 리스트
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
```
어댑터를 정의하는 클래스로, 어댑터는 다음과 같은 책임을 갖는다.

1) 필요한 ViewHolder 객체 생성

2) 모델 계층의 데이터를 ViewHolder와 결합.

※ RecyclerView는 자신이 ViewHolder를 생성하지 않는다. 대신에 그 일을 어댑터에 요청한다.
```java
public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);
}
```

를 보면 반환형이 Holder타입인 onCreateViewHolder메소드를 정의했는데,
이 메소드는 메인 액티비티에서
```java
List<A_Data> datas = B_Loader.getData(this);
```
<A_Data>타입의 변수 datas에 B_Loader 클래스를 통해 가져온 데이터를 담고
```java
C_Adapter adapter = new C_Adapter(datas);
```
위와 같이 adapter객체를 생성할 때 자동으로 실행된다.
view 변수에는 리스트의 한 셀에 해당하는 뷰가 저장되있는 것이다.
뷰를 inflate를 통해 메모리에 객체화 한 후
Holder에 뷰를 붙여 반환한다. 이 반환된 홀더는 홀더 클래스로 이동.
홀더 클래스는 한 셀에 해당하는 뷰 안의 위젯들을 컨트롤하기 위한 연결통로이다.

※ getData(this) -> this로 context 정보를 인자로 담은 것은 전화번호부의 데이터를 가져오기 위해 contentResolver를 사용하기 때문.
```java
 public void onBindViewHolder(D_Holder holder, int position) {
        // 위에서 만들어준 디홀더를 온바인드뷰홀더 함수를 통해서 시스템이 나에게 넘겨주면 이곳에서 데이터를 셋팅한다.
        A_Data data = datas.get(position); // 가져온 데이터의 리스트
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }
```
홀더 클래스를 통해 셀 안의 위젯들을 컨트롤 할 수 있게되면 onBindViewHolder( ) 메소드를 통해 값을 세팅한다.

즉, 메소드 실행순서는
onCreateViewHolder( )    ->    Holder 클래스 호출    ->    onBindViewHolder( )

※ onCreateViewHolder 메서드가 onBindViewHolder 보다 적게 호출 될 수 있다.

-> 충분한 개수의 ViewHolder 객체가 생성되면 RecyclerView가 onCreateHolder의 호출을 중단하기 때문.
기존의 생성된 ViewHolder 객체를 재사용하여 시간&메모리 절감.
***
D_Holder.class
```java
// 홀더 : 셀 하나하나를 다뤄주는 뷰 객체를 만든 것.
public class D_Holder extends RecyclerView.ViewHolder{
    // 위젯 정의
    private TextView textTel;
    private TextView textName;

    public D_Holder(View itemView) {
        super(itemView);
        // 생성자가 생성될 때 xml에 있는 위젯을 소스코드와 연결.
        textTel = (TextView) itemView.findViewById(R.id.textTel);
        textName = (TextView) itemView.findViewById(R.id.textName);
    }

    public String getTel(){
        return textTel.getText().toString();
    }

    public void setTel(String value){
        textTel.setText(value);
    }

    public String getName(){
        return textName.getText().toString();
    }

    public void setName(String value){
        textName.setText(value);
    }
}
```
***
MainActivity.class
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 런타임 권한체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                Toast.makeText(getBaseContext(), " 권한을 가져야 합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            String perms[] = {Manifest.permission.READ_CONTACTS};
            requestPermissions(perms, 100);
        }


    }

    private void init(){
        // 1. 데이터
        List<A_Data> datas = B_Loader.getData(this);
        // 2. 어답터
        C_Adapter adapter = new C_Adapter(datas);             // 1,2번은 데이터와 어답터 연결 과정.
        // 3. 리스트뷰와 어답터 연결                              // 3번은 리스트뷰와 어답터 연결과정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        // 4. 레이아웃매니저
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

```
recyclerView.setAdapter(adapter);
	: 리사이클러뷰에 어답터를 연결. (배치)

recyclerView.setLayoutManager(new LinearLayoutManager(this));
	: 리스트를 보여줄 형태를 정의한다. -> 리니어레이아웃 형태로 정의했기 때문에 일반적인 리스트 형태로 구현됨.