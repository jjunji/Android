package com.example.jhjun.servicebasic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnBind).setOnClickListener(this);
        findViewById(R.id.btnUnbind).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()){
            case R.id.btnStart :
                startService(intent);
                break;
            case R.id.btnStop :
                stopService(intent);
                break;
            case R.id.btnBind :    // 엑티비와 데이터 주고 받고 싶을 때는 커넥션을 통해서 해라.
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbind :
                unbindService(connection);
                break;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        // 서비스와 연결되는 순간 호출
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d("MainActivity", "onServiceConnected");

            // binder 를 통해 서비스에 접근한다.
            MyService.MyBinder iBinder = (MyService.MyBinder) binder;
            MyService service = iBinder.getService();
            service.print("연결되었습니다.");
        }

        // 일반적인 상황에서 호출되지 않는다. onDestroy에서는 호출되지 않는다.
        // 서비스가 도중에 끊기거나 연결이 중단되면 호출된다.
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected");
        }
    };



}
