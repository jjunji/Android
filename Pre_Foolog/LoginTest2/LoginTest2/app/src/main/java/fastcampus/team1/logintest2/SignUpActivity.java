package fastcampus.team1.logintest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import fastcampus.team1.logintest2.Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private ImageView imageView;
    private EditText txtEmail;
    private EditText txtPassword1;
    private EditText txtPassword2;
    private EditText txtNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
    }

    private void initView() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword1 = (EditText) findViewById(R.id.txtPassword1);
        txtPassword2 = (EditText) findViewById(R.id.txtPassword2);
        txtNickName = (EditText) findViewById(R.id.txtNickName);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();   // // TODO: 2017-07-31 toString() 전에 getText() 거치는 이유 알기
                String nickname = txtNickName.getText().toString();
                String password1 = txtPassword1.getText().toString();
                String password2 = txtPassword2.getText().toString();
                // 위젯에 입력된 값을 객체에 담고
                User user = new User();
                user.email = email;
                user.nickname = nickname;
                user.password1 = password1;
                user.password2 = password2;

                // 레트로핏 객체 정의
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://foolog.jos-project.xyz/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                // 실제 서비스 인터페이스 생성.
                UserService service = retrofit.create(UserService.class);
                // 서비스 호출
                Call<User> call = service.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        // 전송결과가 정상이면
                        Log.e("Write","in ====== onResponse");
                        if(response.isSuccessful()){
                            Toast.makeText(getBaseContext(),"등록되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            int statusCode = response.code();
                            Log.i("MyTag", "응답코드 ============= "+statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("MyTag","error==========="+t.getMessage());
                    }
                });
            }
        });
    }
}
