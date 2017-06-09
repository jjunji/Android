package com.example.jhjun.sqliteorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jhjun on 2017-06-09.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static int DATA_VERSION = 1;

    private static DBHelper instance = null;

    // DBHelper 를 메모리에 하나만 있게 해서 효율을 높혀보세~~~~
    // Singleton 으로 구성해보세.
    public static DBHelper getInstance(Context context){
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    // 최초 호출될 때 databas.db 파일을 /data/data/패키지명/database/ 디렉터리 아래에 생성해준다.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    // 최초에 생성되면 버전체크를 통해서 onCreate를 호출한다.
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // 여기서 테이블을 생성해야 한다.
        try {
            TableUtils.createTable(connectionSource, Memo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 데이터베이스 버전이 업그레이드 되면 호출된다.
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            // Memo 테이블의 특정 컬럼만 변경되었을 경우
            // 1. 기존 테이블을 백업하기 위한 임시 테이블을 생성하고 데이터를 담아둔다.
            //  예) create table temp_memo  <- 기존 데이터
            // 2. Memo 테이블을 삭제하고 다시 생성한다.
            // 3. 백업된 데이터를 다시 입력한다.
            // 4. 임시 테이블 삭제
    }

    // Create - 데이터를 입력 / 값을 넣는다는 것은 인자가 있다는 말이겠지. 입력단위는 클래스
    // public static이 아니라는 것은 이미 new가 되어 메모리에 있는 자원을 사용할 수 있다는 것?

    public void create(Memo memo){
        try {
            // 1. 테이블에 연결(데이터처리) , 정해져있음.
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터 입력
            dao.create(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read
    public List<Memo> readAll(){
        List<Memo> datas = null;
        try {
            // 1. 테이블에 연결(데이터처리) , 정해져있음.
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터 전체 읽어오기.
            datas = dao.queryForAll(); // 테이블의 전체 데이터를 가져옴. 리턴 형태가 List
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    // Read one
    public Memo read(int memoid){
        Memo memo = null;
        try {
            // 1. 테이블에 연결(데이터처리) , 정해져있음.
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터 한개 읽어오기
            memo = dao.queryForId(memoid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memo;
    }

    // Search - 데이터 검색하기
    public List<Memo> search(String word){
        List<Memo> datas = null;
        try {
            // 1. 테이블에 연결(데이터처리) , 정해져있음.
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터 검색하기
            String query = "select * from memo where content like '%"+word+"%'";
            GenericRawResults<Memo> temps = dao.queryRaw(query, dao.getRawRowMapper()); // 테이블의 전체 데이터를 가져옴. 리턴 형태가 List
            datas = temps.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    // Update
    public void update(Memo memo){
        try {
            // 1. 테이블에 연결
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터를 수정
            dao.update(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Object
    public void delete(Memo memo){
        try {
            // 1. 테이블에 연결
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터를 삭제
            dao.delete(memo);
            // * 참고 : 아이디로 삭제
            // dao.deleteById(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Delete By Id
    public void delete(int id){
        try {
            // 1. 테이블에 연결
            Dao<Memo,Integer> dao = getDao(Memo.class);
            // 2. 데이터를 삭제
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
