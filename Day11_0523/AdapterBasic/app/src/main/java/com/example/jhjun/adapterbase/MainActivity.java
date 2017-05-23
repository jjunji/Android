package com.example.jhjun.adapterbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"선택하세요", "ListView", "CustomList", "GridView", "RecyclerView"};

    // 스위치문 쉽게 사용하기 위한
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List뷰에 데이터 연결하기
        // 1. 데이터 정의 -> datas 정의함.
        // 2. 어답터 생성  // 어답터는 곧 데이터?
        // 3. 뷰에 어답터 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        // 제네릭                                            // this : context에서 자원을 쓰겠다고 했던 것과 같은 이치
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // 어답터가 데이터를 뿌려주고 컨트롤하는 역할.
        spinner.setAdapter(adapter);
        // 어답터를 쓰는 이유 : 데이터가 변경되도
        // 어답터 : 완벽한 mvp구조
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // 스위치문에서는 지역변수를 전역변수로 고쳐야함. -> 지역변수의 범위는 스코프 안이기 때문.

                switch (position){
                    case LIST:                    // this만 쓰면 에러나는 이유 : 메인엑티비티의 this라고 명시하지 않았기 때문..
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
                        break;
                    default: // 선택하세요
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
