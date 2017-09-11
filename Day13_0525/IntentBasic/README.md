# Intent

**1. ����Ʈ**
�۾��� �����ϱ� ���� ���Ǵ� ������ ��� �Ǵ� ������ ���� ����.

**2. ����Ʈ�� ���� & ��� ���**

 ����Ʈ�� �⺻ ���� ���

 1) �׼�(Action) : ������ ���
 2) ������(Data) : �׼��� ����� ��� ������

**2.1 ����� ����Ʈ & �Ͻ��� ����Ʈ**

 1) ����� ����Ʈ(Explicit Intent)
 
����Ʈ�� Ŭ���� ��ü�� ������Ʈ �̸��� �����Ͽ� ȣ���� ����� Ȯ���� �� �� �ִ� ���

 2) �Ͻ��� ����Ʈ(Implicit Intent)

MIME Ÿ�Կ� ���� �ȵ���̵� �ý��ۿ��� ������ �ٸ� ���� ��Ƽ��Ƽ�� ã�� �� ���� ���.

**2.2 ����Ʈ�� ������ �ִ� �Ӽ�**

 1) ����(Category) : �׼��� ����Ǵµ� �ʿ��� �߰����� ������ ����
 2) Ÿ��(Type) : ����Ʈ�� ���� �������� MIME Ÿ���� ��������� ����.
 3) ������Ʈ(Component) : ����Ʈ�� ���� ������Ʈ Ŭ���� �̸��� ��������� ����.
 4) �ΰ� ������(Extra Data) : ����Ʈ�� �߰����� ������ ���� �� �ֵ��� ����(Bundle)��ü�� ��� �ִ�.
 �� ��ü�� ���� ����Ʈ �ȿ� �� ���� ������ �־� �ٸ� ���ø����̼� ���� ��ҿ� ������ �� ����.

**3. startActivityForResult( ) �޼ҵ�**

```java
						(1)           (2)
startActivityForResult(Intent intent, int requestCode)
```
(1) : ������ ��� �ִ� Intent ��ü                  

(2) : �����ε� �ڵ� �� -> ������ ��Ƽ��Ƽ�� �����ϱ� ���� ���. 

����� ��Ƽ��Ƽ���� ���� ��Ƽ��Ƽ�� ������ �����´ٸ� ���� ����� ��Ƽ��Ƽ �߿� � ������ ���� �� �������� ������ �ʿ䰡 �ֱ� ������ ����.

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
1)  Intent Ŭ������ ����� ��ü�� �ϳ� ����.
2)  intent�� ���� ������. name�� ���� �ΰ������ͷ� �ֱ�.
3) setResult(���� �ڵ�, ����Ʈ) -> ���� ��� ��Ƽ��Ƽ���� ���� ��Ƽ��Ƽ�� ����Ʈ�� �����ϰ� ���� �� ���Ǵ� �޼ҵ�.

```java
public static final int REQUEST_CODE_MENU = 101;
```
�ٸ� ��Ƽ��Ƽ�� ���� ���� ��û�ڵ� ����.

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MENU){
            Toast.makeText(this, "onActivity �޼ҵ� ȣ���. ��û �ڵ� : "
                    + requestCode + "����ڵ� : " + resultCode, Toast.LENGTH_SHORT).show();
            if(resultCode == RESULT_OK){
                String name = data.getExtras().getString("name");
                Toast.makeText(getApplicationContext(), "�������� ���޵� name : " + name , Toast.LENGTH_LONG).show();
            }    
        }
    }
```
startActivityForResult( ) -> �޴�(�� ��Ƽ��Ƽ)�� ���� �� ��������� ���� ��� ��Ƽ��Ƽ�κ��� ���� ���� �� ����.

***

```java
// �������� ����
Uri uri = Uri.parse("http://www.google.com");
Intent it  = new Intent(Intent.ACTION_VIEW,uri);
startActivity(it);

// ���۸� ����
Uri uri = Uri.parse("geo:38.899533,-77.036476");
Intent it = new Intent(Intent.Action_VIEW,uri);
startActivity(it); 


// ���� ��ã�� ����
Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=������ּ�&daddr=�������ּ�&hl=ko");
Intent it = new Intent(Intent.ACTION_VIEW,URI);
startActivity(it);


// ��ȭ �ɱ�
Uri uri = Uri.parse("tel:xxxxxx");
Intent it = new Intent(Intent.ACTION_DIAL, uri);  
startActivity(it);  


Uri uri = Uri.parse("tel.xxxxxx");
Intent it = new Intent(Intent.ACTION_CALL,uri);
// �۹̼��� ���� ������. <uses-permission id="android.permission.CALL_PHONE" />


// SMS/MMS �߼�
Intent it = new Intent(Intent.ACTION_VIEW);   
it.putExtra("sms_body", "The SMS text");   
it.setType("vnd.android-dir/mms-sms");   
startActivity(it);  


// SMS �߼�
Uri uri = Uri.parse("smsto:0800000123");   
Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
it.putExtra("sms_body", "The SMS text");   
startActivity(it);  


// MMS �߼�
Uri uri = Uri.parse("content://media/external/images/media/23");   
Intent it = new Intent(Intent.ACTION_SEND);   
it.putExtra("sms_body", "some text");   
it.putExtra(Intent.EXTRA_STREAM, uri);   
it.setType("image/png");   
startActivity(it); 


// �̸��� �߼�
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


// extra �߰��ϱ�
Intent it = new Intent(Intent.ACTION_SEND);   
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");   
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");   
sendIntent.setType("audio/mp3");   
startActivity(Intent.createChooser(it, "Choose Email Client"));


// �̵������ �÷��� �ϱ�
Intent it = new Intent(Intent.ACTION_VIEW);
Uri uri = Uri.parse("file:///sdcard/song.mp3");
it.setDataAndType(uri, "audio/mp3");
startActivity(it);


Uri uri = Uri.withAppendedPath(
  MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");   
Intent it = new Intent(Intent.ACTION_VIEW, uri);   
startActivity(it);  


// ��ġ ���� ����
Uri uri = Uri.fromParts("package", strPackageName, null);   
Intent it = new Intent(Intent.ACTION_DELETE, uri);   
startActivity(it);


// APK������ ���� �����ϱ�
Uri uninstallUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_DELETE, uninstallUri);


// APK���� ��ġ
Uri installUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);


// ���� ���� ���
Uri playUri = Uri.parse("file:///sdcard/download/everything.mp3");
returnIt = new Intent(Intent.ACTION_VIEW, playUri);


// ÷�������� �߰��Ͽ� ���� ������
Intent it = new Intent(Intent.ACTION_SEND);  
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");  
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/eoe.mp3");  
sendIntent.setType("audio/mp3");  
startActivity(Intent.createChooser(it, "Choose Email Client"));


// ���Ͽ��� ���ø����̼� �˻�
Uri uri = Uri.parse("market://search?q=pname:pkg_name");  
Intent it = new Intent(Intent.ACTION_VIEW, uri);  
startActivity(it);  
// ��Ű������ ���ø����̼��� ��ü ��Ű������ �Է��ؾ� �մϴ�.


// ���� ���ø����̼� �� ȭ��
Uri uri = Uri.parse("market://details?id=���ø����̼Ǿ��̵�");  
Intent it = new Intent(Intent.ACTION_VIEW, uri);  
startActivity(it);
// ���̵��� ��� ���� �ۺ��̻���Ʈ�� ������ �����Ŀ� URL�� Ȯ���غ��� �� �� �ֽ��ϴ�.


// ���� �˻�
Intent intent = new Intent();
intent.setAction(Intent.ACTION_WEB_SEARCH);
intent.putExtra(SearchManager.QUERY,"searchString")
startActivity(intent);
```