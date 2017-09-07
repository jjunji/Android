# Interface 적용하기

기존 HttpUrlConnection 소스에 Interface를 적용해 본다.
<br>
MainActivity.class
```java
public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        String url = "http://google.com";
        newTask(url);
        // textView.setText();
    }

    public void newTask(String url){
        new AsyncTask<String, Void, String>(){
            // 백그라운드 처리 함수
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try{
                    // getData함수를 가져온다.
                    result = getData(params[0]);
                    Log.i("Network",result);
                    // result를 화면에 출력하려면
                    // 서브 스레드에서 메인 스레드로 값을 넘겨줘야함 -> Asynctask
                }catch (Exception e){
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }

        }.execute(url);
    }

    // 데이터를 가져온 순간은 무조건 String이다. -> return값은 String
    // 인자로 받은 url로 네트워크를 통해 데이터를 가져오는 함수
    public String getData(String url) throws Exception{  // 요청한 곳에서 Exception처리를 해줌.
        String result = "";

        // 네트웍 처리
        // 1. 서버와 연결(위에 url을 가진 서버) , 요청처리(Request)
        // 1.1 URL 객체 만들기
        URL serverUrl = new URL(url);
        // 1.2 연결 객체 생성 -> DBHelper와 비슷한 역할.
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();  // url 객체에서 연결을 꺼낸다.
        // 1.3 http method 결정
        con.setRequestMethod("GET");

        // 2. 응답처리 Response
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();
        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){  // 정상적인 코드 처리
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
        // 2.3 오류에 대한 응답처리
        } else{
            // 각자 호출측으로 Exception을 만들어서 넘겨줄 것
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
}
```
기존 MainActivity에는 네트워크 통신 메소드와 데이터를 가져오는 메소드가 같이 있음.


### 1.  getData( ) 메소드를 Remote.class 로 분리
```java
public class Remote {
    public static String getData(String url) throws Exception{

        String result = "";
        // 네트워크 처리
        URL serverUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
        con.setRequestMethod("GET");

        // 응답 처리
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();
        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){  // 정상적인 코드 처리
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
            // 2.3 오류에 대한 응답처리
        } else{
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
}
```
앱이 로드되는 순간 메모리에 올려놓고 바로 사용하기 위해 static 으로 설계.
-> Remote.getData( ) 로 접근 가능해짐.

### 2.1
  newTask( ) 부분을 Task.class 로 분리 & newTask( ) 를 바로 사용할 수 있도록 static으로 설계. -> Task.newTask( ) 로 접근 가능.
```java
public class Task {
    public static void newTask(final MainActivity activity) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                //textView.setText(result);
                activity.postExecute(result);
            }
      //}.execute(url);
        }.execute(activity.getUrl());
    }
}
```
newTask( ) 가 호출되면서 넘겨주는 값 :  url, textView
클래스의 재사용성을 위해서 넘겨주는 인자와, 처리가 끝나고나서 호출되는 부분을 분리해줄 필요가 있다.
만약,
```
public static void newTask(String url, TextView textView)
```
와 같이 인자를 명시해버리면, 위와 같은 형태로만 사용할 수 있게 된다.

추상적으로 접근 -> 내가 넘겨주는 곳에 결과 처리를 하는 함수 & url(어떤 값) 을 꺼낼 수 있는 함수 2가지가 있어야 한다.

ex)
```java
public class Task {
    public void newTask(String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                //textView.setText(result);
                postExecute();
            }
      //}.execute(url);
        }.execute(getUrl());
    }
}
```
MainActivity 에서 아래 두 함수 생성
```java
    public String getUrl(){
        return "주소 값";
    }

    public void postExecute(){
        // 결과 처리
    }
```
위와 같이 2가지 함수를 만들고, 호출될 수 있는 상황을 만들어주면 된다.
***
현재 시점에 MainActivity.class
```java
public class MainActivity extends AppCompatActivity {

    TextView textView;
    String url = "http://google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Task.newTask(this);
        //newTask(url);
        // textView.setText();
    }

    public String getUrl() {
        return url; // 주소 값
    }

    public void postExecute(String result) {
        textView.setText(result); // 결과 처리
    }
}
```
현재 시점에 Task.class
```java
public class Task {
    public static void newTask(final MainActivity activity) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                //textView.setText(result);
                activity.postExecute(result);
            }
      //}.execute(url);
        }.execute(activity.getUrl());
    }
}
```
### 정리
* MainActivity에서 Task 클래스의 newTask(this) 호출,인자로 자기 자신을 넘김.
* Task 클래스에서는 MainActivity를 그대로 받았기 때문에 MainActivity 안에 함수를 모두 사용할 수 있다. ( 나 자신을 넘겼으니, 알아서 꺼내서 써 )
*  activity.getUrl( ) -> MainActivity의 getUrl( ) 을 호출하게 됨 -> MainActivity에 전역으로 선언된 주소 값이 params[0] 으로 전달됨.
*  결과처리 후 activity.postExecute(result) 실행 -> MainActivity에 textView 컨트롤 가능.

***
문제점

* 타입이 고정되었다. -> activity.postExecute(result) 는 activity의 postExecute( ) 가 아니라 어떤 클래스의 postExecute가 되어야 한다.
*  타입의 의존성이 생김 -> 객체 지향 설계에 맞지 않는다.

***
2.2
**Interface 생성**
 
 TaskInterface
```java
public interface TaskInterface {
    public String getUrl();
    public void postExecute(String result);
}
```
어떤 클래스가 되든 인터페이스를 구현하면 캐스팅을 통해서 위 함수를 사용가능.

Task.class
```java
public class Task {
    public static void newTask(final TaskInterface taskInterface) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    result = getData(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                //textView.setText(result);
                taskInterface.postExecute(result);
            }
      //}.execute(url);
        }.execute(taskInterface.getUrl());
    }
}
```
인자로 interface를 받는다 -> 인자로 넘어오는 클래스가 interface를 구현했다는 뜻.

MainActivity
```java
public class MainActivity extends AppCompatActivity implements TaskInterface{

    TextView textView;
    String url = "http://google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Task.newTask(this);
        //newTask(url);
        // textView.setText();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void postExecute(String result) {
        textView.setText(result);
    }
}
```
무엇이 넘어오는지는 호출 측에서 결정. Task의 newTask( ) 메소드는 알 필요가 없다.
캐스팅이 되면 인터페이스에 설계한 함수만을 바라본다.
-> 인자로 넘긴 클래스의 코드를 실행시켜 주는 것.