package com.example.jhjun.httpbbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener, DataSender.CallBack {
    EditText txtTitle, txtAuthor, txtContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtAuthor = (EditText) findViewById(R.id.txtAuthor);
        txtContent = (EditText) findViewById(R.id.txtContent);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 1. 위젯에서 값 가져오기
        String title = txtTitle.getText().toString();
        String author = txtAuthor.getText().toString();
        String content = txtContent.getText().toString();

        // 2. Bbs 객체에 값 담기 - id 는 서버에서 자동으로 생성하므로 넘기지 않는다
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 3. Gson 으로 json 만들기
        Gson gson = new Gson();
        String jsonString = gson.toJson(bbs);
        // 확인
        Log.d("PostData",jsonString);

        // 4. json 전송
        DataSender sender = new DataSender();
        String url = "http://192.168.10.217:8080/bbs/insert.jsp";
        sender.sendData(url, jsonString, this);
    }

    @Override
    public void call(boolean result) {
        Log.d("WriteActivity", "전송결과="+result);
    }
}
