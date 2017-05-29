package com.example.jhjun.memo_re;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

/**
 * Created by jhjun on 2017-05-29.
 */

/**
 * Created by pc on 5/29/2017.
 */

public class DetailView {

    EditText memo;
    FloatingActionButton button;

    DetailActivity activity;

    public DetailView(DetailActivity act){

        activity = act;

        memo = (EditText) activity.findViewById(R.id.editText);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);

        button = (FloatingActionButton) activity.findViewById(R.id.fab);

        // 메모 저장
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.save();
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.back();
                return true;
            }
        });
    }
}