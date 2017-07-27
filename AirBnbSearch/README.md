# Airbnb - search
---

�信 ���� ���ڿ� �̸� �����ϱ�

* �Ʒ��� ���� ������� �̸� ���ڸ� ������ ������, �ش� ���ڰ� ���ԵǴ� �䰡 �������� ��� �ٸ� ���ڷ� �ٲٰ� �ȴٸ� �ѹ��� ó���� �� �ִ�. ���� �ٸ� ���� �� ������ ���丮�� ���� �����ϸ�, �ٱ� �� ������ �� �ִ�.
 ex) project - res - �� ���丮 ���� - values-ko(���ѹα�) - �����ڵ� �Է� �� ����
*   layout xml���� Language�� ���� ������ �� ������ �� �ִ�.
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
�ѱ���
```java
<resources>
    <string name="app_name">�����غ�˻�</string>
    <string name="action_settings">����</string>

    <string name="title_select_date">���� �Ⱓ</string>
    <string name="title_guest">�Խ�Ʈ</string>
    <string name="title_accommodation_type">���� ����</string>
    <string name="title_price">���� ����</string>
    <string name="title_amenities">���ǽü�</string>

</resources>
```
---
�ȵ���̵� Button�� html�ڵ带 �Է��ϴ� ���
```java
    private void setCalendarButtonText() {
        setButtonText(btnCheckin, getString(R.string.hint_start_date), getString(R.string.hint_select_date));
        setButtonText(btnCheckout, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText) {
        // ��ư�� �پ��� ������ ��Ʈ �����ϱ�
        // ������ android:textAllCaps="false" ���� �ʿ�
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText;
        StringUtil.setHtmlText(btn, inText);
    }
```
* TextView �� Html �� ǥ���ϱ� ���ؼ��� android.text.Html Ŭ������ ����Ѵ�.
* Html �� �̷���� ���ڿ��� Html.fromHtml() �Լ��� ��ȯ�ѵ�
TextView �� text ���ð� �Ķ���ͷ� �ѱ�� �ȴ�.
* String Ÿ���� ���� inText�� html�� ������ �ؽ�Ʈ ��Ÿ���� StringUtil�� ������
  setHtmlText( ) �� �����ߴ�.
  
---
��ư text�� html�ڵ�� ��ȯ�� �ִ� �κ�
```java
public class StringUtil {
    public static void setHtmlText(TextView target, String text){   // StringUtilŬ������ �Լ��� ���� �� ���� ���ڷ� TextView�� �޴´� -> setText�� TextView�� ��ӹ޾ұ� ����
        target.setAllCaps(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text),TextView.BufferType.SPANNABLE);
        }else {
            // ���� �̻� ������ fromHtml �� �� ��° ���ڷ� Html.FROM_HTML_MODE_LEGACY �ʿ�
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}
```
* ��ư ���� ���ڸ� html�ڵ�� �Է��ϴ� ��������, ���ο� ���� setText �޼ҵ尡 textView�� ��ӹ޾ұ� ������ TextView ���·� ���� �Ѱ� �޴´�.
*  �ȵ���̵� ��ư�� ���ڸ� �Է��ϸ� default�� �빮�ڷ� �������ֱ� ������
   .setAllCaps(false); �� �Է��� ������ Ǯ���� �� �ִ�.

---
�̸�����(�޷�) ����ϱ�
```java
    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            month = month + 1; // �迭 ���·� ������ �ֱ� ����.(1�� ~ 12��)
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
*  String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth) �� ����Ͽ� 2017 - 07 - 11 �� ���� ���·� ��¥�� ����� �� �ִ�.
*  OnDateChangeListener �� ���ڷ� �޴� ���� ���, �̺�Ʈ�� �߻��� ��¥�� ���� ��, �� ���̴�. (month�� �迭 ���·� ������ �ֱ� ������ +1�� �������.)
*  üũ��, üũ�ƿ� ��ư�� ���� ���� ���� �����ϰ�(flag) �� ��ư�� �ش��ϴ� ��¥�� ����Ѵ�.

---
���� ��ư Ŀ�����ϱ�

* project ���� ���� ���丮 Ʈ���� ��� �� res ���� drawable-xxxhdpi ���丮�� �����Ѵ�.
*  ���� ��ư�� �̿��� �̹��� ������ ���ε�
* drawable ���丮�� ���¿� �ش��ϴ� �̹����� �����ϴ� �ڵ带 �Է�
```java
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- üũ�ڽ� �������� -->
    <item android:drawable="@drawable/radio_double_bed_normal" android:state_checked="false" />
    <!-- üũ�ڽ� ���û��� -->
    <item android:drawable="@drawable/radio_double_bed_checked" android:state_checked="true" />
    <!-- default state -->
    <item android:drawable="@drawable/radio_double_bed_normal" />
</selector>
```
