package com.example.jhjun.customviewpractice2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by jhjun on 2017-07-03.
 */

public class FiveButton extends android.support.v7.widget.AppCompatButton{

    int status = 0;

    public static final int num_of_status = 5;

    public FiveButton(Context context) {
        this(context,null);
    }

    public FiveButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                status = (++status) % num_of_status;
                int color = Color.BLACK;
                switch (status){
                    case 0:
                        color = Color.BLACK;
                        break;
                    case 1:
                        color = Color.BLUE;
                        break;
                    case 2:
                        color = Color.RED;
                        break;
                    case 3:
                        color = Color.YELLOW;
                        break;
                    case 4:
                        color = Color.GREEN;
                        break;
                }
                setBackgroundColor(color);
            }
        });
        super.setOnClickListener(listener);
    }
}
