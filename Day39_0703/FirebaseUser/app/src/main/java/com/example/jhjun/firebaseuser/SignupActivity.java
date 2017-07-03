package com.example.jhjun.firebaseuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jhjun.firebaseuser.domain.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText editEmail, editName, editPassword;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        editEmail = (EditText) findViewById(R.id.editEmail);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
    }

    // SignUp 버튼의 onClick 속성에 직접 연결
    // android:onClick = "postData"
    public void postData(View view){
        String email = editEmail.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        // 정규식으로 이메일이 맞는지 체크 후

        // 패스워드 자릿수 체크


        // 파이어베이스에 저장할 User 객체 생성
        User user = new User(name, email, password);

        // firebase에 키를 자동으로 생성             // << hash 코드로 된 키를 레퍼런스 아래에 삽입해준다.
        //String childKey = userRef.push().getKey(); // -> user 노드의 +를 누르고 key값이 임의로 들어간 상태.

        String childKey = replaceEmailComma(user.email);
        userRef.child(childKey).setValue(user);

        //  userRef.child() 는 리턴 값이 reference다. -> 없으면 생성
    }

    public String replaceEmailComma(String email){
        return email.replace(".", "_comma_");
    }

    public String recoverEmailComma(String convertedEmail){
        return convertedEmail.replaceAll("_comma_",".");
    }

}
