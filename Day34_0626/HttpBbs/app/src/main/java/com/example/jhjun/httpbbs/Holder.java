package com.example.jhjun.httpbbs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jhjun on 2017-06-26.
 */

public class Holder extends RecyclerView.ViewHolder {
    TextView textNo;
    TextView textTitle;

    public Holder(View item) {
        super(item);

        textNo = (TextView)item.findViewById(R.id.textNo);
        textTitle = (TextView)item.findViewById(R.id.textTitle);
    }
}
