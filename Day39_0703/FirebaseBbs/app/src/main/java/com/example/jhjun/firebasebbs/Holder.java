package com.example.jhjun.firebasebbs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-07-03.
 */

public class Holder extends RecyclerView.ViewHolder {

    private TextView textId;
    private TextView textTitle;
    private TextView textAuthor;

    public Holder(View itemView) {
        super(itemView);

        textId = (TextView) itemView.findViewById(R.id.textId);
        textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        textAuthor = (TextView) itemView.findViewById(R.id.textAuthor);
    }

    public String getTextId() {
        return textId.getText().toString();
    }

    public void setTextId(String value) {
        textId.setText(value);
    }

    public String getTextTitle() {
        return textTitle.getText().toString();
    }

    public void setTextTitle(String value) {
        textTitle.setText(value);
    }

    public String getTextAuthor() {
        return textAuthor.getText().toString();
    }

    public void setTextAuthor(String value) {
        textAuthor.setText(value);
    }
}
