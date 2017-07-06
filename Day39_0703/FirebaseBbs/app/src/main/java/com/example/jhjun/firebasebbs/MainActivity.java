package com.example.jhjun.firebasebbs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jhjun.firebasebbs.util.PermissionControl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionControl.CallBack{

    // 파이어베이스 인증
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d("Auth", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d("Auth", "onAuthStateChanged:signed_out");
            }
        }
    };

    // 위젯정의
    EditText editEmail, editPassword;
    Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionControl.checkVersion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // 여기에 코드 작성
    @Override
    public void init() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);;
    }

    public void signUp(View view){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "사용자 등록이 되지 않았습니다!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "사용자 등록이 잘 되었습니다!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(View view){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("Auth", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Sign-in 이 되지 않았습니다",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "Sign-in 되었습니다",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}