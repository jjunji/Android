package com.example.jhjun.custompractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datas = new ArrayList<>();  // 초기화를 밖에서 해주는것 좋지 않지만,
                        // 안드로이드에서는 생성자가 없어서 여기에 선언한다.
                        // Oncreate에 선언해버리면 View가 불러올때마다 계속해서 new를 하기 때문에, 밖에서 해준다.

    public static final String DATA_KEY = "ListActivityData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);

        // 데이터
        for(int i=0; i<100; i++){
            datas.add("데이터 "+i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,datas);
        // 3. 뷰 > 연결 < 아답터
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Activity 에 값 전달하기
                // 1. 전달받을 목적지 Intent 생성
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                // 2. putExtra 로 값 입력
                intent.putExtra(DATA_KEY, datas.get(position));
                // 3. intent 를 이용한 Activity 생성 요청
                startActivity(intent);
            }
        });
    }
}
