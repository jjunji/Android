package com.example.jhjun.memo_re;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    String id = "파일명.txt";
    String memo = "메모내용";

    DetailView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailView = new DetailView(this);
    }

    // 저장
    public void save() {
        Toast.makeText(this, "저장하였습니다.", Toast.LENGTH_SHORT).show();
        // 저장후 상세보기 액티비티를 종료하여 목록으로 돌아간다.
        finish();
    }

    // 뒤로가기 (취소)
    public void back(){
        // 저장하지 않고 목록으로 돌아간다.
        finish();
    }

    // 첨부
    public void attach(){

    }
}