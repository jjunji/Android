# Intent

**1. 인텐트**
작업을 수행하기 위해 사용되는 일종의 명령 또는 데이터 전달 수단.

**2. 인텐트의 역할 & 사용 방식**

 인텐트의 기본 구성 요소

 1) 액션(Action) : 수행할 기능
 2) 데이터(Data) : 액션이 수행될 대상 데이터

**2.1 명시적 인텐트 & 암시적 인텐트**

 1) 명시적 인텐트(Explicit Intent)
 
인텐트에 클래스 객체나 컴포넌트 이름을 지정하여 호출할 대상을 확실히 알 수 있는 경우

 2) 암시적 인텐트(Implicit Intent)

MIME 타입에 따라 안드로이드 시스템에서 적절한 다른 앱의 액티비티를 찾은 후 띄우는 방식.

**2.2 인텐트가 가지고 있는 속성**

 1) 범주(Category) : 액션이 실행되는데 필요한 추가적인 정보를 제공
 2) 타입(Type) : 인텐트에 들어가는 데이터의 MIME 타입을 명시적으로 지정.
 3) 컴포넌트(Component) : 인텐트에 사용될 컴포넌트 클래스 이름을 명시적으로 지정.
 4) 부가 데이터(Extra Data) : 인텐트는 추가적인 정보를 넣을 수 있도록 번들(Bundle)객체를 담고 있다.
 이 객체를 통해 인텐트 안에 더 많은 정보를 넣어 다른 애플리케이션 구성 요소에 전달할 수 있음.

**3. startActivityForResult( ) 메소드**

```java
						(1)           (2)
startActivityForResult(Intent intent, int requestCode)
```
(1) : 정보를 담고 있는 Intent 객체                  

(2) : 정수로된 코드 값 -> 각각의 액티비티를 구분하기 위해 사용. 

띄웠던 액티비티에서 원래 액티비티로 응답을 보내온다면 새로 띄웠던 액티비티 중에 어떤 것으로 부터 온 응답인지 구분할 필요가 있기 때문에 사용됨.

```java
public class MenuActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);        
	    setContentView(R.layout.activity_menu);
	    Button button = (Button) findViewById(R.id.button);
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	             1) Intent intent = new Intent();
	             2) intent.putExtra("name", "mike");
	             3) setResult(RESULT_OK, intent);
		             finish();
			}
		});
}}
```
1)  Intent 클래스를 사용해 객체를 하나 만듬.
2)  intent에 넣을 데이터. name의 값을 부가데이터로 넣기.
3) setResult(응답 코드, 인텐트) -> 새로 띄운 액티비티에서 이전 액티비티로 인텐트를 전달하고 싶을 때 사용되는 메소드.

```java
public static final int REQUEST_CODE_MENU = 101;
```
다른 액티비티를 띄우기 위한 요청코드 정의.

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MENU){
            Toast.makeText(this, "onActivity 메소드 호출됨. 요청 코드 : "
                    + requestCode + "결과코드 : " + resultCode, Toast.LENGTH_SHORT).show();
            if(resultCode == RESULT_OK){
                String name = data.getExtras().getString("name");
                Toast.makeText(getApplicationContext(), "응답으로 전달된 name : " + name , Toast.LENGTH_LONG).show();
            }    
        }
    }
```
startActivityForResult( ) -> 메뉴(새 액티비티)를 띄우는 데 사용하지만 새로 띄운 액티비티로부터 응답 받을 수 있음.

***

```java
// 웹페이지 띄우기
Uri uri = Uri.parse("http://www.google.com");
Intent it  = new Intent(Intent.ACTION_VIEW,uri);
startActivity(it);

// 구글맵 띄우기
Uri uri = Uri.parse("geo:38.899533,-77.036476");
Intent it = new Intent(Intent.Action_VIEW,uri);
startActivity(it); 


// 구글 길찾기 띄우기
Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=출발지주소&daddr=도착지주소&hl=ko");
Intent it = new Intent(Intent.ACTION_VIEW,URI);
startActivity(it);


// 전화 걸기
Uri uri = Uri.parse("tel:xxxxxx");
Intent it = new Intent(Intent.ACTION_DIAL, uri);  
startActivity(it);  


Uri uri = Uri.parse("tel.xxxxxx");
Intent it = new Intent(Intent.ACTION_CALL,uri);
// 퍼미션을 잊지 마세요. <uses-permission id="android.permission.CALL_PHONE" />


// SMS/MMS 발송
Intent it = new Intent(Intent.ACTION_VIEW);   
it.putExtra("sms_body", "The SMS text");   
it.setType("vnd.android-dir/mms-sms");   
startActivity(it);  


// SMS 발송
Uri uri = Uri.parse("smsto:0800000123");   
Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
it.putExtra("sms_body", "The SMS text");   
startActivity(it);  


// MMS 발송
Uri uri = Uri.parse("content://media/external/images/media/23");   
Intent it = new Intent(Intent.ACTION_SEND);   
it.putExtra("sms_body", "some text");   
it.putExtra(Intent.EXTRA_STREAM, uri);   
it.setType("image/png");   
startActivity(it); 


// 이메일 발송
Uri uri = Uri.parse("mailto:xxx@abc.com");
Intent it = new Intent(Intent.ACTION_SENDTO, uri);
startActivity(it);


Intent it = new Intent(Intent.ACTION_SEND);   
it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");   
it.putExtra(Intent.EXTRA_TEXT, "The email body text");   
it.setType("text/plain");   
startActivity(Intent.createChooser(it, "Choose Email Client"));  


Intent it = new Intent(Intent.ACTION_SEND);     
String[] tos = {"me@abc.com"};     
String[] ccs = {"you@abc.com"};     
it.putExtra(Intent.EXTRA_EMAIL, tos);     
it.putExtra(Intent.EXTRA_CC, ccs);     
it.putExtra(Intent.EXTRA_TEXT, "The email body text");     
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");     
it.setType("message/rfc822");     
startActivity(Intent.createChooser(it, "Choose Email Client"));   


// extra 추가하기
Intent it = new Intent(Intent.ACTION_SEND);   
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");   
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");   
sendIntent.setType("audio/mp3");   
startActivity(Intent.createChooser(it, "Choose Email Client"));


// 미디어파일 플레이 하기
Intent it = new Intent(Intent.ACTION_VIEW);
Uri uri = Uri.parse("file:///sdcard/song.mp3");
it.setDataAndType(uri, "audio/mp3");
startActivity(it);


Uri uri = Uri.withAppendedPath(
  MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");   
Intent it = new Intent(Intent.ACTION_VIEW, uri);   
startActivity(it);  


// 설치 어플 제거
Uri uri = Uri.fromParts("package", strPackageName, null);   
Intent it = new Intent(Intent.ACTION_DELETE, uri);   
startActivity(it);


// APK파일을 통해 제거하기
Uri uninstallUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_DELETE, uninstallUri);


// APK파일 설치
Uri installUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);


// 음악 파일 재생
Uri playUri = Uri.parse("file:///sdcard/download/everything.mp3");
returnIt = new Intent(Intent.ACTION_VIEW, playUri);


// 첨부파일을 추가하여 메일 보내기
Intent it = new Intent(Intent.ACTION_SEND);  
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");  
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/eoe.mp3");  
sendIntent.setType("audio/mp3");  
startActivity(Intent.createChooser(it, "Choose Email Client"));


// 마켓에서 어플리케이션 검색
Uri uri = Uri.parse("market://search?q=pname:pkg_name");  
Intent it = new Intent(Intent.ACTION_VIEW, uri);  
startActivity(it);  
// 패키지명은 어플리케이션의 전체 패키지명을 입력해야 합니다.


// 마켓 어플리케이션 상세 화면
Uri uri = Uri.parse("market://details?id=어플리케이션아이디");  
Intent it = new Intent(Intent.ACTION_VIEW, uri);  
startActivity(it);
// 아이디의 경우 마켓 퍼블리싱사이트의 어플을 선택후에 URL을 확인해보면 알 수 있습니다.


// 구글 검색
Intent intent = new Intent();
intent.setAction(Intent.ACTION_WEB_SEARCH);
intent.putExtra(SearchManager.QUERY,"searchString")
startActivity(intent);
```