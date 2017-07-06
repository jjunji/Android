package com.example.jhjun.firebasebbs;

import com.example.jhjun.firebasebbs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhjun on 2017-07-04.
 */

public class Data {
    // 공용으로 사용되는 데이터 저장소
    // 모든 Activity에서 접근할 수 있다.
    public static List<Bbs> list = new ArrayList<Bbs>();
}

