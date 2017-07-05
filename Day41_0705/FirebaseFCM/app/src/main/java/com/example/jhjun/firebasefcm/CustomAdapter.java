package com.example.jhjun.firebasefcm;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-07-05.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

/*    public CustomAdapter(){

    }*/

    List<Uid> data = new ArrayList<>();
    LayoutInflater inflater;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Uid uid = data.get(position);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        int position;
        TextView textUid;
        TextView textName;

        public Holder(View v) {
            super(v);
            textUid = (TextView) v.findViewById(R.id.textUid);
            textName = (TextView) v.findViewById(R.id.textName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}