package com.example.jhjun.servernodejs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-07-25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater;
    List<Bbs> data;

    @Override
    public int getItemCount() {
        return data.size();
    }

    public RecyclerAdapter(Context context, List<Bbs> data){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        holder.setTitle(bbs.title);
        holder.setDate(bbs.date);
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDate;

        public Holder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            textDate = (TextView) itemView.findViewById(R.id.textDate);
        }

        public void setTitle(String title){
            textTitle.setText(title);
        }

        public void setDate(String date){
            textDate.setText(date);
        }
    }
}