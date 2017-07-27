package com.example.jhjun.airbnbsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    Button btnCheckin, btnCheckout;
    private CalendarView calendarView;

    private Search search;

    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initView();
        setListener();
        setCalendarButtonText();
    }

    private void init() {
        search = new Search();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private static final int CHECK_IN = 10;
    private static final int CHECK_OUT = 20;
    private int checkStatus = CHECK_IN;

    private void setListener() {
        calendarView.setOnDateChangeListener(dateChangeListener);
        btnCheckin.setOnClickListener(this);
        btnCheckout.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void setCalendarButtonText() {
        setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
        setButtonText(btnCheckout, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText) {
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText;
        StringUtil.setHtmlText(btn, inText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCheckin:
                checkStatus = CHECK_IN;
                setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
                setButtonText(btnCheckout, getString(R.string.hint_end_date), search.checkoutDate);
                break;
            case R.id.btnCheckout:
                checkStatus = CHECK_OUT;
                setButtonText(btnCheckout, getString(R.string.hint_end_date), getString(R.string.hint_select_date));
                setButtonText(btnCheckin, getString(R.string.hint_start_date), search.checkinDate);
                break;
            case R.id.fab:
                break;
        }
    }

    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            month = month + 1; // 배열 형태로 가지고 있기 때문.(1월 ~ 12월)
            Log.i("Calendar", "year:" + year + ", month:" + month + ", dayOfMonth:" + dayOfMonth);
            String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth);
            //String theDay = year+"-"+month+"-"+dayOfMonth;
            switch (checkStatus) {
                case CHECK_IN:
                    search.checkinDate = theDay;
                    setButtonText(btnCheckin, getString(R.string.hint_start_date), search.checkinDate);
                    break;
                case CHECK_OUT:
                    search.checkoutDate = theDay;
                    setButtonText(btnCheckout, getString(R.string.hint_end_date), search.checkoutDate);
                    break;
            }
        }
    };
}