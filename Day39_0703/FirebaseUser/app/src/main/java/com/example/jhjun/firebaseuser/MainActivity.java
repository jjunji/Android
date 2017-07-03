package com.example.jhjun.firebaseuser;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.jhjun.firebaseuser.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    UserAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference userRef;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        // root 노드인 user를 레퍼런스로 사용
        userRef = database.getReference("user");

        // 프로그레스는 메인 스레드에서, 파이어베이스 네트워크 통신은 서브 스레드에서
        dialog = new ProgressDialog(this);
        dialog.setTitle("처리중....");
        dialog.setMessage("ad");



        // 이벤트 리스너 달기
        userRef.addValueEventListener(new ValueEventListener() {
            @Override                           // 번들처럼 가지고있음, 사진찍듯이 현재 데이터베이스 상황을 사진 찍듯이 찍어서 가져옴.
            public void onDataChange(DataSnapshot users) {
                List<User> data = new ArrayList<User>();
                for( DataSnapshot item : users.getChildren()) { // 데이터 하나하나 배열처럼 가져옴 (user에서 찍으면 전부를 사진찍듯이 찍고 chileren에서 찍으면 user를 제외한 전부를 찍음. for문으로 꺼내면 하나하나 나옴.
                    String key = item.getKey();
                    Log.i("Firebase","user key="+key);
                    User user = item.getValue(User.class);
                    data.add(user);
                }
                refreshData(data);
                dialog.dismiss();  // 돌아올 때 dismiss 가 또 호출되는데 다이얼로그가 없기때문에 상관 없다. -> 메모리 낭비도 없음.
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void signUp(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    // 파이어베이스의 valueEventListener 에서 호출
    public void refreshData(List<User> data){
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}
