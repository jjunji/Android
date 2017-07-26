package com.example.jhjun.servernodejs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class MainActivity extends AppCompatActivity {

    private Button btnWrite;
    private RecyclerView recyclerView;
    private List<Bbs> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        data = new ArrayList<>();
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loader();
    }

    private void initView() {
        btnWrite = (Button) findViewById(R.id.btnWrite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnWrite.setOnClickListener(v->{
            /*
                호출시 startActivity 를 사용하면 onResume 처리를 따로 해줘야 된다.
             */
            Intent intent = new Intent(this, WriteActivity.class);
            startActivityForResult(intent, REQ_CODE);
        });
    }

    private final static int REQ_CODE = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQ_CODE:
                    this.data.clear(); // 기존에 있던 데이터를 삭제해준다...
                    // 중복되는 데이터가 있다면 갱신하지 않는 방향으로...
                    loader();
                    break;
            }
        }
    }

    private void loader() {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                responseBody -> {
                    // 1. 데이터를 꺼내고
                    String jsonString = responseBody.string();
                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<Bbs>>(){}.getType(); // 컨버팅 하기 위한 타입지정
//                    List<Bbs> data = gson.fromJson(jsonString, type);
                    Bbs data[] = gson.fromJson(jsonString, Bbs[].class);
                    // 2. 데이터를 아답터에 세팅하고
                    for(Bbs bbs : data){
                        this.data.add(bbs);
                    }
                    // 3. 아답터 갱신
                    adapter.notifyDataSetChanged();
                }
            );
    }


    interface IUser {

    }
}