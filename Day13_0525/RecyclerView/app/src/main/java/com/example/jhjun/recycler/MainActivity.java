package com.example.jhjun.recycler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.listView);

        // 1. 데이터
        ArrayList<Data> datas = Loader.getData(this);
        // 2. 어답터
        CustomRecycler adapter = new CustomRecycler(datas, this);
        // 3. 연결
        listView.setAdapter(adapter);
        // 4. 레이아웃 매니저
        listView.setLayoutManager(new LinearLayoutManager(this));

    }
}

class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    ArrayList<Data> datas;
    Context context;

    public CustomRecycler(ArrayList<Data>datas , Context context){
        this.datas = datas;
        this.context = context;
    }

    // List View 에서 convertView == null 일때 처리
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                                                                                // null과 parent의 차이
        View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,null,false);
        //View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,parent,false);
        //Holder holder = new Holder(View);
        return new Holder(view);
    }

    // 각 데이터 셀이 나타날때 호출되는 함수
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 꺼내고
        Data data = datas.get(position);
        // 2. 데이터를 세팅
        holder.setImage(data.resId);
        holder.setNo(data.no);
        holder.setTitle(data.title);
    }

    // 데이터의 전체 개수
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView no;
        TextView title;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            no = (TextView) itemView. findViewById(R.id.txtNo);
            title = (TextView) itemView. findViewById(R.id.txtTitle);
        }

        public void setImage(int resId){
            image.setImageResource(resId);
        }

        public void setNo(int no){
            this.no.setText(no+"");
        }

        public void setTitle(String title){
            this.title.setText(title);
        }
    }
}


class Loader {
    public static ArrayList<Data> getData(Context context){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=1 ; i<=10 ; i++){
            Data data = new Data();
            data.no = i;
            data.title = "피카";

            data.setImage("p"+i, context);

            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
    public int resId;

    public void setImage(String str, Context context){
        image = str;
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
    }
}