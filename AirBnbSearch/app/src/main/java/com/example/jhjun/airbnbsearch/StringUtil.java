package com.example.jhjun.airbnbsearch;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import static com.example.jhjun.airbnbsearch.R.id.btnCheckin;

/**
 * Created by jhjun on 2017-07-27.
 */


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
