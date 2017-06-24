package com.example.jhjun.samplefragment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ListFragment listFragment;
    ViewerFragment viewerFragment;
    int main_position = 0;

    int[] images = {R.drawable.p7, R.drawable.p8, R.drawable.p9};  // 이미지 res 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();  // 메인 액티비티에서 프래그먼트를 다루기 위해서 getSupportFragmentManager() 호출.
        listFragment = new ListFragment();
        // listFragment = (ListFragment)manager.findFragmentById(R.id.listFragment);
        viewerFragment = (ViewerFragment)manager.findFragmentById(R.id.viewerFragment);
    }

    public void onImageSelected(int position){
        viewerFragment.setImage(images[position]);
    }


    public void setData(int position) {
        main_position = position;
        onImageSelected(main_position);
    }

}
