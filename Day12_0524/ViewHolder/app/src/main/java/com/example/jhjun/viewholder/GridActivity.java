package com.example.jhjun.viewholder;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // 1. 데이터
        ArrayList<Data>datas = new ArrayList<>();
        // 1.1 데이터 넣기
        datas = Loader.getData();  // Loader와 어답터는 public으로 같은 패키지 안에서 정의했으므로
        // 2. 어답터               // 가져다 사용할 수 있다.
        GridAdapter adapter = new GridAdapter(datas, this);
        // 3. 연결
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }
}

class GridAdapter extends BaseAdapter {
    ArrayList<Data> datas;
    Context context;
    LayoutInflater inflater;

    public GridAdapter(ArrayList<Data> datas, Context context) {
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
            convertView = inflater.inflate(R.layout.item_custom_grid, null);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            holder.title = (TextView) convertView.findViewById(R.id.ItextView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        // 매줄에 해당되는 데이터를 꺼낸다
        Data data = datas.get(position);

        // 이미지 세팅하기
        // 1. 이미지 suffix 만들기
        int suffix = (data.getId() % 5) + 1;  //
        // 2. 문자열로 리소스 아이디 가져오기
        int id = context.getResources()
                .getIdentifier("p" + suffix, "mipmap", context.getPackageName());
        // 3. 리소스 아이디를 이미지뷰에 세팅하기
        holder.image.setImageResource(id);

        holder.title.setText(data.getTitle());

        return convertView;
    }

    class Holder {
        public ImageView image;
        public TextView title;
    }
}