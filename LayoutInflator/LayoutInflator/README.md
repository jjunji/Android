# Layout Inflator

**1. 레이아웃은 어떻게 화면에 보여지는가**

  안드로이드는 화면 배치를 알려주는 XML 레이아웃 + 파일과 화면의 기능을 담당하는 소스 코드 파일로 분리.

  -> 하나의 화면을 만들 때는 XML 레이아웃 파일 하나와 자바 소스 파일 하나를 쌍으로 만들어야 한다고 생각.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼이 눌렸어요.", Toast.LENGTH_LONG).show();
            }
        });

        setContentView(R.layout.activity_main);
    }

}
```
1)  super.onCreate( ) 메소드 -> 부모 클래스의 동일한 메소드를 호출한다는 것.

화면의 기능을 담당하는 자바 소스 파일을 하나 만들면 그 안에는 AppCompatActivity를 상속하는 하나의 클래스 자동으로 만들어짐.

※ AppCompatActivity : 화면에 필요한 기능들이 들어 있음.

2)  상속한 AppCompatActivity의 setContentView( ) 메소드를 호출하면서 XML레이아웃 파일 이름을 파라미터로 전달하면 XML 레이아웃과 자바 소스코      드가 서로 연결된다. 

※ setContentView( ) 메소드의 역할 2가지

   1) 화면에 나타낼 뷰를 지정하는 역할.

   2) XML 레이아웃의 내용을 메모리에 객체화하는 역할.

***
**2. 인플레이션**

앱을 실행했을 때 화면이 보인다는 것 -> XML 레이아웃 안에 들어 있는 화면 배치 정보를 자바 소스 코드에서 사용한다는 것.

 따라서 XML 레이아웃 파일의 내용을 자바 소스 코드에서 사용하려면 앱이 실행될 때 XML 레이아웃 파일의 내용이 메모리로 로딩되어 객체화 되야함.

 이렇게 XML 레이아웃에 정의된 내용이 메모리에 로딩된 후 객체화 되는 과정이 인플레이션(Inflation) 

***

**2.1 전체 화면 중에서도 일부분만 차지하는 레이아웃 보여주기**

 setContentView( ) 메소드는 엑티비티의 화면 전체를 설정하는 역할을 하므로 부분 화면을 위한 XML 레이아웃을 메모리에 객체화하려면 별도의 인플레이션 객체가 필요.

 안드로이드에서는 이를 위해 LayoutInflater 클래스 제공.

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
                checkBox.setText("로딩되었어요.");
            }
        });
    }
}
```
1) 부분 화면으로 보여줄 sub.xml의 내용을 메모리에 객체화하려고 사용된 코드, getSystemService( ) 메소드를 사용해 LayoutInflater객체 참조.

2) 객체의 inflate 메소드를 호출, 파라미터로 R.layout.sub 와 container 전달.
	id가 container인 레이아웃 객체에 sub파일의 레이아웃을 설정하라는 의미.

3) 부분 화면으로 보여줄 파일이 메모리에 객체화되면 그 레이아웃 안에 들어있는 텍스트뷰나 체크박스 등을 findViewById( )로 참조 가능해짐.

***

※ LayoutInflater 객체의 inflate( ) 메소드는 다음과 같이 3가지로 정의됨.

1) View inflate(int resource, ViewGroup root)
위 소스와 같이 getSystemService( ) 메소드를 사용해 객체를 참조하는 방법.

```java
LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
inflater.inflate(R.layout.sub, container, true);
```

2) static LayoutInflater LayoutInflater.from(Context context)
	LayoutInflater 클래스에 정의된 from( ) 메소드를 사용하는 방법.

```java
LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
inflater.inflate(R.layout.sub, container, true);
```

3) LayoutInflater를 내부적으로 지원하는 View의 클래스 메소드를 이용하는 방법. 
	-> 한 줄로 처리 가능.

```java
v.inflate(MainActivity.this, R.layout.sub, container);
```