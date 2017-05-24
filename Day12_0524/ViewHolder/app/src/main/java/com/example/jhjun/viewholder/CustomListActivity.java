package com.example.jhjun.viewholder;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터
        ArrayList<Data> datas = Loader.getData();

        // 2. 아답터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        // 3. 연결
        listView.setAdapter(adapter);
    }
}

class CustomAdapter extends BaseAdapter {
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { // 사용하는 데이터의 총 개수를 리턴...
        return datas.size();
    }

    @Override
    public Object getItem(int position) { // 데이터 클래스 하나를 리턴
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) { // 대부분 인덱스가 그대로 리턴된다
        return position;
    }

    // 아이템 뷰 하나를 리턴한다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // xml 을 class 로 변환한다.
        Log.d("ConvertView",position+" : convertView="+convertView);

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_custom_list, null);
            holder.no = (TextView) convertView.findViewById(R.id.txtNo);
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        // 매줄에 해당되는 데이터를 꺼낸다
        Data data = datas.get(position);
        holder.no.setText(data.getId()+"");
        holder.title.setText(data.getTitle());

        return convertView;
    }

    class Holder {
        public TextView no;
        public TextView title;
    }
}

class Loader {
    public static ArrayList<Data> getData(){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=0; i<100 ; i++){
            Data data = new Data();
            data.setId(i+1);
            data.setTitle("타이틀"+i);
            result.add(data);
        }
        return result;
    }
}

class Data {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}