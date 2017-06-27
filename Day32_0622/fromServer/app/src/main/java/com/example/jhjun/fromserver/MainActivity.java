package com.example.jhjun.fromserver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 내 서버의 데이터를 요청
        networkTask("http://127.0.0.1:8080/bbb.jsp");
    }
        // networkTask를 통해서 AsyncTask가 만들어지고(스레드) 그 안에서 getData의 URLConnection을 통해 서버로 가서 데이터를 가져온다.
    public void networkTask(String url){
        new AsyncTask<String, Void, String>(){

            @Override  // 네트워크 처리
            protected String doInBackground(String... params) {
                String result = getData(params[0]);  // -> 파라미터의 첫번째 값을 의미함 -> url
                return result;
            }

            @Override   // 가져온 데이터 화면에 나타내는 부분
            protected void onPostExecute(String r) {
                Log.e("Result : ","결과 : " + r);
            }
        }.execute(url);   // execute에 url을 넣으면 doInBackGround로 전달됨 ->  첫번째 제네릭을 String으로 바꿔야함.
    }
            // 반환형이 String인 이유 : 가져온 데이터를 화면에 출력하기 위해(결과 처리)
        public String getData(String url){
        StringBuilder result = new StringBuilder();
        try {
            // 1. 요청 처리 끝남.
            URL serverUrl = new URL(url);
            // 주소에 해당하는 서버의 소켓을 연결, 아래 코드는 연결만 한 것.
            HttpURLConnection con = (HttpURLConnection)serverUrl.openConnection();
            // 연결 후 OutputStream으로 데이터 요청, GET하고 주소를 날려준 것.
            con.setRequestMethod("GET"); // http프로토콜 통신 방법중에 GET 방식을 사용하겠다라는 뜻.
            // GET이랑 주소체계가 한 줄에 전송되야 파싱을해서 데이터를 꺼내 사용할 수 있음.
            // 데이터를 처음 보내는 곳은 con.setRequestMethod("GET"); ( 겟이 세팅됬을 때)

            // 응답의 유효성 검사
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 2. 응답 처리 (서버가 응답을 해줄 것 -> 응답을 받으려면 InputStream을 열어야한다.
                BufferedReader br =   // 줄단위로 데이터를 읽기 위해서 버퍼 변수 br에 담는다.
                        new BufferedReader(new InputStreamReader(con.getInputStream()));  // 글자를 읽을 수 있는 스트림리더(레퍼)를 달아줘야한다. & 줄 단위 처리와 속도를 위해 버퍼도 씌운다.
                String temp = "";
                while((temp = br.readLine()) == null){  // 버퍼에서 데이터를 한줄한줄 읽는다. & br.readLine을 하면 줄바꿈 문자가 없어진다.
                    result.append(temp+"\n");  // StringBuilder를 사용했기 때문에 append함수로 한줄 한줄 추가
                }
            }
        }catch (Exception e){e.printStackTrace();}
            return result.toString();  // result라는 StringBuilder에다가 결과값을 쌓아서 최종적으로 호출한 측(doInBackGround) 로 보냄.
    }
}
