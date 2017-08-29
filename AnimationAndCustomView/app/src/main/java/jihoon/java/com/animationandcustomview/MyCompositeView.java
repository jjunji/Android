package jihoon.java.com.animationandcustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Map;

/**
 * Created by jhjun on 2017-08-29.
 */
// context만 받아도 되는데 속성 값을 받는 생성자를 하나 더 추가 하는 이유는 inflate 할때 xml 속성을 받기 위해

public class MyCompositeView extends FrameLayout {
/*    public static final String BUTTON_LISTENER = "BTN";
    public static final String IMG_LISTENER = "IMG";
    public static final String TEXT_LISTENER = "txt";
    private static OnClickListener STATIC_LISTENER = null;*/
    private View mView;

    public MyCompositeView(@NonNull Context context) {
        this(context,null);
    }

    public MyCompositeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyCompositeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 초기화 구분
        mView = LayoutInflater.from(context).inflate(R.layout.item_menu, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCompositeView);
        int leftButtonColor = ta.getColor(R.styleable.MyCompositeView_leftButtonColor, 0);
        if(leftButtonColor != 0){
            mView.findViewById(R.id.clickbutton).setBackgroundColor(leftButtonColor);
        }
    }

}

/*        findViewById(R.id.clickbutton).setOnClickListener();
        findViewById(R.id.clicktext).setOnClickListener();
        findViewById(R.id.clickimg).setOnClickListener();*/


/*
    // 리스너 동적으로 넣기
    public void setListener(Map<String,OnClickListener> listeners){
        findViewById(R.id.clickbutton).setOnClickListener(listeners.get(BUTTON_LISTENER));
    }

    // 액티비티에서 설정한 리스너가 항상 살아있게 한다. (해당 액티비티가 죽어도) -> 위험
    public void setStaticListener(OnClickListener listener){
        STATIC_LISTENER = listener;
    }


 */
