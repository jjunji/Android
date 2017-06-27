package com.example.jhjun.httpbbs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-06-26.
 */

public class Adapter extends RecyclerView.Adapter<Holder> {

    List<Bbs> datas;

    public Adapter(){
        datas = new ArrayList<>();
    }

    public void setList(List<Bbs> datas){
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = datas.get(position);
        holder.textNo.setText(bbs.no);
        holder.textTitle.setText(bbs.title);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
