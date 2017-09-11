# Layout Inflator

**1. ���̾ƿ��� ��� ȭ�鿡 �������°�**

  �ȵ���̵�� ȭ�� ��ġ�� �˷��ִ� XML ���̾ƿ� + ���ϰ� ȭ���� ����� ����ϴ� �ҽ� �ڵ� ���Ϸ� �и�.

  -> �ϳ��� ȭ���� ���� ���� XML ���̾ƿ� ���� �ϳ��� �ڹ� �ҽ� ���� �ϳ��� ������ ������ �Ѵٰ� ����.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "��ư�� ���Ⱦ��.", Toast.LENGTH_LONG).show();
            }
        });

        setContentView(R.layout.activity_main);
    }

}
```
1)  super.onCreate( ) �޼ҵ� -> �θ� Ŭ������ ������ �޼ҵ带 ȣ���Ѵٴ� ��.

ȭ���� ����� ����ϴ� �ڹ� �ҽ� ������ �ϳ� ����� �� �ȿ��� AppCompatActivity�� ����ϴ� �ϳ��� Ŭ���� �ڵ����� �������.

�� AppCompatActivity : ȭ�鿡 �ʿ��� ��ɵ��� ��� ����.

2)  ����� AppCompatActivity�� setContentView( ) �޼ҵ带 ȣ���ϸ鼭 XML���̾ƿ� ���� �̸��� �Ķ���ͷ� �����ϸ� XML ���̾ƿ��� �ڹ� �ҽ���      �尡 ���� ����ȴ�. 

�� setContentView( ) �޼ҵ��� ���� 2����

   1) ȭ�鿡 ��Ÿ�� �並 �����ϴ� ����.

   2) XML ���̾ƿ��� ������ �޸𸮿� ��üȭ�ϴ� ����.

***
**2. ���÷��̼�**

���� �������� �� ȭ���� ���δٴ� �� -> XML ���̾ƿ� �ȿ� ��� �ִ� ȭ�� ��ġ ������ �ڹ� �ҽ� �ڵ忡�� ����Ѵٴ� ��.

 ���� XML ���̾ƿ� ������ ������ �ڹ� �ҽ� �ڵ忡�� ����Ϸ��� ���� ����� �� XML ���̾ƿ� ������ ������ �޸𸮷� �ε��Ǿ� ��üȭ �Ǿ���.

 �̷��� XML ���̾ƿ��� ���ǵ� ������ �޸𸮿� �ε��� �� ��üȭ �Ǵ� ������ ���÷��̼�(Inflation) 

***

**2.1 ��ü ȭ�� �߿����� �Ϻκи� �����ϴ� ���̾ƿ� �����ֱ�**

 setContentView( ) �޼ҵ�� ��Ƽ��Ƽ�� ȭ�� ��ü�� �����ϴ� ������ �ϹǷ� �κ� ȭ���� ���� XML ���̾ƿ��� �޸𸮿� ��üȭ�Ϸ��� ������ ���÷��̼� ��ü�� �ʿ�.

 �ȵ���̵忡���� �̸� ���� LayoutInflater Ŭ���� ����.

![]()

```java
public class MenuActivity extends AppCompatActivity {
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        container = (LinearLayout) findViewById(R.id.container);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.sub1, container, true);

                CheckBox checkBox = (CheckBox) container.findViewById(R.id.checkBox);
                checkBox.setText("�ε��Ǿ����.");
            }
        });
    }
}
```
1) �κ� ȭ������ ������ sub.xml�� ������ �޸𸮿� ��üȭ�Ϸ��� ���� �ڵ�, getSystemService( ) �޼ҵ带 ����� LayoutInflater��ü ����.

2) ��ü�� inflate �޼ҵ带 ȣ��, �Ķ���ͷ� R.layout.sub �� container ����.
	id�� container�� ���̾ƿ� ��ü�� sub������ ���̾ƿ��� �����϶�� �ǹ�.

3) �κ� ȭ������ ������ ������ �޸𸮿� ��üȭ�Ǹ� �� ���̾ƿ� �ȿ� ����ִ� �ؽ�Ʈ�䳪 üũ�ڽ� ���� findViewById( )�� ���� ��������.

***

�� LayoutInflater ��ü�� inflate( ) �޼ҵ�� ������ ���� 3������ ���ǵ�.

1) View inflate(int resource, ViewGroup root)
�� �ҽ��� ���� getSystemService( ) �޼ҵ带 ����� ��ü�� �����ϴ� ���.

```java
LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
inflater.inflate(R.layout.sub, container, true);
```

2) static LayoutInflater LayoutInflater.from(Context context)
	LayoutInflater Ŭ������ ���ǵ� from( ) �޼ҵ带 ����ϴ� ���.

```java
LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
inflater.inflate(R.layout.sub, container, true);
```

3) LayoutInflater�� ���������� �����ϴ� View�� Ŭ���� �޼ҵ带 �̿��ϴ� ���. 
	-> �� �ٷ� ó�� ����.

```java
v.inflate(MainActivity.this, R.layout.sub, container);
```