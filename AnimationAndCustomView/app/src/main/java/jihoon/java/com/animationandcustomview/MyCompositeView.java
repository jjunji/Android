package jihoon.java.com.animationandcustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by jhjun on 2017-08-29.
 */
// context만 받아도 되는데 속성 값을 받는 생성자를 하나 더 추가 하는 이유는 inflate 할때 xml 속성을 받기 위해

public class MyCompositeView extends FrameLayout {
    private View mView;

    public MyCompositeView(@NonNull Context context) {
        this(context, null);
    }

    public MyCompositeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCompositeView(@NonNull final Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 초기화 구분
        Log.i(MyCompositeView.class.getName(),"InflateView");
        mView = LayoutInflater.from(context).inflate(R.layout.item_menu, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCompositeView);
        int leftButtonColor = ta.getColor(R.styleable.MyCompositeView_leftButtonColor, 0);
        if (leftButtonColor != 0) {
            mView.findViewById(R.id.clickbutton).setBackgroundColor(leftButtonColor);
        }
        findViewById(R.id.clickbutton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "얍", Toast.LENGTH_SHORT).show();
            }
        });
    }
}