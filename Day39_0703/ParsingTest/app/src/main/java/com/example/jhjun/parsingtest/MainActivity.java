package com.example.jhjun.parsingtest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.jhjun.parsingtest.Remote.getData;

public class MainActivity extends AppCompatActivity implements TaskInterface{

      /* 기초정보
        url : http://openAPI.seoul.go.kr:8088/(인증키)/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/5/0201/1/1/
        인증키 : 56796c71646c706e38315841504774
     */
    static final String URL_PREFIX = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_CERT   = "56796c71646c706e38315841504774";
    static final String URL_MID    = "/json/SearchArrivalTimeOfLine2SubwayByIDService/";

    // 한 페이지에 불러오는 데이터 수
    static final int PAGE_OFFSET = 10;
    int page = 1;

    ListView listView;
    TextView textView;
    String url = "";

    // 아답터에서 사용할 데이터 공간
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        Task.newTask(this);
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
