package com.example.jhjun.recycleragain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jhjun on 2017-06-07.
 */
                                                // 제네릭에   셀하나하나를 다루는 클래스 디 홀더로
public class C_Adapter extends RecyclerView.Adapter<D_Holder> {

    List<A_Data> datas;

    public C_Adapter(List<A_Data> datas){
        this.datas = datas; // 나의 datas에 넘겨준 datas를 연결한다는 뜻.
        // 생성자를 생성할 때 datas를 넘겨준다는 것은 데이터를 떠서 주는게 아니라.
        // 데이터끼리의 통신선을 만들어준 것.
        // 즉 로더에서 만든 datas는 그대로 있는 것.
        // 모든 객체는 함수의 인자로 넘어갈 때 이와 같은 방식
        // 이렇지 않은 방식은 기본형뿐. (int, long)
    }

    @Override
    public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 셀 하나하나의 xml을 inflate해서 화면에 뿌릴 수 있게 만드는 작업.                   ?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);  //
    }

    @Override  // 화면에 셀이 그려질 때마다 호출되는 함수. 위에서 만들어준 뷰 홀더에 데이터를 세팅.
    public void onBindViewHolder(D_Holder holder, int position) {
        // 1. 데이터 가져와야함.
        A_Data data = datas.get(position);
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }

    @Override
    public int getItemCount() {
        return datas.size(); // 여기에 넘어온 데이터 개수 반환.
    }
}
