package com.jjunji.android.num5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 1001;
    public static final int test = 101;

    EditText usernameInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 이름, 비번 변수에 입력된 값을 담고,
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Intent 객체 생성
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                // 키,값 형태로 intent에 넣는다.
                intent.putExtra("username", username);
                intent.putExtra("password", password);

                startActivityForResult(intent, REQUEST_CODE_MENU);
                Toast.makeText(MainActivity.this, "잠시 왔다간당", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, test);
            }
        });

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_MENU) {
            if (intent != null) {
                String menu = intent.getStringExtra("menu");
                String message = intent.getStringExtra("message");

                Toast toast = Toast.makeText(getBaseContext(), "result code : " + resultCode + ", menu : " + menu + ", message : " + message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else if(requestCode == test){
            String menu = intent.getStringExtra("menu");
            String message = intent.getStringExtra("message");

            Toast toast = Toast.makeText(getBaseContext(), "!!!!!!!!!!!!!! : " + resultCode + ", menu : " + menu + ", message : " + message, Toast.LENGTH_LONG);
            toast.show();
        }

    }

}

