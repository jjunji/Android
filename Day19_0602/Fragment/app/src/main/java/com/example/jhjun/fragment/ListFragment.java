package com.example.jhjun.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 프래그먼의 메인 레이아웃을 inflate 하고
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // 안의 위젯들을 코드에 연결한다.
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
