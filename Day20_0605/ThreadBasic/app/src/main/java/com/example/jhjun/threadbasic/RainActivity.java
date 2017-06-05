package com.example.jhjun.threadbasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainActivity extends AppCompatActivity {

    FrameLayout ground;
    Stage stage;

    int deviceWidth, deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        ground = (FrameLayout)findViewById(R.id.stage);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;

        // 커스텀 뷰를 생성하고
        stage = new Stage(getBaseContext());
        // 레이아웃에 담아주면 화면에 커스텀 뷰의 내용이 출력된다.
        ground.addView(stage);

        findViewById(R.id.btnRun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTask();
            }
        });
    }

    private void runTask(){
        // 빗방울을 1초마다 1개씩 랜덤하게 생성.
        Rain rain = new Rain();
        rain.start();
        // 화면을 1초마다 한번씩 갱신
        DrawCanvas drawCanvas = new DrawCanvas();
        drawCanvas.start();
    }

    // 화면을 1초에 한번씩 그려주는 클래스 (onDraw 를 호출)
    class DrawCanvas extends Thread{

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.postInvalidate();
            }
        }
    }

    class Rain extends Thread {
        @Override
        public void run() {
            // 특정 범위의 숫자를 랜덤하게 생성할 때 사용
            Random random = new Random();

            for (int j = 0; j < 100; j++) {
                // 빗방울 하나를 생성해서 stage 에 더해준다.
                RainDrop rainDrop = new RainDrop();
                rainDrop.radius = random.nextInt(25) + 5;

                rainDrop.x = random.nextInt(deviceWidth); // 0부터 ~ 디바이스 가로사이즈 사이
                rainDrop.y = 0f;

                rainDrop.speed = random.nextInt(10) + 5; // 초당 이동하는 픽셀거리

                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                rainDrop.paint = paint;

                // 생성한 빗방울을 stage 에 더해준다
                stage.addRainDrop(rainDrop);

                // 생성한 빗방울을 동작 시킨다.
                rainDrop.start();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Stage extends View {

        Paint paint;
        List<RainDrop> rainDrops = new ArrayList<>();

        public Stage(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLUE);
        }

        // 화면에 로드되는 순간 호출되는 함수
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //               x좌표, y, 반지름, 굵기, 컬러등
            for(RainDrop drop : rainDrops){
                canvas.drawCircle(drop.x, drop.y, drop.radius, drop.paint);
            }
        }

        public void addRainDrop(RainDrop rainDrop){
            this.rainDrops.add(rainDrop);
        }
    }

    class RainDrop extends Thread{
        Paint paint; // 색상

        float radius; // 크기
        float x; // x 좌표
        float y; // y 좌표

        int speed;  // 속도

        boolean run = true;

        @Override
        public void run() {
            int count = 0;
            while(y < deviceHeight) {
                count++;
                y = count * speed;
                Log.i("RainDrop","y=============="+y);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            run = false;
        }
    }
}

/*
 빗방울이 여러개 떨어지게 하려면 -> task 실행시, 빗방울을 만들어야겠지..
 떨어지면 없어지게..
 */