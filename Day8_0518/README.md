
## Ŭ�� ������ ��� 3���� ���

 1. ������ ����ϴ� ��ü�� ��ӹ޾Ƽ� ����

 2. ��ü������ ������ ����

 3. setOnclickListener �Լ��� �͸� ��ü�� ����

***

����
```java
Button btn = (Button) findViewById(R.id.btnClick);
```
��ư�� ����ϱ� ���� Button ��ü�� findViewById �޼ҵ带 ���� xml���� id���� ã�� �� btn ������ �Ҵ�. (����)

**1�� ����**
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener
```
View.OnclickListener �������̽��� �����Ͽ� ����Ѵ�.
�������̽��� �����ϱ� ���ؼ��� �޼ҵ带 �����ؾ��Ѵ�.

```java
@Override
public void onClick(View v) {
    
}
```
�� ���� 
```java
btn.setOnClickListener(this);
```
btn������ Ŭ�� �����ʸ� �޾��ְ� setOnClickListener(this)����  this <- �� onClick(View v) �� �޼ҵ带 ���� View ��ü�� �޾ƿ��� ���̴�. ��, public void onClick(View v)�� ��ȯ ���� View Ÿ��. v�� �ܼ��� ������ �ǹ�(�̸��� �ٲ㵵 �������)
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
�̿� ���� ��ư�� ������ �� ��, ��(v)�� id ���� ������ �����ʿ� �Ѱ��ְ� �׿� �ش��ϴ� �ൿ�� ������� �� ��쿡 ���δ�.

***

**2�� ����**
```java
btn.setOnClickListener(listener);
```
�� ���� ���� �� listener�� ���� �κ��� ���� �����Ѵ�.
```java
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
        }
    };
```
listener ������ Ŭ���� �Ͼ�� �� �׼��� ��´�.
���� listener�� ��ư�� setOnClickListener�� �����ϴ� ���̴�.

***
**3�� ����**
��� 2���� ����. (�͸� ��ü�� ����)
```java
btn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        //todo
    }
});
```
listener ������ ���� �ʰ� setOnClickListener �ȿ� �ٷ� �����ʸ� ����ϴ� ���̴�.
�͸� ��ü�� �������� �������� �ʰ� �ٷ� ����� �߱� ������ �̸��� ���� ��ü�� ���Ѵ�.

***

```java
package com.example.jhjun.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Ŭ�������� �����ϴ� ��� ������
 * 1. ������ ����ϴ� ��ü�� ��ӹ޾Ƽ� ����
 * 2. ��ü������ ������ ����
 * 3. setOnclickListener �Լ��� �͸� ��ü�� ����
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

        // 1 �����·� ����
        btn.setOnClickListener(this);

        // 2 �����·� ���� - �Ʒ��� ������ �����ʸ� ������ش�.
        btn.setOnClickListener(listener);

        // 3 �����·� ����
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("�ȳ� �ȵ���̵�!!!");
            }
        });
    }

    // 1�� ����
    @Override
    public void onClick(View v) {
        tv.setText("�ȳ� �ȵ���̵�!!!");
    }

    // 2�� ����
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tv.setText("�ȳ� �ȵ���̵�!!!");
        }
    };

}
```