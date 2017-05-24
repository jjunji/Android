package com.example.jhjun.viewholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    String datas[] = {"선택하세요","ListView", "CustomList", "GridView", "RecyclerView"};
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        // List 뷰에 데이터 연결하기
        // 1. 데이터 정의
        // datas 정의함
        // 2. 아답터 생성
        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datas);
        // 3. 뷰에 아답터 연결
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SpinnerValue", datas[position]+", id="+id);
                Intent intent;
                switch(position) {
                    case LIST:
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case GRID:
                        intent = new Intent(MainActivity.this, GridActivity.class);
                        startActivity(intent);
                        break;
                    case RECYCLER:
                        intent = new Intent(MainActivity.this, RecyclerActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM:
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
                        startActivity(intent);
                    default: // 선택하세요

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
