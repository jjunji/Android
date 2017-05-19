package com.example.jhjun.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    Button btnClean;
    Button btnPlus, btnMinus, btnMulti, btnDiv;
    TextView result;

    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 선언
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);

        btnClean = (Button) findViewById(R.id.btnClean);

        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMulti = (Button) findViewById(R.id.btnMulti);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        result = (TextView) findViewById(R.id.result);
        //result.setText("0");

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);

        btnClean.setOnClickListener(this);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn0:
                temp = temp + "0";
                result.setText(temp);
                break;
            case R.id.btn1:
                temp = temp + "1";
                result.setText(temp);
                break;
            case R.id.btn2:
                temp = temp + "2";
                result.setText(temp);
                break;
            case R.id.btn3:
                temp = temp + "3";
                result.setText(temp);
                break;
            case R.id.btn4:
                temp = temp + "4";
                result.setText(temp);
                break;
            case R.id.btn5:
                temp = temp + "5";
                result.setText(temp);
                break;
            case R.id.btn6:
                temp = temp + "6";
                result.setText(temp);
                break;
            case R.id.btn7:
                temp = temp + "7";
                result.setText(temp);
                break;
            case R.id.btn8:
                temp = temp + "8";
                result.setText(temp);
                break;
            case R.id.btn9:
                temp = temp + "9";
                result.setText(temp);
                break;

            case R.id.btnPlus:
                temp = temp + "+";
                result.setText(temp);
                break;

            case R.id.btnMinus:
                temp = temp + "-";
                result.setText(temp);
                break;

            case R.id.btnMulti:
                temp = temp + "*";
                result.setText(temp);
                break;

            case R.id.btnDiv:
                temp = temp + "/";
                result.setText(temp);
                break;

            case R.id.btnClean:
                result.setText("0");
                break;

            case R.id.btnResult:
                //result.setText(temp);
                break;
        }
    }
}
