package com.example.jhjun.airbnbsearch;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Button btnCheckin, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        String inText = "<font color='#888888'>"+getString(R.string.hint_start_date)
                +"</font> <br> <font color=\"#fd5a5f\">"+ getString(R.string.hint_select_date)+"</font>";
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            btnCheckin.setText(Html.fromHtml(inText), TextView.BufferType.SPANNABLE);
        }else {
            // 누가 이상 버전은 fromHtml 의 두 번째 인자로 Html.FROM_HTML_MODE_LEGACY 필요
            btnCheckin.setText(Html.fromHtml(inText, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        }

        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        String outText = "<font color='#888888'>"+getString(R.string.hint_start_date)
                +"</font> <br> <font color=\"#fd5a5f\"> - </font>";
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            btnCheckout.setText(Html.fromHtml(outText), TextView.BufferType.SPANNABLE);
        }else {
            btnCheckout.setText(Html.fromHtml(outText, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}