package com.example.jhjun.memo_re.FileUtil;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by jhjun on 2017-05-29.
 */

public class FileUtil {
    private static final String TAG = "FileUtil";

    public static boolean write(Context context, String filename, String content){
        boolean result = false;
        Writer writer = null;
        try {
            // 생성되는 internal Storage 경로
            // /data/data/패키지명/files
            FileOutputStream fos = context.openFileOutput
                    (filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(fos);
            writer.write(content);
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                writer.close();
            }catch(Exception e){
                Log.e(TAG, e.toString());
            }
        }

        return result;
    }

    public static String read(Context context, String filename){
        String content = "";
        Reader reader = null ;
        int data ;
        char oneWord ;
        try {
            FileInputStream fis = context.openFileInput(filename);
            reader = new InputStreamReader(fis);
            while ((data = reader.read()) != -1) {
                oneWord = (char) data ;
                content = content + oneWord;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                reader.close() ;
            }catch(Exception e){
                Log.e(TAG, e.toString());
            }
        }
        // 2. 에러발생시 Toast 메시지를 출력하고, content에 "" 공백을 전달한다.

        return content;
    }
}
