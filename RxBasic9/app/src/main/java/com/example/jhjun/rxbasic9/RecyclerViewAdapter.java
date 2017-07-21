package com.example.jhjun.rxbasic9;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-07-20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    String[] weatherData;

    public RecyclerViewAdapter(String weatherData[]){
        this.weatherData = weatherData;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtRegion;

        public ViewHolder(View v) {
            super(v);
            txtRegion = (TextView) v.findViewById(R.id.txtRegion);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtRegion.setText(weatherData[0]);
    }

    @Override
    public int getItemCount() {
        return weatherData.length;
    }
}
