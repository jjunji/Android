package com.example.jhjun.servicebasic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
        Log.i("MyService","Constructor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService","onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i("MyService","onBind");
        return null;
    }

    public void print(String string){
        Log.i("MyService",string);
    }

    // 바인더를 통해서
    public class MyBinder extends Binder{
       public MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService","onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService","onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyService","onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i("MyService","onRebind");

        super.onRebind(intent);
    }
}
