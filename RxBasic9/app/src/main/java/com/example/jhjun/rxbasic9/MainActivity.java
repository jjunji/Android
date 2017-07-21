package com.example.jhjun.rxbasic9;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jhjun.rxbasic9.domain.Data;
import com.example.jhjun.rxbasic9.domain.Row;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    // http://openAPI.seoul.go.kr:8088/(인증키)/xml/RealtimeWeatherStation/1/5/중구
    // 4f6f4a5a456a75743939636e6c7661
    public static final String SERVER = "http://openAPI.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "4f6f4a5a456a75743939636e6c7661";

    TextView txtRegion;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Context context;
    Button btnOk;
    String[] weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegion = (TextView) findViewById(R.id.txtRegion);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnOk = (Button) findViewById(R.id.btnOk);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER) // api 설정
                .addConverterFactory(GsonConverterFactory.create()) // json을 간편하게 사용하기 위해 GsonConverter를 이용.
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 2. 서비스 생성
        IWeather service = client.create(IWeather.class);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3. 옵저버블 생성
                String gu = txtRegion.getText().toString();
                Observable<Data> observable = service.getData(SERVER_KEY, 1, 1, gu);

                // 4. 발행시작
                observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        // 구독시작
                        .subscribe(
                                data -> {
                                    Row rows[] = data.getRealtimeWeatherStation().getRow();
                                    for(Row row: rows) {
                                        weatherData[0] = row.getSTN_NM();
                                        weatherData[1] = row.getSAWS_TA_AVG();
                                        weatherData[2] = row.getSAWS_HD();

                                        Log.i("Weather", "지역명=" +row.getSTN_NM());
                                        Log.i("Weather", "온도=" +row.getSAWS_TA_AVG()+"도");
                                        Log.i("Weather", "습도=" +row.getSAWS_HD()+"%");
                                    }
                                }
                        );
            }
        });
    }
}

interface IWeather {
    @GET("{key}/json/RealtimeWeatherStation/{start}/{count}/{name}")
    Observable<Data> getData(@Path("key") String server_key
            ,@Path("start") int begin_index
            ,@Path("count") int offset
            ,@Path("name") String gu);
    }

