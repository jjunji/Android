package com.example.jhjun.activitycontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnStart, btnResult;
    Intent intent;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, SubActivity.class);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnResult = (Button) findViewById(R.id.btnResult);
        editText = (EditText) findViewById(R.id.editText);

        btnStart.setOnClickListener(this);
        btnResult.setOnClickListener(this);
    }

    public static final int BTN_START = 98;
    public static final int BTN_RESULT = 99;  // int 값으로 받는 이유는 아마 빠른 연산을 하기 위함이 아닐까.

    @Override
    public void onClick(View v) {
        // Intent intent = new Intent(this, SubActivity.class);  // intent가 바뀌지 않으므로 여기 쓸 필요 없다, 클릭할 때 마다 new -> 메모리 낭비
        switch (v.getId()){
            // 일반적인 Activity start
            case R.id.btnStart :
                startActivity(intent);  // intent에는 목적지 정보가 있으므로 SubActivity.class 호출.
                break;
            // 값을 돌려받는 Activity start
            case R.id.btnResult :
                                            // 변수 = "값" / 특정 값을 꺼내기 위해 값에 해당하는 이름을 지어주는 행위
                //intent.putExtra("key","From button result");
                intent.putExtra("key",editText.getText().toString());
                // 여러 버튼이 같은 함수(sub액티비티 호출)를 호출하고 각각의 다른결과를 얻어야한다면 인식표가 필요하겠지
                // 구분자, 어디서 오는 것인지 알기 위함. (sub액티비티를 호출하는 버튼이 많을 때)
                startActivityForResult(intent, BTN_START);
                // start subActivity > finish() > 결과값을 돌려준다. > MainActivity.onActivityResult
                break;
        }
    }

    @Override                           // 구분 코드   // 반환하는 쪽에서 세팅한 값
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                                                        // 결과값이 담겨온다.
        // super.onActivityResult(requestCode, resultCode, data); // ->
        Toast.makeText(this, "Result Code="+resultCode, Toast.LENGTH_SHORT).show();

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case BTN_RESULT:
                                // Intent 인 data에서 result 변수로 값을 꺼내는데
                                // 값이 없을경우 디폴트값으로 0 을 사용한다.
                    int result = data.getIntExtra("result",0);
                    editText.setText("결과값="+result);
                    break;
                case BTN_START:
                    Toast.makeText(this, "Start 버튼을 눌렀다가 돌아옴", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
