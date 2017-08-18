package jihoon.java.com.customview;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    GridLayout cal;
    CalendarView target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cal = (GridLayout) findViewById(R.id.calendar);
        for (int i = 0; i < 30; i++) {
            CalendarView temp = new CalendarView(this);
            cal.addView(temp);
            if (i == 15) {
                target = temp;
            }
        }
        findViewById(R.id.red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target.addDot(CalendarView.RED);
            }
        });

        findViewById(R.id.blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target.addDot(CalendarView.BLUE);
            }
        });

        findViewById(R.id.green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target.addDot(CalendarView.GREEN);
            }
        });

    }
}