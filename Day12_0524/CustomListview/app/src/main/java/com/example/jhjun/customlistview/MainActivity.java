package com.example.jhjun.customlistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 데이터
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        // 3. 연결
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);

    }
}

class CustomAdapter extends BaseAdapter {
    // 1. 데이터
    ArrayList<Data> datas;
    // 2. 인플레이터
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. 컨버터뷰를 체크해서 null 일경우만 item layout 을 생성해준다
        Holder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new Holder();
            holder.no = (TextView) convertView.findViewById(R.id.txtNo);
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.image = (ImageView) convertView. findViewById(R.id.imageView);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        // 2. 내 아이템에 해당하는 데이터를 세팅해준다.
        Data data = datas.get(position);
//        ((TextView) convertView.findViewById(R.id.txtNo)).setText(data.no + "");
//        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(data.title);
        holder.no.setText(data.no+"");
        holder.title.setText(data.title);

        return convertView;
    }

    class Holder {
        TextView no;
        TextView title;
        ImageView image;
    }
}


class Loader {
    public static ArrayList<Data> getData(){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=0 ; i<100 ; i++){
            Data data = new Data();
            data.no = i;
            data.title = "데이터"+i;
            data.image = "p"+i;
            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
}