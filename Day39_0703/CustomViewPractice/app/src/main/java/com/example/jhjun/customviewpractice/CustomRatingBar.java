package com.example.jhjun.customviewpractice;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by jhjun on 2017-07-03.
 */

// 부모 뷰가 생성자가 있기 때문에 처음에 생성자를 만들라고 강제함.(빨간줄)
// 생성자 만들 시에 어트리뷰트셋(xml에 쓰는 문장들)이 인자로 있는 것은 -> xml을 통해서 만들겠다.
// 정적인 화면 : xml
// 동적인 화면 : xml로 정의할 수 없기 때문에 코드로..-> 어트리뷰트셋이 필요없음, Context만 있으면 된다.

public class CustomRatingBar extends LinearLayout{
    // this로 자기 자신의 생성자를 호출할 수 있다는 것 알아두기.
    public CustomRatingBar(Context context) {
        this(context,null);  // CustomRatingBar(Contex context, @Nullable AttributeSet attrs) 호출
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs, 0);  // 또 아래 생성자 호출, 즉 맨 위 부터 차례 차례 연쇄로 호출
    }
    // 왜 이런식으로 하는가? -> 성능적인 측면에서
    public CustomRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);  // 마지막으로 호출됨.
        View view = LayoutInflater.from(context).inflate(R.layout.custom_ratingbar,this,false); // this : 내가 자식뷰를 갖겠다.(부모 뷰로 나 자신을..)
        addView(view); // 위에 2줄은 inflate(R.layout.custom_ratingbar,this,false) -> false -> true 로 바꾼거랑 같음.

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Button star1 = (Button) findViewById(R.id.star1);
        star1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(android.R.drawable.star_big_on);
            }
        });
        Button star2 = (Button) findViewById(R.id.star2);
        Button star3 = (Button) findViewById(R.id.star3);
        Button star4 = (Button) findViewById(R.id.star4);
        Button star5 = (Button) findViewById(R.id.star5);

    }

    /*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }*/
    private void init(){

    }
}




/*

 */


/*

    public CustomRatingBar(Context context) {
        super(context);
        init();
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    // 연쇄적으로 생성자를 호출하지 않으면 위와 같이 init(); 을 4번 쓰게된다. 생성자 쓰면 2번(1번인데 1번은 타겟 Api때문에)
    // 즉 초기화 과정 몰빵
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){

    }


 */