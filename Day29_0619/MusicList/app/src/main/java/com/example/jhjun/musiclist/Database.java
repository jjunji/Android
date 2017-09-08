package com.example.jhjun.musiclist;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jhjun on 2017-06-19.
 */

// 음악 파일이 있는 저장소
// 음악 목록이 저장되어 있는 저장소를 컨트롤하는 객체
public class Database {

    // C R U D 가 있어야겠지!
    // 입력은 없을 것이고, 수정은 있겠지? -> 수정 함수를 만들자
    // 데이터를 수정하는 함수, 음악제목, 앨범 이름 등을 변경.
    public void updata(){

    }

    // 음원 데이터를 삭제하는 함수
    public void delete(){

    }
    // 음원 데이터를 읽어오는 함수
    public List<Music> read(Context context){
        // 0. 먼저 Album Art 가 있는 테이블에서 전체 이미지를 조회해서 저장해 둔다
        setAlbumArt(context);
        // (음악ID, 앨범아트 이미지경로) = hashMap
        // (     1, /sdcar/exteranl/0/medai..... image.jpg)
        // (     2, /sdcar/exteranl/0/medai..... image3.jpg)

        // 1. 읽어 올 데이터가 무엇인지 알아야 한다.
        // 읽어 올 데이터 = 음악파일
        // 2. 음악 파일을 읽어오기 위한 도구 선정 -> 일반적인 프로그래밍에서 File I/O
        //                                          안드로이드에서 Content Resolver

        // 가. 읽어올 데이터의 주소를 설정
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 나. 읽어올 데이터의 구체적인 컬럼(속성)을 정의
        String projection[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST
        };

        // 다. 위에 정의된 주소와 설정값으로 목록을 가져오는 Query(질의)를 한다.
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(musicUri, projection, null, null, null);
                        // 커서가 가져올 데이터를 가리키고 있는 것. (데이터베이스와 계속 연결되어 있는 상태여야함)
                        // 커서를 통해 연결되는 것.
        // 라. 반복문을 돌면서 cursor에 있는 데이터를 다른 저장소에 저장한다.
        ArrayList<Music> datas = new ArrayList<>();
        if(cursor != null){
            while (cursor.moveToNext()){
                Music music = new Music();
                //Log.d("projection","projection[0]========================================"+projection[0]);
                music.id = getValue(cursor,projection[0]); // <- 커서에서 id를 꺼내서 담는다.
                music.title = projection[1];
                music.albumId = projection[2];
                music.artist = projection[3];
                //Log.d("getString","cursor.getString(0)=========================================="+cursor.getString(0));
                //music.id = cursor.getString(0);

                // 음원 uri
                music.musicUri = makeMusicUri(music.id);
                // 앨범아트 가져오기
                music.albumArt = albumMap.get(Integer.parseInt(music.id));

                datas.add(music);
            }
        }
        cursor.close();

        return datas;
    }

    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    private Uri makeMusicUri(String musicId){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // content://mediaStore/external/audio/media
        return Uri.withAppendedPath(contentUri, musicId);
    }

    // 앨범아트 데이터만 따로 저장
    private static HashMap<Integer, String> albumMap = new HashMap<>();

    private static void setAlbumArt(Context context) {
        String[] Album_cursorColumns = new String[]{
                MediaStore.Audio.Albums.ALBUM_ART, //앨범아트
                MediaStore.Audio.Albums._ID
        };
        Cursor Album_cursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                Album_cursorColumns, null, null, null);
        if (Album_cursor != null) { //커서가 널값이 아니면
            if (Album_cursor.moveToFirst()) { //처음참조
                int albumArt = Album_cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int albumId = Album_cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                do {
                    if (!albumMap.containsKey(Integer.parseInt(Album_cursor.getString(albumId)))) { //맵에 앨범아이디가 없으면
                        albumMap.put(Integer.parseInt(Album_cursor.getString(albumId)), Album_cursor.getString(albumArt)); //집어넣는다
                    }
                } while (Album_cursor.moveToNext());
            }
        }
        Album_cursor.close();
    }
}

class Music {
    String id;
    String title;
    String albumId;
    String artist;

    Uri musicUri;
    String albumArt;
}




/*
cursor는 값을 가지고 있는데, 바로 값을 꺼내서 사용하면 되는데,
어떤 이유로 그 값을 다시 리스트에 담아서 사용할까 -> 모든 데이터 베이스에서 동일한데,
cursor는 유지하는 비용이 크다.(메모리 같은 자원 소모. -> 메모리 저장소 옮겨서 사용해야 됨??)
Database 커넥션도 동일하게 연결 유지에 사용되는 비용이 아주 크다.
그래서 데이터베이스에서 제공되는 연결 객체는 사용 후 즉시 반환하는 것이 성능향상에 도움이 된다.
 */

/*
while (cursor.moveToNext()){
                Music music = new Music();

   어레이 리스트를 만들고 뮤직이 생성되면 생성된 뮤직이 계속 데이터스에 저장될 것,
   데 이터베이스라는 객체도 데이터스를 참조하고 뮤직은 참조되고 있다. 리턴 데이터를 하게 되면
   read를 호출한 어떤 객체로 날아간다. 두번 째 호출하면 뮤직 객체가 참조하는게 없어지므로 삭제? -> 연쇄삭제.
 */