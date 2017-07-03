package com.example.jhjun.customviewpractice2;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jhjun on 2017-07-03.
 */

// CompoundDrawableTextView

public class Compound extends android.support.v7.widget.AppCompatTextView{

    public Compound(Context context) {
        super(context);
    }

    public Compound(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Compound,0,0); // 이미 스타일이 정의된 속성을 가져온다.
        int compoundLeft = ta.getResourceId(R.styleable.Compound_compoundLeft,0);
        setCompoundDrawablesWithIntrinsicBounds(compoundLeft,0,0,0);

        ta.recycle();
    }
}
