package com.example.jhjun.httpbbs;

import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Loader.CallBack{

    RecyclerView recyclerView;
    Adapter adapter;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPost = (Button) findViewById(R.id.btnPost);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Loader loader = new Loader();
        loader.getData("http://192.168.10.217:8080/bbs/json/list", this); // 여기서 서브 스레드 시작.
        adapter = new Adapter();  // 어답터 객체 생성
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setData(List<Bbs> list) {
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }
}
// interface를 쓰지 않으면 호출된 곳으로 값을 전달할 방법이 없음..?