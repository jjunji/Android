package com.example.jhjun.firebasebbs;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-07-03.
 */

public class Holder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private TextView textCount;
    private int position;

    public Holder(View v) {
        super(v);
        textTitle = (TextView) v.findViewById(R.id.textTitle);
        textCount = (TextView) v.findViewById(R.id.textDate);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ReadActivity.class);
                intent.putExtra("LIST_POSITION", position);
                v.getContext().startActivity(intent);
            }
        });
    }
    public void setTitle(String title){
        textTitle.setText(title);
    }

    public void setCount(long count){
        textCount.setText(count + "");
    }

    public void setPosition(int position){
        this.position = position;
    }

}
