package com.example.jhjun.samplejavathread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int value = 0;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.showBtn);
        final TextView textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("스레드에서 받은 값 : " + value);
            }
        });
    }

    protected void onResume(){
        super.onResume();

        running = true;
        Thread thread1 = new BackGroundThread();
        thread1.start();
    }

    protected void onPause(){
        super.onPause();

        running = false;
        value = 0;

    }

    class BackGroundThread extends Thread{
        public void run(){
            while(running){
                try {
                    Thread.sleep(1000);
                    value++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
