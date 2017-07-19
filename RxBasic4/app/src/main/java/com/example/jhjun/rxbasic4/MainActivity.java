package com.example.jhjun.rxbasic4;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;
    Observable<String> observable;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        // just는 배열 형태로 , from은 배열을 직접 넣을 수 없음,
        //observable = Observable.just("월", "화", "수", "목", "금", "토", "일");

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        Log.e("Main","months=============="+months);

        // 객체 안에 함수가 하나라는 전제가 있기 때문에 내부의 불필요한 코드를 지워서 라인을 줄일 수 있다.
        observable = Observable.create(emitter -> {
            for(String month : months){
                emitter.onNext(month);
                Thread.sleep(1000);
            }
            emitter.onComplete();
            }
        );
    }

    public void doAsync(View view){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    str -> {
                        data.add(str);
                        adapter.notifyDataSetChanged(); // 매번 데이터를 가져올 때마다 출력하기 위해 설정.
                    },
                    error -> Log.e("Error", error.getMessage()),
                    () -> {
                        data.add("complete");
                        adapter.notifyDataSetChanged();
                    }
                );
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }
}
