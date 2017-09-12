package com.example.jhjun.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView;
    Button btnCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setView();
        setListener();
    }
    private void setView(){
        imageView = (ImageView) findViewById(R.id.imageView);
        btnCapture = (Button) findViewById(R.id.btnCapture);
    }
    private void setListener(){
        btnCapture.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        takePhoto();
    }

    Uri fileUri = null;
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            File photoFile = null;
            try {
                photoFile = createFile();
                if(photoFile != null){
                    // 마시멜로 이상 버전은 파일 프로바이더를 통해 권한을 획득
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                        // 롤리팝 버전은 권한 없이 획득
                    } else {
                        fileUri = Uri.fromFile(photoFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                }
            }catch(Exception e){
                Toast.makeText(getBaseContext(), "사진파일 저장을 위한 임시파일을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

        }else { // 롤리팝 미만 버전에서만 바로 실행
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }

    private File createFile() throws IOException {
        // 임시파일명 생성
        String tempFilename = "TEMP_"+System.currentTimeMillis();
        // 임시파일 저장용 디렉토리 생성
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        // 실제 임시파일을 생성
        File tempFile = File.createTempFile(
                tempFilename,
                ".jpg",
                tempDir
        );
        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;
                // 롤리팝 미만 버전에서는 data 인텐트에 찍은 사진의 uri 가 담겨온다.
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    imageUri = data.getData();
                }else{
                    imageUri = fileUri;
                }

                imageView.setImageURI(imageUri);
            }
        }
    }
}