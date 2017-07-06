package com.example.jhjun.firebasebbs;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhjun.firebasebbs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-07-03.
 */

public class ListAdapter extends RecyclerView.Adapter<Holder>{

    private List<Bbs> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ListAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Bbs> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        holder.setTitle(bbs.title);
        holder.setCount(bbs.count);
        holder.setPosition(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
