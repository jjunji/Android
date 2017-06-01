package com.example.jhjun.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhjun.contacts.domain.Data;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class ContactActivity extends AppCompatActivity {

    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        listView = (RecyclerView) findViewById(R.id.listView);

        // List<data> datas = getContacts() 어쩌고 이걸 안하고 getContacts() 이렇게만 써도 리턴이 되는 것을 알 수 있겠죠?
        for(Data data :getContacts()){
            Log.i("Contacts", "이름 = " + data.getName() + "tel"+data.getTel());
        }
    }

    public List<Data> getContacts(){
        // 데이터베이스 혹은 content resolver를 통해 가져온 데이터를 적재할
        // 데이터 저장소를 먼저 정의한다.

        List<Data> datas = new ArrayList<>();  // ArrayList -> List로 쓴다는 것은 다형성을 이해하고 있다?

        // 일종의 Database 관리 툴
        // 전화번호부에 이미 만들어져 있는 Content Provider 를 통해
        // 데이터를 가져올 수 있다.

        ContentResolver resolver = getContentResolver();  // 카톡에서 주소록 가져오는 것 처럼.

        // 1. 데이터 컨텐츠 URI(자원의 주소)를 정의
        //  phonUri -> 테이블의 이름이라고 생각.
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        // 미리 정의 된 클래스

        // URI에는 여러 데이터가 들어 있기 때문에
        // 2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null); // -> 배열처럼 값이 들어있다?
        // 4. 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다.
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스 값을 조회하고
                int idIndex = cursor.getColumnIndex(projections[0]);
                // 4.2 해당 index를 사용해서 실제값을 가져온다.
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(nameIndex);

                int telIndex = cursor.getColumnIndex(projections[2]);
                String tel = cursor.getString(telIndex);

                // 5. 내가 설계한 데이터 클래스에 담아준다.
                Data data = new Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                // 6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
                datas.add(data);
            }
        }
        return datas;
    }
}

/* 앱을 개발할 때 내가 데이터를 제공하고자 한다면 컨택트 프로바이더를 만들어야함.
   핸드폰의 기본 앱들은 모두 프로바이더를 가지고있나? -> 아님. 데이터를 제공하지 않는 계산기 등은 필요 없음.
 */
