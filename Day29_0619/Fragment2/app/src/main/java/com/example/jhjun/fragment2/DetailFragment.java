package com.example.jhjun.fragment2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    MainActivity activity;

    public DetailFragment() {
        // Required empty public constructor
    }

    // 화면만 뿌려줄 것이라면, setActivity 메소드로 액티비티의 정보를 넘기지 않아도 된다.
    // 메인 액티비티의 정보를 넘기는 이유는 프래그먼트의 버튼을 눌렀을 때 메인엑티비티에서
    // 디테일 프래그먼트를 호출하여 교체하기 위해서이다.

    public void setActivity(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Button btnGoBack = (Button) view.findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });
        return view;  // 뷰를 반환 하므로써 -> 메인 액티비티의 container에 붙인 ListFragment의 화면이 보여짐.
    }

}
