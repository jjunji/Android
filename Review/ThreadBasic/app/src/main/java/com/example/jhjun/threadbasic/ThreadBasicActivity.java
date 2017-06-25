package com.example.jhjun.threadbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);

        Thread thread = new Thread() {
            @Override
            public void run() {
                Log.i("Thread Test : ", "Hello Thread!");
            }
        };
        thread.start();

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("Thread Test : ", "Hello Runnable");
            }
        };
        // thread2.start(); -> 안됨.
        new Thread(thread2).start();

        Thread thread3 = new Thread(new CustomThread());


        thread3.start();

        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();

    }
}

class CustomThread extends Thread{
    @Override
    public void run() {
        Log.i("Thread Test : ", "Hello Custom");
    }
}

class CustomRunnable implements Runnable{
    @Override
    public void run() {
        Log.i("Thread Test : ", "Hello Custom Runnable");
    }
}