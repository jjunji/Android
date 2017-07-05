package com.example.jhjun.firebasefcm;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    TextView textReceive;
    TextView textSend;

    // 파이어베이스 데이터베이스 연결
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uidRef = database.getReference("uid");

    // uuid는 바뀌지 않는다. 등록해 놓으면 어느 서버에서도 찾을 수 있음.(기기의 식별)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textReceive = (TextView) findViewById(R.id.textReceive);
        textSend = (TextView) findViewById(R.id.textSend);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CustomAdapter adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);

        // 1. UUID 생성 후 저장 객체에 담기
        String deviceUid = Settings.Secure.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID
        );
        Log.e("UUID---", deviceUid+"");

        Uid uid = new Uid();
        uid.deviceUid = deviceUid;
        uid.name = "손님";
        uid.token = null;
        // 2. 데이터베이스에 저장
        // 데이터베이스에 동일한 uuid가 있으면 생략
        uidRef.child(deviceUid).setValue(uid); // 로그인하지 않아도 식별 가능해짐.  // uid를

/*        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token---", "Refreshed token: " + refreshedToken+"");*/
    }
}

class Uid{
    String deviceUid;
    String token;
    String name;

    public Uid(){
        // 파이어베이스 데이터베이스에서 사용하기 위해서는 public 생성자가 필요하다.
    }

    public Uid(String deviceUid, String token, String name){
        this.deviceUid = deviceUid;
        this.token = token;
        this.name = name;
    }


}
