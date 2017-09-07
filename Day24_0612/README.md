# HttpUrlConnection
***

네트워크 통신은 Main Thread에서 작업 할 수 없도록 강제 되어 있다.
-> Sub Thread 이용 or AsyncTask 이용
***

getData( ) 메소드 : 데이터를 가져오는 함수
```java
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
```
네트워크 통신을 통해 데이터를 가져오면 그 순간은 무조건 String 타입이다. -> getData( ) 메소드의 반환형은 String
json / xml 에 상관없음. -> json, xml 객체로 접근하는 것은 Parsing을 했기 때문에 가능한 것.

***
```java
throws Exception
try - catch
```
**try - catch** 문 : try~catch 가 감싸고 있는 부분에서 결과 처리를 하겠다는 것.
**throw Exception** : 에러 자체를 호출한 측에서 넘겨 받는다.
-> 호출 측에서 UI를 컨트롤 하므로써 오류 메시지에 대한 결과 처리를 선택적으로 표준화 할 수 있음. (Toast,  팝업 등등 )
-> 호출 측에서 try - catch 처리

***
newTask(String url) : 쓰레드를 처리하는 함수
```java
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
```
execute( ) 메소드가 doInBackground( ) 함수를 직접 호출한다. 인자로 url(String) 을 넘기므로 AsyncTask의 첫번째 제네릭은 String 타입이 된다.
onPostExecute( ) 함수로 전달되는 result 는 String 타입 이므로 세번째 제네릭은 String 타입이 된다.

화면에 결과를 출력하려면..
doInBackground( ) 를 통해 SubThread에서 처리한 결과를 메인 스레드로 넘겨야한다.
-> onPostExecute(result)