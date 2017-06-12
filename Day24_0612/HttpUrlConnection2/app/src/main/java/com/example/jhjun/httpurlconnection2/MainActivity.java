package com.example.jhjun.httpurlconnection2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String url = "http://google.com";
            String result = getData(url);
            Log.i("Result", result);
        } catch (Exception e) {
            Log.e("Network",e.toString());
            Toast.makeText(this,"네트워크 오류"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public String getData(String url) throws Exception{

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

    public void newTask(String url){
        new AsyncTask<String,Void,Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    String result = getData(params[0]);
                    Log.i("Network", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
    }
}
