package com.example.jhjun.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnTrans;
    private Button btnRotate;
    private Button btnScale;
    private Button btnAlpha;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnTrans = (Button) findViewById(R.id.btnTrans);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnScale = (Button) findViewById(R.id.btnScale);
        btnAlpha = (Button) findViewById(R.id.btnAlpha);

        btnScale.setOnClickListener(this);
        btnAlpha.setOnClickListener(this);
        btnTrans.setOnClickListener(this);
        btnRotate.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        Animation animation = null;
        switch (v.getId()) {
            case R.id.btnTrans:
                animation = AnimationUtils.loadAnimation(this, R.anim.trans);
                break;
            case R.id.btnRotate:
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                break;
            case R.id.btnScale:
                animation = AnimationUtils.loadAnimation(this, R.anim.scale);
                break;
            case R.id.btnAlpha:
                animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                break;
        }
        if (animation != null) {
            imageView.startAnimation(animation);
        }
    }
}
