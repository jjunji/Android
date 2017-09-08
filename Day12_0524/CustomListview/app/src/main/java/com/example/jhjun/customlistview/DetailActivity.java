package com.example.jhjun.customlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    //    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.imageView);
        //textView = (TextView) findViewById(R.id.textView);

        // Activity 에서 넘어온 값 처리하기
        // 1. intent 를 꺼낸다
        Intent intent = getIntent();
        // 2. 값의 묶음인 bundle 을 꺼낸다
        Bundle bundle = intent.getExtras(); // 데이터의 모음
        int position = -1;
        // 3. bundle 이 있는지 유효성 검사를 한다.
        if(bundle != null) {
            // 3.1 bundle 이 있으면 값을 꺼내서 변수에 담는다
            position = bundle.getInt(MainActivity.DATA_KEY);
        }
        Log.e("DATA","===========position="+position);
        if(position > -1) {
            Log.e("DATA","===========DATA_RES_ID="+bundle.getInt(MainActivity.DATA_RES_ID));
            imageView.setImageResource(bundle.getInt(MainActivity.DATA_RES_ID));
            //textView.setText(bundle.getString(MainActivity.DATA_TITLE));
        }

//        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}