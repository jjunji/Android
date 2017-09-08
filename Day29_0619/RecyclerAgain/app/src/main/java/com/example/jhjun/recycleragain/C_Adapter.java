package com.example.jhjun.recycleragain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jhjun on 2017-06-19.
 */
// 홀더는 셀 하나하나의 뷰 컨트롤을 위해 만드는 것.
public class C_Adapter extends RecyclerView.Adapter<D_Holder>{

    List<A_Data> datas;

    public C_Adapter(List<A_Data> datas){
        this.datas = datas;
    }

    @Override
    public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);
    }

    @Override // 화면에 셀이 그려질 때 마다 호출됨.
    public void onBindViewHolder(D_Holder holder, int position) {
        // 위에서 만들어준 디홀더를 온바인드뷰홀더 함수를 통해서 시스템이 나에게 넘겨주면 이곳에서 데이터를 셋팅한다.
        A_Data data = datas.get(position); // 가져온 데이터의 리스트
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

