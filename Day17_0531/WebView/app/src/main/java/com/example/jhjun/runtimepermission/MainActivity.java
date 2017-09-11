package com.example.jhjun.runtimepermission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// 설치식 권한 : 메니페스트에 permission 권한을 주는 것. -> 그냥 permission
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button previous, next, go;
    WebView webView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        go = (Button) findViewById(R.id.go);

        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        go.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.url);


        // ---------- 기본적인 WebView 세팅 ------------------
        // script 사용 설정(필수) - 페이지내 javaScript가 동작하도록 한다.
        webView.getSettings().setJavaScriptEnabled(true);

        // 1. 웹뷰 클라이언트를 지정.
        webView.setWebViewClient(new WebViewClient());
        // 2. 둘다 세팅할 것 : 프로토콜에 따라 클라이언트가 선택됨.
        webView.setWebChromeClient(new WebChromeClient());
        /* 클라이언트가 지정되지 않으면 내장 웹앱이 실행된다.*/
        // ---------------------------------------------------

        loadUrl("naver.com");
    }

    public void loadUrl(String url){
        // 문자열 앞에 프로토콜인 http 문자열이 없다면 불러준다.
        if(!url.startsWith("http")){
            url = "http://" + url;
        }
        // url 호출하기
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous :  // 뒤로가기
                if(webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case R.id.next : // 앞으로 가기
                if(webView.canGoForward()){
                    webView.goForward();
                }
                break;
            case R.id.go : // url 이동
                String url = editText.getText().toString();
                    if(!"".equals(url)){ // 공백이 아닐 경우 처리.
                    // 문자열이 url 패턴일 때만
                        if(url.matches("^(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$"   )){
                            loadUrl(url);
                        }else{
                            Toast.makeText(this, "url이 잘못되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                break;
        }
    }
}
