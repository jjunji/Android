package com.example.jhjun.adapterbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> datas = new ArrayList<>(); // datas는 객체
    // 위 필드에서는 초기화만 가능, 코드가 들어가는 로직은 여기서 구현할 수 없다.

    // 다른 액티비티와 데이터를 주고받을때 사용하는 키를 먼저 정의해둔다.
    public static final String DATA_KEY = "ListActivityData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터
        for(int i=0; i<100; i++){
            datas.add("데이터"+i);
        }

        // 2. 어답터                           // super도됨. this : context를 쓰기위한
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,datas);

        // 3. 뷰 > 연결 < 아답터
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Activity 에 값 전달하기
                // 1. 전달받을 목적지 Intent 생성
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                // 2. putExtra 로 값 입력.
                intent.putExtra(DATA_KEY, datas.get(position));
                // 3. intent 를 이용한 Activity 생성 요청
                startActivity(intent);
            }
        });
    }
}

// context 는 abstract로 보면됨.
// 위젯등을 사용할 수 있게 모두 정의해 놓은것(기능과 속성을 모두 정의), 최상위 객체
// 어레이어답터 : 이미 정의되있는 기능을 쓰겠다 -> context가 가지고있음.

// this :