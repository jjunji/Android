package com.example.jhjun.rxbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // 서브젝트 생성
        subject = new Subject();
        // 서브젝트 동작 시작.
        subject.start(); // 메인 쓰레드와 서브 쓰레드가 따로 돌아가고,
        //subject.run(); // run()을 하면 run()을 먼저 처리하고 끝나면 나머지 작업을 수행한다. -> run()을 하면 작업이 끝날때까지 아무 작업을 할 수 없음. 심지어 뷰도 안그려졌다.
    }                     // initView() 가 위에 있음에도 뷰가 안그려졌다는 것은 컴파일시, 우선순위가 있어서 스레드를 먼저 찾아 수행한다는 것을 알 수 있음.

    int count = 0;

    private void initView() {
        button = (Button) findViewById(R.id.button);
        // 버튼이 클릭될 때 마다 Subject에 옵저버를 추가
        button.setOnClickListener(v->{
            count++;
            subject.addObserver(new Subject.Observer() {
                String myName = "Observer "+count;
                @Override
                public void notification(String msg) {
                    System.out.println(myName + ":" + msg);
                }
            });
        });
    }
}
