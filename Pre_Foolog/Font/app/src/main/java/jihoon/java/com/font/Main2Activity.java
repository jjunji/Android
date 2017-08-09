package jihoon.java.com.font;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView textView3;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        font = Typeface.createFromAsset(this.getAssets(), "yaFontBold.ttf");
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setTypeface(font);
    }
}
