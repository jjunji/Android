package com.example.jhjun.layouttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRelative, btnLinear, btnGrid, btnSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRelative = (Button) findViewById(R.id.btnRelative);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnSpinner = (Button) findViewById(R.id.btnSpinner);

        btnRelative.setOnClickListener(this);
        btnLinear.setOnClickListener(this);
        btnGrid.setOnClickListener(this);
        btnSpinner.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRelative:
                Intent intent = new Intent(this, RelativeActivity.class);   // this : 시스템 자원을 사용하기 때문에 사용.
                startActivity(intent);
                break;
            case R.id.btnLinear:
                Intent intent = new Intent(this, LinearActivity.class);
                startActivity(intent);
                break;
            case R.id.btnGrid:
                Intent intent = new Intent(this, LinearActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSpinner:
                Intent intent = new Intent(this, LinearActivity.class);
                startActivity(intent);
                break;
        }
    }
}
