# Log

**1. Debug란**

* 단순히 오류를 찾아 해결하는 것
* 개선의 의미 포함 X
* 컴퓨터 회로안에 나방을 잡았더니 다시 컴퓨터가 정상 작동한 사건에서 유래.

```
위키백과

디버그(영어: debug), 디버깅 (debugging) 혹은 수정은 컴퓨터 프로그램의 정확성이나 논리적인 오류(버그)를 찾아내는 테스트 과정을 뜻한다.

디버거(debugger)는 디버그를 돕는 도구이다. 디버거는 주로 원하는 코드에 중단점을 지정하여 프로그램 실행을 정지하고, 메모리에 저장된 값을 살펴보며, 실행을 재개하거나, 코드를 단계적으로 실행하는 등의 동작을 한다. 고급 디버거들은 메모리 충돌 감지, 메모리 누수 감지, 다중 스레드 관리 등의 기능도 지원한다.
```

**2. BuildConfig**

개발중에는 로그가 찍히고 배포상태에는 로그가 찍히지 않게 하고 싶다.
-> BuildConfig 를 이용하여  개발 상태와 배포 상태를 구분.

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
BuildConfig의 DEBUG 값은 boolean 타입으로서 개발시에는 true, 배포시에는 false로 자동으로 바뀜.  -> 이를 통해서 사용자는 개발 상태와 배포 상태를 구분할 수 있음.

***

**로그 찍는 방법**

에러가 발생한 시점(라인) 아래로는 실행이 안되기 때문에 로그는 에러가 난 시점 위로 찍어야 한다.
```java
public static final String TAG = "TAG";
```
로그의 첫번째 인자로 현재 로그를 호출하는 클래스 명을 쓰는데, 로그는 자주 사용하므로
상수화하여 선언해 놓는 것이 편하다.
```java
Log.i(TAG, "로그의 내용=" + "확인하고 싶은 내용 혹은 값");
```
로그를 자주 사용한다면 Logger class를 생성하면 좋다.
로그 Class는 간단하게 구성되어 있기 때문에 많이 찍어도 부하가 걸리지 않는다.

Util 패키지 - Logger.class
```java
public class Logger {
    public static final boolean DEBUG = BuildConfig.DEBUG_MODE;

    public static void i(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG) {     // if DEBUG가 true 일때만 작동
            Log.e(tag, msg);
        }
    }

}
```
***

**로그 활용법**

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

                Log.i(TAG, "로그의 내용=" + "확인하고 싶은 내용 혹은 값");
            }
        });
    }
}

```
버튼 클릭시 Logger 클래스 호출.

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
* 로그메세지에서 로그가 출력되는 클래스, 메소드, 라인수가 출력되도록 함.
* 해당 라인 수 바로 참조 가능.

```
Thread.currentThread().getStackTrace()
```
하나의 프로그램을 수행하는 동안 스레드는 다양한 메서드를 돌아다니게 된다.
메서드가 호출되고 종료되면, 메서드를 호출하는 곳으로 다시 돌아가게 된다.
이렇게 호출된 곳으로 다시 돌아가기 위해서는 Stack에 메서드가 호출된 포인트를 기록해야함.
프로그램 실행 중 기록되는 특정 포인트들을 쌓아놓은 것이 Stack Trace.

![](https://github.com/jjunji/Android/blob/master/Log/LogTest/image/log.PNG)

```java
Thread.currentThread().getStackTrace()[4].getMethodName()
```
[4] 값에는 onClick 메소드를 가리키며

```java
for(int i=0; i<Thread.currentThread().getStackTrace().length; i++){
                msgBuilder2.append(Thread.currentThread().getStackTrace()[i].getMethodName() + " / ");
            }

            Log.println(priority, "test" , msgBuilder2.toString());
```
를 통해 전체를 출력해보면 아래와 같은 결과가 나온다는 것을 확인할 수 있다.
![](https://github.com/jjunji/Android/blob/master/Log/LogTest/image/log2.PNG)