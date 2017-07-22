package com.example.jhjun.practice_tdd;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// 목업 : 뭔가를 호출했을 때 정상 동작하도록 매핑 하는 것.
    // when - 무엇이 호출됬을 때, thenReturn - 무엇이 반환되는가   -> a가 됬을때 b가 리턴된다.

    // 러너로서 등록이 되야되고, 어노테이션 달아야되고,

// jvm에서 돌아가는 것.

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    static Networker networker;

    @Mock
    static Context context;

    @BeforeClass // 전체 클래스가 실행되기 전 한번만 실행됨. static으로 인스턴스가 생성되기 전에 수행됨.
    public static void log_beforeClass(){
        System.out.println("BeforeClass");
    }

    @Before // 각각의 테스트 메소드 별로 인스턴스가 생기는데, 인스턴스 생성 전에 수행.
    // 메소드 하나하나에
    public void log_before(){
        when(context.getString(R.string.app_name)).thenReturn("practice_TDD");
        networker = new Networker(context);
        networker.setDefault();
        System.out.println("before");
    }

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("test addition");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void division_isCorrect() throws Exception {
        System.out.println("test division");
        // (기대 값, 계산 식)
        assertEquals(16, 64/4);
    }

    @Test
    public void testNetworker(){
        assertEquals("OKpractice_TDD", networker.doNetwork());
    }

    @AfterClass
    public static void log_afterClass(){
        System.out.println("AfterClass");
    }

    @After  //  후에 생성됨.
    public void log_after(){
        System.out.println("after");
    }
}