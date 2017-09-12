package com.jjunji.android.autologin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText et_id, et_pw;
    CheckBox chk_auto;
    Button btnLogin;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        chk_auto = (CheckBox) findViewById(R.id.chk_auto);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("chk_auto", false)){
            et_id.setText(setting.getString("ID", ""));
            et_pw.setText(setting.getString("PW", ""));
            chk_auto.setChecked(true);
        }

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(chk_auto.isChecked()){
            Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show();
            String ID = et_id.getText().toString();
            String PW = et_pw.getText().toString();

            editor.putString("ID", ID);
            editor.putString("PW", PW);
            editor.putBoolean("chk_auto", true);
            editor.commit();
        }else{
            editor.clear();
            editor.commit();
        }
    }
}
