package com.jjunji.android.num3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView01;
    ImageView imageView02;

    Button button01;
    Button button02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView01 = (ImageView) findViewById(R.id.imageView01);
        imageView02 = (ImageView) findViewById(R.id.imageView02);
        button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);

        imageView01.setImageResource(R.drawable.checkbox_korea_c);

        button01.setOnClickListener(this);
        button02.setOnClickListener(this);

        /*Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveImageUp();
            }
        });

        Button button02 = (Button) findViewById(R.id.button02);
        button02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveImageDown();
            }
        });

        moveImageUp();*/
    }

    public void moveImageDown() {
        imageView01.setImageResource(0);
        imageView02.setImageResource(R.drawable.checkbox_korea_c);

      /*  imageView01.invalidate();
        imageView02.invalidate();*/
    }

    public void moveImageUp() {
        imageView01.setImageResource(R.drawable.checkbox_korea_c);
        imageView02.setImageResource(0);

/*        imageView01.invalidate();
        imageView02.invalidate();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button01 :
                moveImageUp();
                break;
            case R.id.button02:
                moveImageDown();
                break;
        }
    }
}
