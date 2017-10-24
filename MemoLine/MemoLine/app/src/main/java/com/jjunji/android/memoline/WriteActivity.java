package com.jjunji.android.memoline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import static com.jjunji.android.memoline.R.color.colorAccent;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinedEditText et = new LinedEditText(this, null);
        //et.setText("The name of our country is Bangladesh. I am proud of my country :)");
        et.setLayoutParams(textViewLayoutParams);
        et.setKeyListener(null);

        ll.addView(et);
        this.setContentView(ll);
    }
}

