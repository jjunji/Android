package com.example.jhjun.fragment2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    MainActivity activity;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setActivity(MainActivity activity){
        this.activity = activity;
    }

    @Override // onBindViewHolder와 비슷.
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 프레그먼트의 메인 레이아웃을 인플레이트하고
        View view =  inflater.inflate(R.layout.fragment_list, container, false); // view는 xml 화면 세팅
        // 안의 위젯들을 코드와 연결한다.
        Button btnGoDetail = (Button) view.findViewById(R.id.btnGoDetail);
        btnGoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addDetail();
            }
        });
        return view;
    }

}
