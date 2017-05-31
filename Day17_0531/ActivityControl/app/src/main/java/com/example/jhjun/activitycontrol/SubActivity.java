package com.example.jhjun.activitycontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        /*
        위 버튼을 클릭해서 startActivity를 실행하면 intent에 값이 없기 때문에 null 발생하고 아래 코드에서
        intent.getExtras() -> null.getExtras() 는 없으므로 nullPointException 이 발생. (null.)은 존재 할 수 없음.
        -> try-catch로 처리하면 연산 속도가 느리다. ???
        -> 예상 가능한 오류는 Exception으로 처리.
         */

        // 1. 이전 액티비티에서 넘어온 intent 객체
        final Intent intent = getIntent();  // 여기는 null 이 안된다.
        // 2. 값의 묶음을 꺼낸다.
        Bundle bundle  = intent.getExtras(); // 여기는 전달된 값이 없으면 null 이 된다.
        // 3. 단일 값을 꺼낸다. 꺼내기 전에 null 체크를 해줘야함.
        if(bundle != null){
            value = bundle.getString("key");
            // 3.1 값이 있으면 textView에 출력한다.
            textView.setText(value);
        }
        // 위에 두줄(2,3번)을 합쳐놓은 method
        // 자체적으로 bundle에 대한 null 처리 로직을 포함하고 있다.
        // String value = intent.getStringExtra("key");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity에서 넘겨받은 값을 int로 변환
                int num1 = Integer.parseInt(value);
                // 현재 Activity에 입력된 값을 받아서
                String temp = editText.getText().toString();
                // int 로 변환
                int num2 = Integer.parseInt(temp);

                int result = num1 + num2;

                /* 값 반환하기 */

                // 결과값을 intent에 담아서     //                              시스템 자원을 이용하는 것이 아니고
                Intent intent = new Intent(); // context를 넘기지 얂는다. 왜? -> 이미 있는 액티비티에 값만 넘겨주는 것이기 때문?
                intent.putExtra("result", result); //

                // setResult에 넘겨준다.
                setResult(RESULT_OK, intent); // setResult : 현재 액티비티의 intent에 값만 담아 놓는 것. finish()하면 onResume 처럼 시스템에서 호출.

                // 현재 activity를 종료한다.
                finish();  //finish()를 하면 onActivityResult 호출
            }
        });
    }
}


/*
    if

 */