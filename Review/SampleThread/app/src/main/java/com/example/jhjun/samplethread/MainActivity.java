package com.example.jhjun.samplethread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    //ProgressHandler handler;
    ProgressBar progress;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progress);
        btnStart = (Button) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                thread.start();
            }
        });
        //handler = new ProgressHandler();
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            for(int i=0; i<=100; i++){
                progress.setProgress(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
/*    public class ProgressHandler extends Handler{
        public void handleMessage(Message msg){

        }
    }*/
}
