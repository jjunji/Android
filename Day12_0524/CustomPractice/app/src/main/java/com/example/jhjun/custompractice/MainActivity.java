package com.example.jhjun.custompractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;

    String datas[] = {"선택하세요", "ListView", "CustomList", "GridView"}; // 스피너에 표시할 항목을 정의.

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //(캐스팅) resource에 있는 id가 spinner인 위젯을 사용할 것인데, 그 위젯은 Spinner이다. 라고 알려주기 위함.
        spinner = (Spinner) findViewById(R.id.spinner); // 스피너 객체를 메소드를 통해 찾아 변수에 할당.

        // List뷰에 데이터 연결하기
        // 1. 데이터 정의 -> datas 정의함.
        // 2. 어답터 생성 -> 뷰에 넣을 데이터를 컨트롤, 데이터를 뿌려줌.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        // 제네릭                                // this : context에서 자원을 쓰겠다고 했던 것과 같은 이치
        // 3. 뷰에 어답터 연결
        spinner.setAdapter(adapter);
        // 어답터를 쓰는 이유 : 데이터의 변경 사항이 있어도 아답터가 처리를 해준다. (레이아웃 등) , 완벽한 mvp구조
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 스위치문에서는 지역변수를 전역변수로 고쳐야함. -> 지역변수의 범위는 스코프 안이기 때문.
            Intent intent;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case LIST :             // this만 쓰면 에러.
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        break;
                    case GRID :
                        intent = new Intent(MainActivity.this, GridActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM :
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
                        startActivity(intent);
                        break;
                    default: // 선택하세요.
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
