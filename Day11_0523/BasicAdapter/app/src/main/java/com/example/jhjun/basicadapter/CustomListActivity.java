package com.example.jhjun.basicadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {

    // ListView의 객체 listview 생성
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터
        ArrayList<Data> datas = Loader.getData();  // Data타입의 datas ArrayList 생성.

        // 2. 어댑터
        CustomAdapter adapter = new CustomAdapter(datas, this);

        // 3. 연결
        listView.setAdapter(adapter);
    }
}

class CustomAdapter extends BaseAdapter{ // BaseAdapter는 !
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<Data> datas, Context context){
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {  // 사용하는 데이터의 총 개수를 리턴 -> 데이터의 사이즈 알 수 있다.
        return datas.size();
    }

    @Override
    public Object getItem(int position) { // 데이터 클래스 하나를 리턴
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {  // 뷰의 아이디 값을 리턴 -> 대부분 인덱스가 그대로 리턴됨
        return position;
    }
    // 아이템 뷰 하나를 리턴한다.

    @Override                           // convertView
    public View getView(int position, View convertView, ViewGroup parent) {  // 아이템 뷰 하나를 리턴(중요)
        // xml 을 class 로 변환한다.
        View view = inflater.inflate(R.layout.item_custom_list,null);

//        Log.d("ConvertView",position+" convertView =" + convertView);
//        if(convertView == null) {
//            convertView = inflater.inflate(R.layout.item_custom_list, null);
//        }
        TextView no = (TextView) view. findViewById(R.id.txtNo);
        TextView title = (TextView) view. findViewById(R.id. txtTitle);

        // 매 줄에 해당되는 데이터를 꺼낸다.
        Data data = datas.get(position);
        no.setText(data.getId()+"");
        title.setText(data.getTitle());

        return view;
    }
}

class Loader{
    public static ArrayList<Data> getData(){
        ArrayList<Data> result = new ArrayList<>();

        for(int i=0; i < 100; i++){
            Data data = new Data();
            data.setId(i+1);
            data.setTitle("타이틀 : " + i);
            result.add(data);
        }
        return result;
    }
}

class Data{
    private int id;
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }
}