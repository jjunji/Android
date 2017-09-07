# Interface �����ϱ�

���� HttpUrlConnection �ҽ��� Interface�� ������ ����.
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
            // ��׶��� ó�� �Լ�
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try{
                    // getData�Լ��� �����´�.
                    result = getData(params[0]);
                    Log.i("Network",result);
                    // result�� ȭ�鿡 ����Ϸ���
                    // ���� �����忡�� ���� ������� ���� �Ѱ������ -> Asynctask
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

    // �����͸� ������ ������ ������ String�̴�. -> return���� String
    // ���ڷ� ���� url�� ��Ʈ��ũ�� ���� �����͸� �������� �Լ�
    public String getData(String url) throws Exception{  // ��û�� ������ Exceptionó���� ����.
        String result = "";

        // ��Ʈ�� ó��
        // 1. ������ ����(���� url�� ���� ����) , ��ûó��(Request)
        // 1.1 URL ��ü �����
        URL serverUrl = new URL(url);
        // 1.2 ���� ��ü ���� -> DBHelper�� ����� ����.
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();  // url ��ü���� ������ ������.
        // 1.3 http method ����
        con.setRequestMethod("GET");

        // 2. ����ó�� Response
        // 2.1 �����ڵ� �м�
        int responseCode = con.getResponseCode();
        // 2.2 �������� ����ó��
        if(responseCode == HttpURLConnection.HTTP_OK){  // �������� �ڵ� ó��
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
        // 2.3 ������ ���� ����ó��
        } else{
            // ���� ȣ�������� Exception�� ���� �Ѱ��� ��
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
}
```
���� MainActivity���� ��Ʈ��ũ ��� �޼ҵ�� �����͸� �������� �޼ҵ尡 ���� ����.


### 1.  getData( ) �޼ҵ带 Remote.class �� �и�
```java
public class Remote {
    public static String getData(String url) throws Exception{

        String result = "";
        // ��Ʈ��ũ ó��
        URL serverUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
        con.setRequestMethod("GET");

        // ���� ó��
        // 2.1 �����ڵ� �м�
        int responseCode = con.getResponseCode();
        // 2.2 �������� ����ó��
        if(responseCode == HttpURLConnection.HTTP_OK){  // �������� �ڵ� ó��
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
            // 2.3 ������ ���� ����ó��
        } else{
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
}
```
���� �ε�Ǵ� ���� �޸𸮿� �÷����� �ٷ� ����ϱ� ���� static ���� ����.
-> Remote.getData( ) �� ���� ��������.

### 2.1
  newTask( ) �κ��� Task.class �� �и� & newTask( ) �� �ٷ� ����� �� �ֵ��� static���� ����. -> Task.newTask( ) �� ���� ����.
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
newTask( ) �� ȣ��Ǹ鼭 �Ѱ��ִ� �� :  url, textView
Ŭ������ ���뼺�� ���ؼ� �Ѱ��ִ� ���ڿ�, ó���� �������� ȣ��Ǵ� �κ��� �и����� �ʿ䰡 �ִ�.
����,
```
public static void newTask(String url, TextView textView)
```
�� ���� ���ڸ� ����ع�����, ���� ���� ���·θ� ����� �� �ְ� �ȴ�.

�߻������� ���� -> ���� �Ѱ��ִ� ���� ��� ó���� �ϴ� �Լ� & url(� ��) �� ���� �� �ִ� �Լ� 2������ �־�� �Ѵ�.

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
MainActivity ���� �Ʒ� �� �Լ� ����
```java
    public String getUrl(){
        return "�ּ� ��";
    }

    public void postExecute(){
        // ��� ó��
    }
```
���� ���� 2���� �Լ��� �����, ȣ��� �� �ִ� ��Ȳ�� ������ָ� �ȴ�.
***
���� ������ MainActivity.class
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
        return url; // �ּ� ��
    }

    public void postExecute(String result) {
        textView.setText(result); // ��� ó��
    }
}
```
���� ������ Task.class
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
### ����
* MainActivity���� Task Ŭ������ newTask(this) ȣ��,���ڷ� �ڱ� �ڽ��� �ѱ�.
* Task Ŭ���������� MainActivity�� �״�� �޾ұ� ������ MainActivity �ȿ� �Լ��� ��� ����� �� �ִ�. ( �� �ڽ��� �Ѱ�����, �˾Ƽ� ������ �� )
*  activity.getUrl( ) -> MainActivity�� getUrl( ) �� ȣ���ϰ� �� -> MainActivity�� �������� ����� �ּ� ���� params[0] ���� ���޵�.
*  ���ó�� �� activity.postExecute(result) ���� -> MainActivity�� textView ��Ʈ�� ����.

***
������

* Ÿ���� �����Ǿ���. -> activity.postExecute(result) �� activity�� postExecute( ) �� �ƴ϶� � Ŭ������ postExecute�� �Ǿ�� �Ѵ�.
*  Ÿ���� �������� ���� -> ��ü ���� ���迡 ���� �ʴ´�.

***
2.2
**Interface ����**
 
 TaskInterface
```java
public interface TaskInterface {
    public String getUrl();
    public void postExecute(String result);
}
```
� Ŭ������ �ǵ� �������̽��� �����ϸ� ĳ������ ���ؼ� �� �Լ��� ��밡��.

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
���ڷ� interface�� �޴´� -> ���ڷ� �Ѿ���� Ŭ������ interface�� �����ߴٴ� ��.

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
������ �Ѿ�������� ȣ�� ������ ����. Task�� newTask( ) �޼ҵ�� �� �ʿ䰡 ����.
ĳ������ �Ǹ� �������̽��� ������ �Լ����� �ٶ󺻴�.
-> ���ڷ� �ѱ� Ŭ������ �ڵ带 ������� �ִ� ��.