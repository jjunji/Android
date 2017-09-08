package com.example.jhjun.recycleragain;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-06-19.
 */

// 홀더 : 셀 하나하나를 다뤄주는 뷰 객체를 만든 것.
public class D_Holder extends RecyclerView.ViewHolder{
    // 위젯 정의
    private TextView textTel;
    private TextView textName;

    public D_Holder(View itemView) {
        super(itemView);
        // 생성자가 생성될 때 xml에 있는 위젯을 소스코드와 연결.
        textTel = (TextView) itemView.findViewById(R.id.textTel);
        textName = (TextView) itemView.findViewById(R.id.textName);
    }

    public String getTel(){
        return textTel.getText().toString();
    }

    public void setTel(String value){
        textTel.setText(value);
    }

    public String getName(){
        return textName.getText().toString();
    }

    public void setName(String value){
        textName.setText(value);
    }
}
