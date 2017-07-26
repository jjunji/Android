package com.example.jhjun.servernodejs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class WriteActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editAuthor;
    private EditText editContent;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();

        btnPost.setOnClickListener(
                v -> {
                    String title = editTitle.getText().toString();
                    String author = editAuthor.getText().toString();
                    String content = editContent.getText().toString();

                    postData(title, author, content);
                }
        );
    }

    private void postData(String title, String author, String content){
        // 0. 입력할 객체 생성
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Gson gson = new Gson();
        // bbs 객체를 수동으로 전송하기 위해서는
        // bbs 객체 -> json String 변환
        // RequestBody 에 미디어타입과, String 으로 벼환된 데이터를 담아서 전송
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                gson.toJson(bbs)
        );

        Observable<ResponseBody> observable = myServer.write(body);

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            String result = responseBody.string(); // 결과코드를 넘겨서 처리...
                    /*
                        1. 내가 정상적으로 새글을 입력한 후 데이터를 전송하고 종료
                        2. 새글을 전송하지 않고 화면을 그냥 종료
                        위의 두 가지를 구분하고
                        1번 또는 2번 결과값을 MainActivity로 넘겨서 처리
                     */
                            setResult(Activity.RESULT_OK);  // 값을 인텐트에 담아 주기만 하는 것, -> onActivityResult에서는 값을 꺼내서 실행
                            finish();
                        }
                );
    }
    // 포스트를 하고 나서 어떤 결과가 블럭안에서 처리되기 때문에 onDestroy() 를 호출하지 않아도 될 것 같다.

    private void initView() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnPost = (Button) findViewById(R.id.btnPost);
    }
}
