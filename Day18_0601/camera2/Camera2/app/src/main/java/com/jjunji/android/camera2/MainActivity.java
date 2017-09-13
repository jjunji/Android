package com.jjunji.android.camera2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);

                    }else{
                        fileUri = Uri.fromFile(photoFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }

    private File createFile() throws IOException{
        String tempFileName = "TEMP_" + System.currentTimeMillis();
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        if(!tempDir.exists()){
            tempDir.mkdirs();
        }

        File tempFile = File.createTempFile(tempFileName, ".jpg", tempDir);

        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if(resultCode == RESULT_OK){
                Uri imageUri = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    imageUri = data.getData();
                }else{
                    imageUri = fileUri;
                }
            }
        }
    }
}
