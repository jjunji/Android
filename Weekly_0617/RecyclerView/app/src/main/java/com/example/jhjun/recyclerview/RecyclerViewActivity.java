package com.example.jhjun.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SimpleStringAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.simple_recycler_view);

        // RecyclerView 자체의 크기가 변하지 않는 것을 알고 있을 때
        // 이 옵션을 설정하면 성능이 개선됩니다
        recyclerView.setHasFixedSize(true);

        // Adapter를 설정합니다
        adapter = new SimpleStringAdapter(DummyDataGenerator.generateStringListData());
        adapter.setOnItemViewClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // item이 클릭되면 호출됩니다
                Toast.makeText(v.getContext(), "Position:" + recyclerView.getChildAdapterPosition(v)
                        + "가 클릭됐습니다", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, RecyclerViewActivity.class);
    }
}