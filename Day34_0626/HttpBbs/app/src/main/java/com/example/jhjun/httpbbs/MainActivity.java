package com.example.jhjun.httpbbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }



    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<Bbs> datas = Loader.getData(this);

        Adapter adapter = new Adapter(datas);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


// interface를 쓰지 않으면 호출된 곳으로 값을 전달할 방법이 없음..?