package com.example.jhjun.paintmemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 보드를 새로 생성한다.
        Board board = new Board(getBaseContext());
        // 2. 붓을 만들어서 보드에 담는다.
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        board.setPaint(paint);

        // 3. 생성된 보드를 화면에 세팅한다.
        // setContentView(board);
        layout.addView(board);
    }

    class Board extends View {
        Paint paint;
        Path path;

        public Board(Context context) {
            super(context);
            path = new Path(); // -> 터치한 포인트를 패스로 연결(그리기 직전)
        }

        public void setPaint(Paint paint){
            this.paint = paint;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path,paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // 내가 터치한 좌표를 꺼낸다.
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    Log.e("LOG", "onTouchEvent ==========");
                    path.moveTo(x,y); // 이전 점과 현재 점 사이를 그리지 않고 이동한다.
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x,y);  //
                    break;
                case MotionEvent.ACTION_UP:
                    path.lineTo(x,y);
                    break;
                case MotionEvent.ACTION_POINTER_UP:  // 뭐하는 함수인지 궁금하면 아래와 같이 토스트로 확인할 수 있다.
                    Toast.makeText(getContext(),"언제 찍히나 ", Toast.LENGTH_LONG).show();
                    break;
            }

            // Path를 그린 후 화면을 갱신해서 반영해준다.
            invalidate(); // -> 하지 않으면 그림을 그려주지 않음.

            // return super.onTouchEvent(event);
            // return false   -> 로그 찍어보면서 확인해보기.
            // 리턴 false 일 경우 touch 이벤트가 발생되지 않는다.
            // 즉 드래그시 onTouchEvent가 호출되지 않는다.
            return true;
        }
    }
}
