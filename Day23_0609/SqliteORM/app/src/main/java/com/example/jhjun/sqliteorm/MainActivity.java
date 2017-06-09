package com.example.jhjun.sqliteorm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(this);

 //       List<Memo> datas;
        // 1. 데이터 입력
        /*for(int i=0; i<10; i++){
            Memo memo = new Memo();
            memo.setTitle("제목");
            memo.setContent("내용");
            helper.create(memo);
            //memo.setDate();
        }
*/
        // 2. 데이터 한개 읽어오기
/*        Memo one = helper.read(3);
        Log.i("Memo",one.getId()+" : title="+one.getTitle()+", content="+one.getContent());*/

        // 3. 데이터 전체 읽어오기
/*        datas = helper.readAll();
        for(Memo one : datas) {
            Log.i("Memo", one.getId() + " : title=" + one.getTitle() + ", content=" + one.getContent());
        }*/


// 4. 데이터 검색하기
        // 기본 데이터 넣기
//        helper.create(new Memo("제목1","내용1"));
//        helper.create(new Memo("제목2","내용2"));
//        helper.create(new Memo("제목3","내용3"));
//        helper.create(new Memo("제목4","내용4"));
//
//        // 검색하기
//        List<Memo> datas = helper.search("내용3");
//        for(Memo one : datas){
//            Log.i("Memo",one.getId()+" : title="+one.getTitle()+", content="+one.getContent());
//        }

        // 5. 수정하기
//        Memo memo = helper.read(3);
//        memo.setContent("내용");
//        helper.update(memo);

        // 6. 삭제하기
        helper.delete(5);
    }
}