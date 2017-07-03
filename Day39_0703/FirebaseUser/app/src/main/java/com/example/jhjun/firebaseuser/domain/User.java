package com.example.jhjun.firebaseuser.domain;

/**
 * Created by jhjun on 2017-07-03.
 */

// 데이터 저장을 위한 User 객체

public class User {
    // 멤버 필드, 속성, 멤버 변수, 전역 변수
    public String username;
    public String email;
    public String password;

    // 생성자
    public User(){
        // User data = new User("홍길동".)
    }

    // 파라미터가 있는 생성자 오버로드
    public User(String username, String email, String password){
        this.username = username;
        this.email= email;

        // password를 단방향 암호화
        this.password = password;
    }
}
