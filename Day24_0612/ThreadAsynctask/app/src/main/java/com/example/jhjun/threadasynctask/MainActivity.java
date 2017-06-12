package com.example.jhjun.threadasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public static final int SET_DONE = 1;

    TextView textView;
    ProgressDialog progress;

    // 스레드에서 호출하기 위한 Handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case SET_DONE :
                    setDone();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //run();
                runAsync();
            }
        });
        // 화면의 진행상태를 표시.
        // 프로그레스 다이얼로 정의.
        progress = new ProgressDialog(this);   // getBaseContext와 Application은 안되고 this만 되는 이유가 뭘까.
        progress.setTitle("진행중...");                    // progress를 사용할 때 기본적으로 세팅하는 함수.
        progress.setMessage("진행중 표시되는 메시지입니다.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void setDone(){
        textView.setText("Done!!");
        // 프로그레스 창을 해제
        progress.dismiss();
    }

/*    private void run(){
        new Thread(){
            public void run(){
                // 10초 후에
                try {
                    Thread.sleep(10000);
                    // Main Ui에 현재 스레드가 접근 할 수 없으므로
                    // handler를 통해 호출해준다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // 10초 후에 위 함수 실행하려면 스레드가 내부적으로 돌아야 한다.
        // textView.setText("Done!!"); -> 여기 쓰면 바로 떠버린다. 스레드 안으로 넣어야함.
    }*/

    private void runAsync(){
        new AsyncTask<String, Integer, Float>(){
            // 제네릭 타입
            /*              1 - doInBackground 의 인자
                            2 - onProgressUpdate 의 인자
                            3 - doInBackground 의 리턴타입 ,
                            doInBackground 가 호출되기 전에 먼저 호출된다.
            */
            @Override
            protected Float doInBackground(String... params) { // 데이터를 처리...
                // 10초 후에
                try {
                    for(int i=0 ; i<10 ; i++) {
                        publishProgress(i*10); // <- onProgressUpdate 를 주기적으로 업데이트 해준다.
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1000.4f;
            }

            // doInBackground 가 처리되고 나서 호출된다.
            @Override
            protected void onPostExecute(Float result) {
                Log.e("AsyncTask","doInBackground의 결과값="+result);
                // 결과값을 메인 UI 에 세팅하는 로직을 여기에 작성한다.
                setDone();
                progress.dismiss();
            }

            // 주기적으로 doInBackground 에서 호출이 가능한 함수
            @Override
            protected void onProgressUpdate(Integer... values) {
                progress.setMessage("진행율 = " +values[0]+ "%");
            }
        }.execute("안녕", "하세요"); // <- doInBackground 를 실행

    }

    private void runThread() {
        // 프로그래스 창을 호출
        progress.show();
        CustomThread thread = new CustomThread(handler);
        thread.start();
    }
}

class CustomThread extends Thread{

    Handler handler; // 메인 엑티비티의 전역변수 handler.

    // 생성자를 통해 메인액티비티의 handler와 연결.
    public CustomThread(Handler handler){
        this.handler = handler;
    }

    public void run(){
        try {
            Thread.sleep(10000);
            // Main Ui에 현재 스레드가 접근 할 수 없으므로
            // handler를 통해 호출해준다.

//            Message msg = new Message();
//            msg.what = MainActivity.SET_DONE;      // -> 이 3줄을 한줄로 바꾸면 아래 한줄.
//            handler.sendMessage(msg);

            handler.sendEmptyMessage(MainActivity.SET_DONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}