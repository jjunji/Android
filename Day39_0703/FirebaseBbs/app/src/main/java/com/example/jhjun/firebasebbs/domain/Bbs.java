package com.example.jhjun.firebasebbs.domain;

/**
 * Created by jhjun on 2017-07-03.
 */

public class Bbs {
    public String id;  // 파이어베이스의 push 로 자동생성된다.
    public String title;
    public String author;
    public String content;
    public long date;
    public long count; // 조회수
}
