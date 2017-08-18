package jihoon.java.com.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-08-17.
 */


public class CalendarView extends FrameLayout {
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int GREEN = 2;
    private TextView date;
    private LinearLayout marks;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.item_day,this,true);
        date = view.findViewById(R.id.date);
        marks = view.findViewById(R.id.marks);
    }


    public void addDot(int color){
        View view = new View(this.getContext());
        switch (color){
            case RED:
                view.setBackgroundResource(R.drawable.tag_circle_korea);
                break;
            case BLUE:
                view.setBackgroundResource(R.drawable.tag_circle_china);
                break;
            case GREEN:
                view.setBackgroundResource(R.drawable.tag_circle_usa);
                break;
        }
        marks.addView(view,new LayoutParams(10,10));
    }
    public void setDate(String dateStr){
        date.setText(dateStr);
    }
}
