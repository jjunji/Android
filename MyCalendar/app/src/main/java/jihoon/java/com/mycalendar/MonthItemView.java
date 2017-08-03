package jihoon.java.com.mycalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-08-03.
 */

// 필수 생성자 2개
public class MonthItemView extends RelativeLayout{

    TextView textView;

    public MonthItemView(Context context) {
        super(context);
        init(context);
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        // LayoutInflater inflater = (LayoutInflater.from(context.getApplicationContext()));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.month_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void setDay(int day){
        // day(숫자) -> 문자로
        textView.setText(String.valueOf(day));
    }
}
