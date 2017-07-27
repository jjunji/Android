# Airbnb - search
---

뷰에 들어가는 문자열 미리 정의하기

* 아래와 같은 방법으로 미리 문자를 지정해 놓으면, 해당 문자가 삽입되는 뷰가 여러개인 경우 다른 문자로 바꾸게 된다면 한번에 처리할 수 있다. 또한 다른 나라 언어를 정의한 디렉토리를 만들어서 관리하면, 다국 언어를 지원할 수 있다.
 ex) project - res - 새 디렉토리 생성 - values-ko(대한민국) - 국가코드 입력 후 생성
*   layout xml에서 Language를 눌러 정의한 언어를 선택할 수 있다.
```java
<resources>
    <string name="app_name">AirBnbSearch</string>
    <string name="action_settings">Settings</string>

    <string name="title_select_date">Select Period</string>
    <string name="title_guest">Guests</string>
    <string name="title_accommodation_type">Accommodation Types</string>
    <string name="title_price">Price</string>
    <string name="title_amenities">Amenities</string>

    <string name="hint_start_date">Check in</string>
    <string name="hint_end_date">Check out</string>
    <string name="hint_select_date">Select date</string>
    
</resources>
```
한국어
```java
<resources>
    <string name="app_name">에어비앤비검색</string>
    <string name="action_settings">설정</string>

    <string name="title_select_date">숙박 기간</string>
    <string name="title_guest">게스트</string>
    <string name="title_accommodation_type">숙소 유형</string>
    <string name="title_price">가격 범위</string>
    <string name="title_amenities">편의시설</string>

</resources>
```
---
안드로이드 Button에 html코드를 입력하는 방법
```java
    private void setCalendarButtonText() {
        setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
        setButtonText(btnCheckout, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText) {
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText;
        StringUtil.setHtmlText(btn, inText);
    }
```
* TextView 에 Html 을 표현하기 위해서는 android.text.Html 클래스를 사용한다.
* Html 로 이루어진 문자열을 Html.fromHtml() 함수로 변환한뒤
TextView 의 text 셋팅값 파라미터로 넘기면 된다.
* String 타입의 변수 inText에 html로 정의한 텍스트 스타일을 StringUtil에 정의한
  setHtmlText( ) 로 전달했다.
  
---
버튼 text를 html코드로 변환해 주는 부분
```java
public class StringUtil {
    public static void setHtmlText(TextView target, String text){   // StringUtil클래스로 함수로 만들어서 뺄 때는 인자로 TextView를 받는다 -> setText가 TextView를 상속받았기 때문
        target.setAllCaps(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text),TextView.BufferType.SPANNABLE);
        }else {
            // 누가 이상 버전은 fromHtml 의 두 번째 인자로 Html.FROM_HTML_MODE_LEGACY 필요
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}
```
* 버튼 안의 문자를 html코드로 입력하는 것이지만, 내부에 들어가는 setText 메소드가 textView를 상속받았기 때문에 TextView 형태로 값을 넘겨 받는다.
*  안드로이드 버튼에 글자를 입력하면 default로 대문자로 설정되있기 때문에
   .setAllCaps(false); 를 입력해 설정을 풀어줄 수 있다.

---
켈린더뷰(달력) 사용하기
```java
    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            month = month + 1; // 배열 형태로 가지고 있기 때문.(1월 ~ 12월)
            Log.i("Calendar", "year:" + year + ", month:" + month + ", dayOfMonth:" + dayOfMonth);
            String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth);
            //String theDay = year+"-"+month+"-"+dayOfMonth;
            switch (checkStatus) {
                case CHECK_IN:
                    search.checkinDate = theDay;
                    setButtonText(btnCheckin, getString(R.string.hint_start_date), search.checkinDate);
                    break;
                case CHECK_OUT:
                    search.checkoutDate = theDay;
                    setButtonText(btnCheckout, getString(R.string.hint_end_date), search.checkoutDate);
                    break;
            }
        }
    };
```
*  String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth) 을 사용하여 2017 - 07 - 11 과 같은 형태로 날짜를 출력할 수 있다.
*  OnDateChangeListener 가 인자로 받는 것은 뷰와, 이벤트가 발생한 날짜의 연도 월, 일 순이다. (month는 배열 형태로 가지고 있기 때문에 +1을 해줘야함.)
*  체크인, 체크아웃 버튼에 따라 상태 값을 저장하고(flag) 각 버튼에 해당하는 날짜를 기억한다.

---
라디오 버튼 커스텀하기

* project 보기 모드로 디렉토리 트리를 띄운 후 res 에서 drawable-xxxhdpi 디렉토리를 생성한다.
*  라디오 버튼에 이용할 이미지 파일을 업로드
* drawable 디렉토리에 상태에 해당하는 이미지를 설정하는 코드를 입력
```java
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 체크박스 해제상태 -->
    <item android:drawable="@drawable/radio_double_bed_normal" android:state_checked="false" />
    <!-- 체크박스 선택상태 -->
    <item android:drawable="@drawable/radio_double_bed_checked" android:state_checked="true" />
    <!-- default state -->
    <item android:drawable="@drawable/radio_double_bed_normal" />
</selector>
```
