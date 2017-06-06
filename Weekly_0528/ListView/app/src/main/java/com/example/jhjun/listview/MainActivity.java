package com.example.jhjun.listview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    ListView listView;
    SingerAdapter adapter;

    ArrayList<SingerItem> items = new ArrayList<SingerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new SingerAdapter(items, getApplicationContext());
//
        adapter.addItem(new SingerItem("소녀시대", "010-1000-1000", 20, R.drawable.singer));
        adapter.addItem(new SingerItem("걸스데이", "010-2000-2000", 22, R.drawable.singer2));
        adapter.addItem(new SingerItem("여자친구", "010-3000-3000", 21, R.drawable.singer3));
        adapter.addItem(new SingerItem("티아라", "010-4000-4000", 24, R.drawable.singer4));
        adapter.addItem(new SingerItem("AOA", "010-5000-5000", 25, R.drawable.singer5));
//
        listView.setAdapter(adapter);
///
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }
}


class SingerAdapter extends BaseAdapter{

    ArrayList<SingerItem> items;
    Context context;

    public SingerAdapter(ArrayList<SingerItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    public void addItem(SingerItem item){
        items.add(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingerItemView view = new SingerItemView(context);
        SingerItem item = items.get(position);
        view.setName(item.getName());
        view.setMobile(item.getMobile());
        view.setAge(item.getAge());
        view.setImage(item.getResId());

        return  view;
    }
}
