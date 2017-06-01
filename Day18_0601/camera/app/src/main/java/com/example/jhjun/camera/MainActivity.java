package com.example.jhjun.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final int REQ_PERMISSION = 100;
    Button btnGallery, btnCamera;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        // 리스너
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);

        // 버튼 잠금
        btnCamera.setEnabled(false);
        btnGallery.setEnabled(false);

        // 마시멜로 이상버전에서만 런타임 권한체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }else {
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        //1 권한체크 - 특정권한이 있는지 시스템에 물어본다
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            // 2. 권한이 없으면 사용자에 권한을 달라고 요청
            String permissions[] = { Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA };
            requestPermissions(permissions ,REQ_PERMISSION); // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 사용자가 승인을 했음
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                init();
                // 3.2 사용자가 거절 했음
            }else{
                Toast.makeText(this, "권한요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init(){
        btnCamera.setEnabled(true);
        btnGallery.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnGallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요") , 100);
                break;
            case R.id.btnCamera:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case 100:
                    Uri imageUri = data.getData();
                    Log.i("Gallery","imageUri========================="+imageUri.getPath());
                    imageView.setImageURI(imageUri);
                    break;
            }
        }
    }
}