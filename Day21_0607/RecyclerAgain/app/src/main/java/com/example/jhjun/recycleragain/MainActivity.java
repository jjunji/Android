package com.example.jhjun.recycleragain;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 런타임 권한 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            init();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            String perms[] = {Manifest.permission.READ_CONTACTS};
            requestPermissions(perms,100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                Toast.makeText(getBaseContext(),"권한을 승인하셔야 앱을 사용할 수 있습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init(){
        // 1. 데이터                       // getBaseContext()도 가능.
        List<A_Data> datas = B_Loader.getData(this);

        // 2. 어답터
        C_Adapter adapter = new C_Adapter(datas);

        // 3. 리스트뷰와 어답터 연결 / 1,2번 과정은 데이터와 어답터 연결, 3번은 리스트뷰와 어답터
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        // 4. 레이아웃 매니저
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }
}
