package com.example.jhjun.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 클릭리스너 구현하는 방법 세가지
 * 1. 위젯을 사용하는 객체가 상속받아서 구현
 * 2. 객체내에서 변수로 생성
 * 3. setOnclickListener 함수에 익명 객체로 전달
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.btnClick);

        // 1 번형태로 구현
        btn.setOnClickListener(this);

        // 2 번형태로 구현 - 아래에 구현된 리스너를 등록해준다.
        btn.setOnClickListener(listener);

        // 3 번형태로 구현
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("안녕 안드로이드!!!");
            }
        });
    }

    // 1번 형태
    @Override
    public void onClick(View v) {
        tv.setText("안녕 안드로이드!!!");
    }

    // 2번 형태
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tv.setText("안녕 안드로이드!!!");
        }
    };

}