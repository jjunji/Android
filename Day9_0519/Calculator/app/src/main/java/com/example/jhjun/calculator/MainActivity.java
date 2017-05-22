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

    String tempInt = "";
    String tempOper = "";
    String tempTot = "";

    String[] array;

    String numPre = "";
    String numNext = "";
    int realNum = 0;

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
                tempInt = tempInt + "0";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn1:
                tempInt = tempInt + "1";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn2:
                tempInt = tempInt + "2";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn3:
                tempInt = tempInt + "3";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn4:
                tempInt = tempInt + "4";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn5:
                tempInt = tempInt + "5";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn6:
                tempInt = tempInt + "6";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn7:
                tempInt = tempInt + "7";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn8:
                tempInt = tempInt + "8";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;
            case R.id.btn9:
                tempInt = tempInt + "9";
                tempTot = tempTot + tempInt;
                tempInt = "";
                result.setText(tempTot);
                break;

            case R.id.btnPlus:
                tempOper = "+";
                tempTot = tempTot + tempOper;
                result.setText(tempTot);
                tempOper ="";
                break;

            case R.id.btnMinus:
                tempOper = "-";
                tempTot = tempTot + tempOper;
                result.setText(tempTot);
                tempOper ="";
                break;

            case R.id.btnMulti:
                tempOper = "*";
                tempTot = tempTot + tempOper;
                result.setText(tempTot);
                tempOper ="";
                break;

            case R.id.btnDiv:
                tempOper = "/";
                tempTot = tempTot + tempOper;
                result.setText(tempTot);
                tempOper ="";
                break;

            case R.id.btnClean:
                tempInt = "";
                tempOper = "";
                tempTot = "";
                result.setText(tempTot);
                break;

            case R.id.btnResult:
                array = tempTot.split("");

                for(int i=0; i<array.length; i++){
                    if(array[i].equals("+")){
                        for(int j=i+1; j<array.length; j++){
                            numNext = numNext + array[j];
                        }
                        break;
                    }
                    numPre = numPre + array[i];
                }

                realNum = Integer.getInteger(numPre) + Integer.getInteger(numNext);

                result.setText(realNum);

                break;
        }
    }
}
