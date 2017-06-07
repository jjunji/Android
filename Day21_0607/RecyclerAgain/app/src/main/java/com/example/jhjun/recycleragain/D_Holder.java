package com.example.jhjun.recycleragain;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-06-07.
 */

public class D_Holder extends RecyclerView.ViewHolder{
// 홀더를 만들 때 멤버 변수로 정의되야 하는 것 : 위젯

    private TextView textTel;
    private TextView textName;

    public String getTel() {
        return textTel.getText().toString();
    }

    public void setTel(String value) {
        textTel.setText(value);
    }

    public String getName() {
        return textTel.getText().toString();
    }

    public void setName(String value) {
        textName.setText(value);
    }

    public D_Holder(View itemView) {
        super(itemView);

        //생성자가 생성될 때 xml에 있는 위젯을 소스코드와 연결
        textTel = (TextView)itemView.findViewById(R.id.textTel);
        textName = (TextView)itemView.findViewById(R.id.textName);


    }
}
