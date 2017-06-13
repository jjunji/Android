package com.example.jhjun.httpdataconnection;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jhjun.httpdataconnection.domain.Data;
import com.example.jhjun.httpdataconnection.domain.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.jhjun.httpdataconnection.R.id.textView;

public class MainActivity extends AppCompatActivity
        implements TaskInterface, OnMapReadyCallback {

    /* 기초정보
        url : http://openAPI.seoul.go.kr:8088/4c425976676b6f643437665377554c/json/SearchPublicToiletPOIService/1/5/
        인증키 : 4c425976676b6f643437665377554c
     */
    static final String URL_PREFIX = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_CERT = "4c425976676b6f643437665377554c";
    static final String URL_MID = "/json/SearchPublicToiletPOIService/";
    // 한 페이지에 불러오는 데이터 수
    static final int PAGE_OFFSET = 10;

    int pageBegin = 1;
    int pageEnd = 10;

    ListView listView;
    TextView textView;
    String url = "";

    // 아답터
    ArrayAdapter<String> adapter;

    // 아답터에서 사용할 데이터 공간
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        // 맵을 세팅
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.mapView);
        // 로드되면 OnReady
        mapFragment.getMapAsync(this);


        // 데이터 - 위에서 공간 할당 됨
        // 아답터
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);

        // 최초 호출시 첫번째 집합을 불러온다.
    }

    private void setPage(int page) {
        pageEnd = page * PAGE_OFFSET;
        pageBegin = pageEnd - PAGE_OFFSET + 1;
    }

    private void setUrl(int begin, int end) {
        // String
        // StringBuffer
        // StringBuilder

        // String 연산.........
        // String result = "문자열" + "문자열" + "문자열";
        //                  ----------------
        //                    메모리공간 할당
        //                   ---------------------------
        //                         메모리공간 할당
        url = URL_PREFIX + URL_CERT + URL_MID + begin + "/" + end;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void postExecute(String jsonString) {
        Gson gson = new Gson();
        // 1. json String -> class 로 변환
        Data data = gson.fromJson(jsonString, Data.class);

        // 총개수를 화면에 세팅
        textView.setText("총 개수 : " + data.getSearchPublicToiletPOIService().getList_total_count());
        // 건물의 이름을 listView 에 세팅

        Row rows[] = data.getSearchPublicToiletPOIService().getRow();

        // 네트웍에서 가져온 데이터를 꺼내서 datas에 담아준다.
        for (Row row : rows) {
            datas.add(row.getFNAME());
        }
        // 그리고 adapter 를 갱신해준다.
        adapter.notifyDataSetChanged();
    }

    GoogleMap myMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        setPage(1);
        setUrl(pageBegin, pageEnd);
        Remote.newTask(this);
    }
}