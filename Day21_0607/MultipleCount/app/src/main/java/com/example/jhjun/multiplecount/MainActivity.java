package com.example.jhjun.multiplecount;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textViews[] = new TextView[4];

    public static final int SET_COUNT = 99;

    // 서브 thread로 부터 메시지를 전달받을 Handler를 생성한다.  메시지 통신

    Handler handler = new Handler(){
        // 서브 스레드에서 메시지를 전달하면 handleMessage 함수가 동작한다.
        @Override
        public void handleMessage(Message msg) {
           // super.handleMessage(msg);
            switch (msg.what){
                case SET_COUNT :
                    textViews[msg.arg1].setText(""+msg.arg2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0 ; i<4 ; i++){
            // 텍스트로 아이디 가져오기
            int resId = getResources().getIdentifier("textView"+(i+1) , "id", getPackageName());
            textViews[i] = (TextView) findViewById(resId);
        }

        // 생성부                                    //this : runUitread를 사용하기 위한.  //
        Counter counter1 = new Counter(0, handler);
       // Counter counter2 = new Counter(textViews[1], this);
       // Counter counter3 = new Counter(textViews[2], this);
       // Counter counter4 = new Counter(textViews[3], this);

        // 실행부
        counter1.start();
        //counter2.start();
        //counter3.start();
        //counter4.start();
    }
}

class Counter extends Thread{
    Handler handler;
    int textViewIndex;
    int count=0;

    public Counter(int index, Handler handler){
        this.handler = handler;
        this.textViewIndex = index;
    }

    @Override
    public void run(){

        for(int i=0 ; i<10 ; i++){
            // 서브 thread 에서 UI 를 조작하기 위해 핸들러를 통해 메시지를 전달한다.
            count++;
            Message msg = new Message();
            msg.what = MainActivity.SET_COUNT;
            msg.arg1 = textViewIndex;
            msg.arg2 = count;

            handler.sendMessage(msg);

            // Log 는 UI가 아니기 때문에 메인 thread로 옮기지 않아도 동작한다.
            //Log.e("Count","=============="+count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}