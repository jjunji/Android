package com.example.jhjun.recycleragain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-06-07.
 */

public class B_Loader {

    // new를 하지 않고 데이터를 가지고 오는 형태로 만들 것

    // 여러개의 데이터를 가져올 것이므로 List사용함. (List -> 다형성)

    // Context를 넘기는 이유는 주소록에 접근하기 위한 Resolver를 사용해야하기 때문.
    public static List<A_Data> getData(Context context){
        // 로직 -> 여기서 데이터를 가져오면 됨.

        List<A_Data> datas =new ArrayList<>();

        // 데이터를 가져오는 커넥터 역할.
        ContentResolver resolver = context.getContentResolver();
        // URI는 테이블 구조
        // 데이터가 있는 테이블 주소
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 테이블에서 가져올 컬럼명을 정의.
        String proj[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 컨텐츠 리졸버로 데이터를 가져오기

        // 데이터를 가져오면 데이터를 가져온 형태가 Cursor형태.
        Cursor cursor = resolver.query(phoneUri, proj, null, null, null);

        // 커서를 가져왔으니까  cursor에 데이터 존재 여부를 먼저 체크
        if(cursor != null){
            // 데이터는 while문이나 for문을 통해 가져올 수 있는데, while이 적합.
            // cursor.moveToNext( )메소드가 true/false를 반환하기 때문에 조건식이 들어가는 while문이 좋다.
            // 하나하나의 열을 가져온다.
            while (cursor.moveToNext()){
                // 이렇게 가져올 수 있지만, index로 명시하는게 좋음. 가져오는 데이터의 타입과 변수명만 바꾸면 된다.
                //int id = cursor.getInt(0);
                int index = cursor.getColumnIndex(proj[0]);
                int id = cursor.getInt(index);

                index = cursor.getColumnIndex(proj[1]);
                String name = cursor.getString(index);

                index = cursor.getColumnIndex(proj[2]);
                String tel = cursor.getString(index);

                // 이 데이터들을 내가 미리 정의한 데이터 클래스에 담아주고
                A_Data data = new A_Data();
                // datas에 열 하나하나 단위의 데이터 클래스가 저장됨.
                // 주소록만큼 datas에 개수가 생성됨.
                data.setId(id);
                data.setName(name);
                data.setTel(tel);
                datas.add(data);
            }
        }
        cursor.close(); // 커서는 사용후 꼭 닫아야한다. -> 닫지 않으면 계속 열려있음.
                        // 데이터베이스, 이미지 계열의 스트림은 닫아주지 않으면 닫히지 않음.
        return datas; // 로직이 끝나면 datas를 반환.
    }
}
