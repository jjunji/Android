package com.example.jhjun.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView textView;
    EditText editText;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 파이베이스 데이터베이스 연결
        database = FirebaseDatabase.getInstance();  // firebase 인스턴스 얻기, json구조와 흡사
        // 레퍼런스를 가져옴
        myRef = database.getReference("message2");  // 처음에 노드가 하나 생성된다고 생각.
                        // getReference ->  -> 노드의 이름을 지칭

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                myRef.setValue(text);
            }
        });

        initData();
    }

    private void initData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class); // getValue( ) 받을 데이터 타입 명시, 모델 클래스가 있다면 객체로 받겠지
                textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
