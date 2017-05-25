package com.example.jhjun.intentbasic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtCall;
    EditText txtUrl;
    Button btnCall;
    Button btnBrowser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCall = (EditText) findViewById(R.id.txtcall);
        btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = txtCall.getText().toString();
                // Uri : 자원을 가리키는 주소체계
                Uri uri = Uri.parse("tel:" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        // Http http = http.parse(https://www.naver.com/)

        txtUrl = (EditText) findViewById(R.id.txtUrl);
        btnBrowser = (Button) findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = txtUrl.getText().toString();
                Uri uri = Uri.parse("http://"+url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
