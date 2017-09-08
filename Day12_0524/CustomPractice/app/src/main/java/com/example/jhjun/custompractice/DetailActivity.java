package com.example.jhjun.custompractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detail = (TextView) findViewById(R.id.detail);

        // Activity 에서 넘어온 값 처리하기
        // 1. intent 를 꺼낸다
        Intent intent = getIntent();
        // 2. 값의 묶음인 bundle 을 꺼낸다
        Bundle bundle = intent.getExtras();  // bundle : 데이터의 모음
        String result = "";

        // 3. bundle 이 있는지 유효성 검사를 한다.
        if(bundle != null){
            // 3.1 bundle 이 있으면 값을 꺼내서 변수에 담는다
            result = bundle.getString(ListActivity.DATA_KEY);
        }
        detail.setText(result);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
