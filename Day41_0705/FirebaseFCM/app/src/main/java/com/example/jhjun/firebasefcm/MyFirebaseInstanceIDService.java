package com.example.jhjun.firebasefcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyFirebaseInstanceIDService extends Service {
    public MyFirebaseInstanceIDService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
