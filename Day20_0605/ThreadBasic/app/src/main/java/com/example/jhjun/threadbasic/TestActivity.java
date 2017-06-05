package com.example.jhjun.threadbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // 1. 버튼을 클릭하면 1부터 100까지 출력하는 함수를 실행.
        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogThread().start();
            }
        });

        // 2. Thread에서 1부터 100까지 출력하는 함수를 실행
        new LogThread().start();

        // 3. 위의 Thread 클래스의 실행순서를 1번과 바꿔서 실행
    }
                                // 어디서 호출했는지 확인하기 위해 caller를 받는다.
    public void print100T(String caller){
        for(int i=0; i<100; i++){
            Log.i("Thread Test", caller + " : Current Number===========" + i);
            //System.out.println(caller + " : Current Number===========" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class LogThread extends Thread {
        @Override
        public void run() {
            count++;
            print100T("SubThread" + count);
        }
    }
}
