
## 클릭 리스너 등록 3가지 방법

 1. 위젯을 사용하는 객체가 상속받아서 구현

 2. 객체내에서 변수로 생성

 3. setOnclickListener 함수에 익명 객체로 전달

***

먼저
```java
Button btn = (Button) findViewById(R.id.btnClick);
```
버튼을 사용하기 위해 Button 객체를 findViewById 메소드를 통해 xml에서 id값을 찾은 후 btn 변수에 할당. (공통)

**1번 형태**
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener
```
View.OnclickListener 인터페이스를 구현하여 사용한다.
인터페이스를 구현하기 위해서는 메소드를 정의해야한다.

```java
@Override
public void onClick(View v) {
    
}
```
그 다음 
```java
btn.setOnClickListener(this);
```
btn변수에 클릭 리스너를 달아주고 setOnClickListener(this)에서  this <- 로 onClick(View v) 의 메소드를 통해 View 객체를 받아오는 것이다. 즉, public void onClick(View v)의 반환 값은 View 타입. v는 단순히 변수를 의미(이름을 바꿔도 상관없다)
```java
@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClick:
                break;
            case R.id.btnClick2:
                break;
        }
    }
```
이와 같이 버튼이 여러개 일 때, 뷰(v)의 id 값을 가져와 리스너에 넘겨주고 그에 해당하는 행동을 보여줘야 할 경우에 쓰인다.

***

**2번 형태**
```java
btn.setOnClickListener(listener);
```
와 같이 선언 후 listener에 대한 부분을 따로 구현한다.
```java
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
        }
    };
```
listener 변수에 클릭이 일어났을 때 액션을 담는다.
다음 listener와 버튼을 setOnClickListener를 연결하는 것이다.

***
**3번 형태**
사실 2번과 같다. (익명 객체로 전달)
```java
btn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        //todo
    }
});
```
listener 변수에 담지 않고 setOnClickListener 안에 바로 리스너를 등록하는 것이다.
익명 객체란 변수명을 지정하지 않고 바로 등록을 했기 때문에 이름이 없는 객체를 뜻한다.

***

```java
package com.example.jhjun.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 클릭리스너 구현하는 방법 세가지
 * 1. 위젯을 사용하는 객체가 상속받아서 구현
 * 2. 객체내에서 변수로 생성
 * 3. setOnclickListener 함수에 익명 객체로 전달
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.btnClick);

        // 1 번형태로 구현
        btn.setOnClickListener(this);

        // 2 번형태로 구현 - 아래에 구현된 리스너를 등록해준다.
        btn.setOnClickListener(listener);

        // 3 번형태로 구현
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("안녕 안드로이드!!!");
            }
        });
    }

    // 1번 형태
    @Override
    public void onClick(View v) {
        tv.setText("안녕 안드로이드!!!");
    }

    // 2번 형태
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tv.setText("안녕 안드로이드!!!");
        }
    };

}
```