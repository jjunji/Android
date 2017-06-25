package com.example.jhjun.handlersample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    TextView textView;
    ProgressBar progress;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        progress = (ProgressBar) findViewById(R.id.progress);

        handler = new ProgressHandler();
    }

    @Override
    public void onStart() {
        super.onStart();

        progress.setProgress(0);
        Thread thread1 = new Thread(){  // 스레드 객체 생성시 Runnable을 파라미터로 넘긴 이유...
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        Thread.sleep(1000);

                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){

                }
            }
        };
//        isRunning = true;
        thread1.start();
    }

/*    @Override
    protected void onStop() {
        super.onStop();

        isRunning = false;
    }*/

    public class ProgressHandler extends Handler{
        public void handleMessage(Message msg){
            progress.incrementProgressBy(5);
            if(progress.getProgress() == progress.getMax()) {
                textView.setText("Done");
            }else{
                textView.setText("Working..."+ progress.getProgress());
            }
        }
    }
}
