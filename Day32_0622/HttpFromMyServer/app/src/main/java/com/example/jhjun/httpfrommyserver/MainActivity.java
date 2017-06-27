package com.example.jhjun.httpfrommyserver;

import android.os.AsyncTask;
import android.support.v4.view.ActionProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 내 서버의 데이터를 요청
        networkTask("http://192.168.10.217:8080/ccc.jsp");
    }

    public void networkTask(String url){

        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                String result = getData(params[0]);
                return result;
            }
            @Override
            protected void onPostExecute(String r) {
                Log.e("RESULT","결과:"+r);
            }
        }.execute(url);

    }

    public String getData(String url){
        StringBuilder result = new StringBuilder();
        try {
            Log.e("URL",""+url);
            // 1. 요청처리
            URL serverUrl = new URL(url);
            // 주소에 해당하는 서버의 socket 을 연결
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
            // OutputStream으로 데이터를 요청
            con.setRequestMethod("GET");

            // 2. 응답처리
            // 응답의 유효성 검사
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = // 줄단위로 데이터를 읽기 위해서 버퍼에 담는다. 물론 속도도 빨라지겠지...
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                String temp = "";
                while( (temp = br.readLine()) != null){
                    result.append(temp+"\n");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return result.toString();
    }
}
