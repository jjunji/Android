package com.example.jhjun.parsingtest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jhjun.parsingtest.domain.Data;
import com.example.jhjun.parsingtest.domain.Row;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskInterface{

      /* 기초정보
        url : http://openAPI.seoul.go.kr:8088/(인증키)/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/5/0201/1/1/
        인증키 : 56796c71646c706e38315841504774
     */

      /*
      
       */


    static final String URL_PREFIX = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_CERT   = "56796c71646c706e38315841504774/";
    static final String URL_MID    = "json/SearchArrivalTimeOfLine2SubwayByIDService/";
    // 1/40/0236/1/1/

    int pageBegin = 1;
    int pageEnd = 10;
    String code = "0236"; // 전철 역 코드
    String weektag = "1"; // 1: 평일
    String inouttag = "1"; // 1: 상행, 2: 하행

    ListView listView;
    TextView textView;
    String url = "";

    // 어답터
    ArrayAdapter<String> adapter;

    // 어답터에서 사용할 데이터 공간
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);


        setUrl(pageBegin, pageEnd, code, weektag, inouttag);

        Remote.newTask(this);
    }

    private void setUrl(int begin, int end, String code, String weektag, String inouttag){
        url = URL_PREFIX + URL_CERT + URL_MID +begin+"/"+end +"/" + code + "/" + weektag + "/" + inouttag + "/";
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void postExecute(String jsonString){
        Gson gson = new Gson();
        // 1. json String -> class 로 변환
        Data data = gson.fromJson(jsonString, Data.class);

        Log.i("data : ","data : " + data);
        Row rows[] = data.getSearchArrivalTimeOfLine2SubwayByIDService().getRow();

        // 네트웍에서 가져온 데이터를 꺼내서 datas에 담아준다.
        for(Row row : rows){
            datas.add(row.getSUBWAYNAME());
        }

        // 그리고 adapter 를 갱신해준다.
        adapter.notifyDataSetChanged();
    }
}
