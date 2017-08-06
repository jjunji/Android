package jihoon.java.com.mycalendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView txtMonth;
    private GridView monthView;
    //private MonthAdapter adapter;
    CalendarAdapter adapter;


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

        adapter = new CalendarAdapter(getApplicationContext());
        monthView.setAdapter(adapter);
        adapter.setNowMonth();
        txtMonth.setText(adapter.getCurrentYear() + "년" + adapter.getCurrentMonth() + "월");
        //adapter.notifyDataSetChanged();

        Button btnPrevious = (Button) findViewById(R.id.btnPrevious);
        // 이전 월을 설정하고 그대로 표시됨.
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setPreviousMonth();
                txtMonth.setText(adapter.getCurrentYear() + "년" + adapter.getCurrentMonth() + "월");
                // 어댑터가 바뀌었으니 notifyDataSetChanged
                adapter.notifyDataSetChanged();
            }
        });
        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNextMonth();
                txtMonth.setText(adapter.getCurrentYear() + "년" + adapter.getCurrentMonth() + "월");
                adapter.notifyDataSetChanged();
            }
        });

    }

    // 선택위젯 활용 -> 어답터 생성
 /*   class MonthAdapter extends BaseAdapter{

        Calendar calendar;
        ArrayList<String> dayList = new ArrayList<String>();

        int lastDay; // 마지막 날
        int curYear; // 현재 년도
        int curMonth; // 현재 월

        @RequiresApi(api = Build.VERSION_CODES.N)
        public MonthAdapter() {

            Date date = new Date();
            calendar = Calendar.getInstance();
            calendar.setTime(date);  // calendar 에 현재 시간 설정.
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setNowMonth(){
            calendar.add(Calendar.MONTH, curMonth);
            recalculate(); // 해당 월의 첫날, 마지막 날 계산
            resetDayNumbers2(); // 실제 아이템 뷰에 넣어서 뷰로 보여줌
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setPreviousMonth(){
            calendar.add(Calendar.MONTH, -1); // -1 : 이전 달로 이동
            recalculate(); // 해당 월의 첫날, 마지막 날 계산
            resetDayNumbers2(); // 실제 아이템 뷰에 넣어서 뷰로 보여줌
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setNextMonth(){
            calendar.add(Calendar.MONTH, +1);
            recalculate(); // 해당 월의 첫날, 마지막 날 계산
            resetDayNumbers2(); // 실제 아이템 뷰에 넣어서 뷰로 보여줌
        }

        public int getCurrentYear(){
            return curYear;
        }

        public int getCurrentMonth(){
            return (curMonth+1);
        }

        //------------- 어답터 생성 -> 메인액티비 안에서 그리드뷰안에 설정 할 수 있게 됨.----------------

        // 시작하는 요일 확인
        @RequiresApi(api = Build.VERSION_CODES.N)
        // 달력을 선택한 달의 연도 & 달로 설정하고, 1일이 시작되는 요일을 재계산하는 메소드
        public void recalculate() {
            // 날짜를 현재 달의 1일로 설정.
            calendar.set(Calendar.DAY_OF_MONTH, 1);  // -> 1 ex) 현재 8월이면 8월 1일 에서 1을 가져옴.
            curYear = calendar.get(Calendar.YEAR);  // 2017
            curMonth = calendar.get(Calendar.MONTH);  // 7 (0 부터 시작) -> 8월
            lastDay = getLastDay();  // 셋팅 달의 마지막 날이 몇일인지 출력 -> 31

            Log.i("Main","DAY_OF_MONTH==============="+ Calendar.DAY_OF_MONTH);
            Log.i("Main","curYear==============="+ curYear);
            Log.i("Main","curMonth==============="+ curMonth);
            Log.i("Main","lastDay==============="+ lastDay);
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        public void resetDayNumbers2(){

            dayList.clear();
            dayList.add("일");
            dayList.add("월");
            dayList.add("화");
            dayList.add("수");
            dayList.add("목");
            dayList.add("금");
            dayList.add("토");

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 위에서 1일로 셋팅했으므로 1일이 무슨 요일인지 확인 -> 3(화)
            Log.i("Main","DAY_OF_WEEK==============="+ dayOfWeek);

            for (int i = 1; i < dayOfWeek; i++) {
                dayList.add("");
            }

            for (int i = 1; i <= lastDay; i++) {
                    dayList.add("" + (i));
            }
        }

        // 각 월 마다의 마지막 날 반환
        public int getLastDay() {
            switch (curMonth){
                case 0: // 1월
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
            return dayList.size();
        }

        @Override
        public Object getItem(int position) {
            return dayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MonthItemView view = null;


            if(convertView == null){
                view = new MonthItemView(getApplicationContext());
            }else{
                view = (MonthItemView) convertView;
            }

            // 각 데이터들이 어디에 들어가 있는지

            view.setDay(dayList.get(position),position);
            //view.setDay(items[position].day, position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("View","position================="+ position);
                }
            });

            return view;
        }
    }*/

}
