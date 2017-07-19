package com.example.jhjun.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

public class AniActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnProp;
    private Button btnRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani);
        initView();
    }

    private void initView() {
        btnProp = (Button) findViewById(R.id.btnProp);
        btnRed = (Button) findViewById(R.id.btnRed);

        btnProp.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProp:
                ObjectAnimator transY = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "translationY", //애니메이션 속성
                        -500             // 속성 값
                );
                ObjectAnimator transX = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "translationX", //애니메이션 속성
                        300             // 속성 값
                );
                ObjectAnimator rotate = ObjectAnimator.ofFloat(
                        btnRed,         // 움직일 대상
                        "rotation", //애니메이션 속성
                        1440             // 속성 값
                );
                // 애니메이터 셋을 구성해서 실행한다.
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.playTogether(transX,transY,rotate); // 개수의 제한이 없슴
                aniSet.setDuration(3000);           // 애니메이터 셋의 실행 시간
                // 처음엔 빨리 점점 느리게
                aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
                aniSet.start();
                break;
        }
    }
}
