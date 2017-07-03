package com.example.jhjun.study_1;

/**
 * Created by jhjun on 2017-07-03.
 */

public class Parent {
    int mflag = 0;
    public Parent(int flag){
        mflag = flag;
    }

    public Parent(){
        mflag = 1;
    }
}

class child extends Parent{

    public child(int flag){
        super(flag);  // 핵심은 super. 생성자만 만들고 super가 없으면 의미없음.
    }

    private void something(){

    }
}

/*
부모의 명시적인 초기화가 있으면 자식도 초기화를 가져야한다.  -> 안그러면 자기가 상속받은 부모의 정보가 완전하지 않으므로..
부모 클래스에서 초기화가 없으면 자식도 생성자를 갖지 않아도됨.
 */


