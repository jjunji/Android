package com.example.jhjun.musiclist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jhjun on 2017-06-19.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    List<Music> datas;

    public ListAdapter(List<Music> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(datas.get(position).albumId);
        holder.textView2.setText(datas.get(position).artist);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.id);
            textView2 = (TextView) itemView.findViewById(R.id.content);
        }
    }

}
