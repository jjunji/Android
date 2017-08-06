package jihoon.java.com.mycalendar;

import android.content.Context;
import android.graphics.Color;
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

    /*
     달력의 각 뷰는 0 부터 시작해 ++ 된다는 것을 확인 -> 일요일에 해당하는 뷰들은 position 0 7 14 21 로 진행.
     position 값을 7로 나누어 나머지가 0일 경우는 일요일을 뜻하므로 조건문으로 판단하여 텍스트를 붉게 표시함.
    */
    // 달력(그리드 뷰)에 날짜를 설정
    public void setDay(String day,int position){
        if( position % 7 == 0 ){
            textView.setTextColor(Color.RED);
        }else if( position % 7 == 6 ){
            textView.setTextColor(Color.BLUE);
        } else{
            textView.setTextColor(Color.BLACK);
        }

        textView.setText(day);
    }
}
