package jihoon.java.com.font;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jhjun on 2017-08-08.
 */

public class BaseActivity extends Activity {

    private static Typeface typeface;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(typeface == null) {
            typeface = Typeface.createFromAsset(this.getAssets(), "font/yaFont.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if(v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}