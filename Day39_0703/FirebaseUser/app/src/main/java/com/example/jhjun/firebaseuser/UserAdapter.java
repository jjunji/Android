package com.example.jhjun.firebaseuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jhjun.firebaseuser.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-07-03.
 */

public class UserAdapter extends BaseAdapter{
    List<User> data;
    LayoutInflater inflater;

    public UserAdapter(Context context){
        data = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<User> data){
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_row,null);
        }
        User user = data.get(position);
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textEmail = (TextView) convertView.findViewById(R.id.textEmail);

        textName.setText(user.username);
        textEmail.setText(user.email);

        return convertView;
    }
}
