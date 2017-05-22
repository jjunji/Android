package com.example.jhjun.calculator2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtPreview,txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);

        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnResult).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);

        txtPreview = (TextView) findViewById(R.id.txtPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn1: setPreview(1); break;
            case R.id.btn2: setPreview(2); break;
            case R.id.btn3: setPreview(3); break;
            case R.id.btn4: setPreview(4); break;
            case R.id.btn5: setPreview(5); break;
            case R.id.btn6: setPreview(6); break;
            case R.id.btn7: setPreview(7); break;
            case R.id.btn8: setPreview(8); break;
            case R.id.btn9: setPreview(9); break;
            case R.id.btn0: setPreview(0); break;

            case R.id.btnPlus: setPreview("+"); break;
            case R.id.btnMinus: setPreview("-"); break;
            case R.id.btnMultiply: setPreview("*"); break;
            case R.id.btnDivide: setPreview("/"); break;
            case R.id.btnResult:
                result();
                break;
            case R.id.btnClear:
                clear();
                break;
        }
    }

    private void result(){
        String current = txtPreview.getText().toString();
        txtResult.setText(calculate(current));
    }
    private String calculate(String preview){

        // TODO 문자열을 쪼갠후 우선순위에 따라 연산하시오
        // 1. 문자열을 정규식으로 * / + - 을 이용해서 배열로 자른다                       (0) (1) (2) (3) (4) (5) (6)
        String splited[] = preview.split("(?<=[*/+-])|(?=[*/+-])");  // ex) 30+5x2/2  ->  30 | + | 5 | X | 2 | / | 2
        ArrayList<String> splited2 =  new ArrayList<>();

        for(int i=0; i < splited.length; i++){
            splited2.add(splited[i]);
        }

        //while (splited2.size() > 0) {
            for (int i = 0; i < splited2.size(); i++) {
                String temp = "";
                int temp_int = 0;

                if (splited2.get(i).equals("*")) {
                    //int a = Integer.getInteger(splited[i-1]) * Integer.getInteger(splited[i+1]);
                    temp_int = Integer.parseInt(splited2.get(i - 1)) * Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if (splited2.get(i).equals("/")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) / Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if(splited2.get(i).equals("+")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) + Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if (splited2.get(i).equals("-")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) - Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                }
            }
            return splited2.get(0);
    }


    // 0 값으로 계산될 때 예외처리 추가하기.

    private void setPreview(int number){
        String current = txtPreview.getText().toString();
        txtPreview.setText(current + number);
    }

    private void setPreview(String str){
        String current = txtPreview.getText().toString();
        txtPreview.setText(current + str);
    }

    private void clear(){
        txtPreview.setText("");
        txtResult.setText("0");
    }
}