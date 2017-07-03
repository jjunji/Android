package com.example.jhjun.firebasebbs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhjun.firebasebbs.domain.Bbs;

import java.util.List;

/**
 * Created by jhjun on 2017-07-03.
 */

public class ListAdapter extends RecyclerView.Adapter<Holder>{

    List<Bbs> datas;

    public ListAdapter(List<Bbs> datas){
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs data = datas.get(position);
        holder.setTextId(data.id);
        holder.setTextTitle(data.title);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
