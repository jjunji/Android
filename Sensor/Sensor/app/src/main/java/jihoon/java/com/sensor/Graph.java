package jihoon.java.com.sensor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by jhjun on 2017-09-01.
 */

public class Graph extends View {

    private int realWidth, realHeight; // 높이, 넓이에서 패딩을 뺀 값.
    private final int xDensity = 300, yDensity = 300;
    private int cellHeight, cellWidth;
    private Paint graphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int lineWidth = 5; // px 단위.
    private List<Integer> points = new ArrayList<>();
    private float[] lines = new float[xDensity*3];
    private Point zerobyzero = new Point();

    public Graph(Context context) {
        super(context, null);
    }

    public Graph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Graph);
        int graphColor = ta.getInt(R.styleable.Graph_graphColor, 0);
        graphPaint.setColor((graphColor != 0) ? graphColor : Color.CYAN);
        graphPaint.setStrokeWidth(lineWidth);
        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        realWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
        realHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        cellWidth = realWidth/xDensity;
        cellHeight = realHeight/yDensity;
        Log.e("width===", cellWidth+"");
        Log.e("Height===", cellHeight+"");
        zerobyzero.set(getPaddingLeft(),getPaddingTop()+realHeight/2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLines(lines, graphPaint);

    }

    public void getPoints(List<Integer> points) {
        if(points.size() < 2){
            throw new IllegalArgumentException("Required at least two points if you want draw line");
        } else if(points.size() > xDensity){
            int deletable = points.size() - xDensity;
            for(int i = 0; i < deletable; i++){
                points.remove(i);
            }

        }
        this.points = points;
        populateLinePoints();
        invalidate();
    }

    public void setPoint(Integer point) {
        if(points.size() == xDensity) {
            points.remove(0);
        } else if(points.size() == 0){
            points.add(0);
        }
        points.add(point);
        populateLinePoints();
        invalidate();
    }

    private void populateLinePoints(){

        for(int i =0; i<points.size(); i++){
            if(i%2 == 1){
                float y = zerobyzero.y - (points.get(i/2)*cellHeight);
                lines[i] = y;
            } else {
                float x = zerobyzero.x + ((i/2) *cellWidth);
                lines[i] = x;
            }
        }
    }
}
