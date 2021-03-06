package com.jjunji.android.logtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jjunji.android.logtest.Util.Logger;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.LOGV("TEST :: VERBOSE");
                Logger.LOGI("TEST :: INFO");
                Logger.LOGW("TEST :: WARN");
                Logger.LOGD("TEST :: DEBUG");
                Logger.LOGE("TEST :: ERROR");

                Log.i(TAG, "로그의 내용=" + "확인하고 싶은 내용 혹은 값");
            }
        });
    }
}
