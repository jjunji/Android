package com.example.jhjun.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ListFragment list;
    DetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 프래그먼트 생성
        createFragments();
        // 목록 프로그먼트 화면에 세팅
        addList();
    }

    private void createFragments(){
        list = new ListFragment();
        list.setActivity(this);
        detail = new DetailFragment();
        detail.setActivity(this);
    }

    public void addList(){
        /*
          프래그먼트 화면에 넣기
        */
        // 1. 프래그먼트 트랜잭션 시작하기
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 2. 화면에 프래그먼트 넣기
        transaction.add(R.id.container, list);
        // 3. 트랜잭션 완료
        transaction.commit();
    }

    public void addDetail(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, detail);
        // 트랜잭션 처리 전체를 stack 에 담아놨다가, 안드로이드의 back 버튼으로 뒤로가기를 할 수 있다.
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backToList(){
        super.onBackPressed();
    }
}

// 트랜잭션부터 commit 까지 .. commit 하면 다 올라감