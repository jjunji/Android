package com.example.jhjun.fragment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListFragment list;
    DetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragments();
        addList();
    }
    private void createFragments(){
        list = new ListFragment();
        list.setActivity(this);
        detail = new DetailFragment();
        detail.setActivity(this);

    }

    public void addList(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 트랜잭션 연결
        transaction.add(R.id.container, list); // add
        transaction.commit();  // 실행
    }

    public void addDetail(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, detail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backToList(){
        super.onBackPressed();
    }
}
