package com.example.jhjun.threadbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);

        // 1.1 Thread 생성
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.i("Thread test", "Hello Thread");
            }
        };

        // 1.2 Thread 실행
        thread.start(); // run() 함수를 실행시켜준다.

        // 2.1 Thread 생성2
        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("Thread Test","Hello Runnable!");
            }
        };

        // 2.2
        new Thread(thread2).start();

        // 3.2 Thread 실행3
        Thread thread3 = new Thread(new CustomThread());
        thread3.start();

        // 4.2 Runnable 실행
        Thread thread4 = new Thread(new CustomRunnalbe());
        thread4.start();
    }
}

// 3.1 Thread 생성
class CustomThread extends Thread{
    @Override
    public void run() {
        Log.i("Thread Test", "Hello Custom Thread!");
    }
}

// 4.1 Runnable 구현
class CustomRunnalbe implements Runnable{
    @Override
    public void run() {
        Log.i("Thread Test","Hello Custom Runnable!");
    }
}