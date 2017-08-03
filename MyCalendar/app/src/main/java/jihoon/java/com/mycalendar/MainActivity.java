package jihoon.java.com.mycalendar;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView txtMonth;
    private GridView monthView;
    MonthAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initView() {
        txtMonth = (TextView) findViewById(R.id.txtMonth);
        monthView = (GridView) findViewById(R.id.monthView);

        adapter = new MonthAdapter();
        monthView.setAdapter(adapter);

        Button btnPrevious = (Button) findViewById(R.id.btnPrevious);

        // 이전 월을 설정하고 그대로 표시됨.
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setPreviousMonth();
                txtMonth.setText(adapter.getCurrentYear() + "년" + adapter.getCurrentMonth());
                // 어댑터가 바뀌었으니 notifyDataSetChanged
                adapter.notifyDataSetChanged();
            }
        });
        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNextMonth();
                txtMonth.setText(adapter.getCurrentYear() + "년" + adapter.getCurrentMonth());
                adapter.notifyDataSetChanged();
            }
        });

    }

    // 선택위젯 활용 -> 어답터 생성
    class MonthAdapter extends BaseAdapter{

        MonthItem[] items;
        Calendar calendar;

        int firstDay;
        int lastDay;
        int curYear;
        int curMonth;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public MonthAdapter() {
            items = new MonthItem[ 7 * 6 ];

            Date date = new Date();
            calendar = Calendar.getInstance();
            calendar.setTime(date);  // calendar 에 현재 시간 설정.
            recalculate();
            resetDayNumbers();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setPreviousMonth(){
            calendar.add(Calendar.MONTH, -1); // -1 : 이전 달로 이동
            recalculate(); // 해당 월의 첫날, 마지막 날 계산
            resetDayNumbers(); // 실제 아이템 뷰에 넣어서 뷰로 보여줌
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setNextMonth(){
            calendar.add(Calendar.MONTH, +1);
            recalculate(); // 해당 월의 첫날, 마지막 날 계산
            resetDayNumbers(); // 실제 아이템 뷰에 넣어서 뷰로 보여줌
        }

        public int getCurrentYear(){
            return curYear;
        }

        public int getCurrentMonth(){
            return curMonth;
        }

        //------------- 어답터 생성 -> 메인액티비 안에서 그리드뷰안에 설정 할 수 있게 됨.----------------

        // 시작하는 요일 확인
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void recalculate() {
            // 현재 시간에서 해당하는 월에서 1일로 설정.  ex) 오늘이 10월 4일이면 -> 1을 뽑아냄.
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            // 해당되는 주에서 몇번째 day 인지를 가져온다.
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            firstDay = getFirstDay(dayOfWeek);  // 현재 월에 첫번째 날이 컬럼에 index로 따지면 어떤 값인지 확인.

            curYear = calendar.get(Calendar.YEAR);
            curMonth = calendar.get(Calendar.MONTH);
            lastDay = getLastDay();

            /*int firstDayOfWeek = calendar.getFirstDayOfWeek();  // 그 주에 첫날을 무슨 요일로 할 것인지를 세팅 (일반적으로 일요일)
            int startDay = getFirstDayOfWeek();*/
        }

        // 각각의 그리드 뷰 안에 들어가는 숫자를 넣는 함수
        public void resetDayNumbers() {
            for(int i=0; i<42; i++){
                int dayNumber = (i+1) - firstDay;
                if(dayNumber <1 || dayNumber > lastDay){
                    dayNumber = 0;
                }
                items[i] = new MonthItem(dayNumber);
            }
        }

        // 각 월 마다의 마지막 날 확인
        public int getLastDay() {
            switch (curMonth){
                case 0:
                case 2:
                case 4:
                case 6:
                case 7:
                case 9:
                case 11:
                return 31;

                case 3:
                case 5:
                case 8:
                case 10:
                    return 30;

                default:
                    if(((curYear%4 == 0) && (curYear%100 != 0)) || (curYear%400==0)) {
                        return 29;
                    } else{
                        return 28;
                }

            }
        }
        // 해당 주에 요일 가져오기 , 칼럼 index 구분
        public int getFirstDay(int dayOfWeek){
            int result = 0;

            if (dayOfWeek == Calendar.SUNDAY){
                result = 0;
            }else if( dayOfWeek == Calendar.MONDAY){
                result = 1;
            }else if( dayOfWeek == Calendar.TUESDAY){
                result = 2;
            }else if( dayOfWeek == Calendar.WEDNESDAY){
                result = 3;
            }else if( dayOfWeek == Calendar.THURSDAY){
                result = 4;
            }else if( dayOfWeek == Calendar.FRIDAY){
                result = 5;
            }else if( dayOfWeek == Calendar.SATURDAY){
                result = 6;
            }

            return result;
        }

        @Override
        public int getCount() {
            return 42;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MonthItemView view = null;
            if(convertView == null){
                view = new MonthItemView(getApplicationContext());
            }else{
                view = (MonthItemView) convertView;
            }

            // 각 데이터들이 어디에 들어가 있는지
            view.setDay(items[position].day);

            return view;
        }
    }

}
