package fastcampus.team1.logintest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fastcampus.team1.logintest2.Model.Login;
import fastcampus.team1.logintest2.Model.LoginResult;
import fastcampus.team1.logintest2.Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtEmail, txtPassword;
    Button btnLogin, btnSignUp;
    Intent intent;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword1);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin :
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                Login login = new Login();
                login.email = email;
                login.password = password;

                // 레트로핏 객체 정의
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://foolog.jos-project.xyz/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                // 실제 서비스 인터페이스 생성.
                UserService service = retrofit.create(UserService.class);
                // 서비스 호출
                Call<LoginResult> call = service.createLogin(login);
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        // 전송결과가 정상이면
                        Log.e("Write","in ====== onResponse");
                        if(response.isSuccessful()){
                            LoginResult login = response.body(); // 3
                            token = login.key;
                            Log.e("LoginActivity", "token =====================" +token);
                            Toast.makeText(getBaseContext(),"Success", Toast.LENGTH_SHORT).show();
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            int statusCode = response.code();
                            Log.i("MyTag", "응답코드 ============= "+statusCode);
                            //Log.i("MyTag", "응답코드 ============= "+response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Log.e("MyTag","error==========="+t.getMessage());
                    }
                });

                /*intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                break;
            case R.id.btnSignUp :
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
