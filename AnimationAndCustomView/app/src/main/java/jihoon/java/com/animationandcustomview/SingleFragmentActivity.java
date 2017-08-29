package jihoon.java.com.animationandcustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SingleFragmentActivity extends AppCompatActivity {

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

    }
}


/*
    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener myImgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
 */