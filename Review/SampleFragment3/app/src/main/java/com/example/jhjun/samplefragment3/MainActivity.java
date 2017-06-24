package com.example.jhjun.samplefragment3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ListFragment listFragment;
    ImageFragment imageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListFragment();
        imageFragment = new ImageFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        FragmentManager fragmentManager = getSupportFragmentManager(); // 메인액티비티에서 프래그먼트를 다루기 위해
        fragmentManager.beginTransaction();                            // getSupportFragmentManager() 호출.

        FragmentTransaction transaction1.



        // fragmentManager.findFragmentById(R.)

        //fragmentManager.
    }
}
