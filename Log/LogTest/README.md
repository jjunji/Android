# Log

**1. Debug��**

* �ܼ��� ������ ã�� �ذ��ϴ� ��
* ������ �ǹ� ���� X
* ��ǻ�� ȸ�ξȿ� ������ ��Ҵ��� �ٽ� ��ǻ�Ͱ� ���� �۵��� ��ǿ��� ����.

```
��Ű���

�����(����: debug), ����� (debugging) Ȥ�� ������ ��ǻ�� ���α׷��� ��Ȯ���̳� ������ ����(����)�� ã�Ƴ��� �׽�Ʈ ������ ���Ѵ�.

�����(debugger)�� ����׸� ���� �����̴�. ����Ŵ� �ַ� ���ϴ� �ڵ忡 �ߴ����� �����Ͽ� ���α׷� ������ �����ϰ�, �޸𸮿� ����� ���� ���캸��, ������ �簳�ϰų�, �ڵ带 �ܰ������� �����ϴ� ���� ������ �Ѵ�. ��� ����ŵ��� �޸� �浹 ����, �޸� ���� ����, ���� ������ ���� ���� ��ɵ� �����Ѵ�.
```

**2. BuildConfig**

�����߿��� �αװ� ������ �������¿��� �αװ� ������ �ʰ� �ϰ� �ʹ�.
-> BuildConfig �� �̿��Ͽ�  ���� ���¿� ���� ���¸� ����.

![](https://github.com/jjunji/Android/blob/master/Log/LogTest/image/BuildConfig.PNG)

```java
public final class BuildConfig {
  public static final boolean DEBUG = Boolean.parseBoolean("true");
  public static final String APPLICATION_ID = "com.jjunji.android.logtest.test";
  public static final String BUILD_TYPE = "debug";
  public static final String FLAVOR = "";
  public static final int VERSION_CODE = 1;
  public static final String VERSION_NAME = "1.0";
}
```
BuildConfig�� DEBUG ���� boolean Ÿ�����μ� ���߽ÿ��� true, �����ÿ��� false�� �ڵ����� �ٲ�.  -> �̸� ���ؼ� ����ڴ� ���� ���¿� ���� ���¸� ������ �� ����.

***

**�α� ��� ���**

������ �߻��� ����(����) �Ʒ��δ� ������ �ȵǱ� ������ �α״� ������ �� ���� ���� ���� �Ѵ�.
```java
public static final String TAG = "TAG";
```
�α��� ù��° ���ڷ� ���� �α׸� ȣ���ϴ� Ŭ���� ���� ���µ�, �α״� ���� ����ϹǷ�
���ȭ�Ͽ� ������ ���� ���� ���ϴ�.
```java
Log.i(TAG, "�α��� ����=" + "Ȯ���ϰ� ���� ���� Ȥ�� ��");
```
�α׸� ���� ����Ѵٸ� Logger class�� �����ϸ� ����.
�α� Class�� �����ϰ� �����Ǿ� �ֱ� ������ ���� �� ���ϰ� �ɸ��� �ʴ´�.

Util ��Ű�� - Logger.class
```java
public class Logger {
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    public static void i(String tag, String msg){
        if(DEBUG) {     // if DEBUG�� true �϶��� �۵�
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG) {     // if DEBUG�� true �϶��� �۵�
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(DEBUG) {     // if DEBUG�� true �϶��� �۵�
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG) {     // if DEBUG�� true �϶��� �۵�
            Log.e(tag, msg);
        }
    }

}
```
***

**�α� Ȱ���**

MainActivity
```java
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.LOGV("TEST :: VERBOSE");
                Logger.LOGI("TEST :: INFO");
                Logger.LOGW("TEST :: WARN");
                Logger.LOGD("TEST :: DEBUG");
                Logger.LOGE("TEST :: ERROR");

                Log.i(TAG, "�α��� ����=" + "Ȯ���ϰ� ���� ���� Ȥ�� ��");
            }
        });
    }
}

```
��ư Ŭ���� Logger Ŭ���� ȣ��.

Util - Logger.class
```java
public class Logger {
    public static void LOGV(String msg) {
        Logger(Log.VERBOSE, msg);
    }

    public static void LOGD(String msg) {
        Logger(Log.DEBUG, msg);
    }

    public static void LOGI(String msg) {
        Logger(Log.INFO, msg);
    }

    public static void LOGW(String msg) {
        Logger(Log.WARN, msg);
    }

    public static void LOGE(String msg) {
        Logger(Log.ERROR, msg);
    }

    private static void Logger(int priority, String msg) {
        if (BuildConfig.DEBUG) {
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("[").append(Thread.currentThread().getStackTrace()[4].getMethodName())
                    .append("()").append("]").append(" :: ").append(msg)
                    .append(" (").append(Thread.currentThread().getStackTrace()[4].getFileName()).append(":")
                    .append(Thread.currentThread().getStackTrace()[4].getLineNumber()).append(")");

            Log.println(priority, Thread.currentThread().getStackTrace()[4].getFileName()+"::", msgBuilder.toString());
        }
    }
}
```
* �α׸޼������� �αװ� ��µǴ� Ŭ����, �޼ҵ�, ���μ��� ��µǵ��� ��.
* �ش� ���� �� �ٷ� ���� ����.

```
Thread.currentThread().getStackTrace()
```
�ϳ��� ���α׷��� �����ϴ� ���� ������� �پ��� �޼��带 ���ƴٴϰ� �ȴ�.
�޼��尡 ȣ��ǰ� ����Ǹ�, �޼��带 ȣ���ϴ� ������ �ٽ� ���ư��� �ȴ�.
�̷��� ȣ��� ������ �ٽ� ���ư��� ���ؼ��� Stack�� �޼��尡 ȣ��� ����Ʈ�� ����ؾ���.
���α׷� ���� �� ��ϵǴ� Ư�� ����Ʈ���� �׾Ƴ��� ���� Stack Trace.

![](https://github.com/jjunji/Android/blob/master/Log/LogTest/image/log.PNG)

```java
Thread.currentThread().getStackTrace()[4].getMethodName()
```
[4] ������ onClick �޼ҵ带 ����Ű��

```java
for(int i=0; i<Thread.currentThread().getStackTrace().length; i++){
                msgBuilder2.append(Thread.currentThread().getStackTrace()[i].getMethodName() + " / ");
            }

            Log.println(priority, "test" , msgBuilder2.toString());
```
�� ���� ��ü�� ����غ��� �Ʒ��� ���� ����� ���´ٴ� ���� Ȯ���� �� �ִ�.
![](https://github.com/jjunji/Android/blob/master/Log/LogTest/image/log2.PNG)