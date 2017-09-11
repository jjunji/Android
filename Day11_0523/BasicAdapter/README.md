# Spinner

**스피너** : 여러 아이템 중에서 하나를 선택하는 전형적인 위젯.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"선택하세요", "ListView", "CustomList", "GridView", "RecyclerView"};

    // 스위치문 쉽게 사용하기 위한
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List뷰에 데이터 연결하기
        // 1. 데이터 정의 -> datas 정의함.
        // 2. 어답터 생성  // 어답터는 곧 데이터?
        // 3. 뷰에 어답터 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        // 제네릭                                            // this : context에서 자원을 쓰겠다고 했던 것과 같은 이치
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // 어답터가 데이터를 뿌려주고 컨트롤하는 역할.
```
```java
1. 스피너에 들어갈 데이터를 정의

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> 스피너 객체를 findViewById 메소드를 통해 찾은 후 spinner변수에 할당.

	1) 현재 클래스에서 사용하기 위한 spinner 변수

	2) (Spinner) : resource에 있는 id가 spinner인 위젯을 사용할 것인데,
	 그 위젯은 Spinner이다. 라고 알려주기 위함. (캐스팅) , 일종의 연결.

	3) findViewById : xml에 포함된 요소들 중에서 id값을 검색하여
	 매치되는 요소를 반환. -> 뷰가 아닐 경우 null 반환.

	4) 스피너의 ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) 배열로 된 데이터를 이용하는 ArrayAdapter 객체를 생성, < >안에 String이 들어갈 값의 타입. -> 자바에서 ArrayList<>

2) new를 통해 공간을 할당 받음.

3) this : 여기에서 사용하겠다!, Context객체로 현재 호출된 엑티비티인 this를 전달.  // context에서 자원을 쓰겠다.

4) android.R.layout.simple_list_item_1 : 안드로이드 API에 들어 있는 미리 정의된 레이아웃, 문자열을 아이템으로 보여주는 단순 스피너 레이아웃.

5) datas : 값이 들어있는 문자열 데이터들의 배열.
```

***

```java
spinner.setAdapter(adapter);
        // 어답터를 쓰는 이유 : 데이터가 변경되도
        // 어답터 : 완벽한 mvp구조
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // 스위치문에서는 지역변수를 전역변수로 고쳐야함. -> 지역변수의 범위는 스코프 안이기 때문.

                switch (position){
                    case LIST:                    // this만 쓰면 에러나는 이유 : 메인엑티비티의 this라고 명시하지 않았기 때문..
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case GRID:
                        intent = new Intent(MainActivity.this, GridActivity.class);
                        startActivity(intent);
                        break;
                    case RECYCLER:
                        intent = new Intent(MainActivity.this, RecyclerActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM:
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
//                        startActivity(intent);
                        break;
                    default: // 선택하세요
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
```
```
4. Spinner.setAdapter(adapter);
  :  뷰와 어댑터 연결.  -> 연결이 되면 화면에 표시가 된다.
	 (여기까지만 코딩해도 화면에 보여지기는 한다.)
	 
5. Spinner.setOnItemSelectedListener
   : 아이템을 선택했을 때 발생할 이벤트에 대한 리스너 등록.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this 라고 만 쓰면 에러 -> 블럭안을 벗어나지 못함.(스위치문 밖으로 못나감, MainActivity라고 생각못함) -> MainActivity.this라고 명시해야됨.                  

2) 이동할 클래스 ( 1 번에서 2 번으로 가겠다 (목적지) )
```

**Spinner 선언 할 때**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
로 하지 않고  선언부를 onCreate 스코프 밖에 빼서 사용한 이유는
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner객체의 spinner 변수를 전역변수화 함으로써, onCreate스코프 외에 다른 영역에서도 사용할 수 있게 하기 위함이다.

이 코드 에서는 한번만 쓰이기 때문에 첫번째와 같이 선언해도 무관하지만, 자주 쓰이는 변수 혹은 다른 영역에서 참조하는 변수는 모두 전역변수화 시키는 것이 가독성이 좋다. 또한 중복되는 패턴의 액션은 함수로 묶어서 따로 관리하는 것이 좋다.
# Spinner

**스피너** : 여러 아이템 중에서 하나를 선택하는 전형적인 위젯.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"선택하세요", "ListView", "CustomList", "GridView", "RecyclerView"};

    // 스위치문 쉽게 사용하기 위한
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List뷰에 데이터 연결하기
        // 1. 데이터 정의 -> datas 정의함.
        // 2. 어답터 생성  // 어답터는 곧 데이터?
        // 3. 뷰에 어답터 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        // 제네릭                                            // this : context에서 자원을 쓰겠다고 했던 것과 같은 이치
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // 어답터가 데이터를 뿌려주고 컨트롤하는 역할.
```
```java
1. 스피너에 들어갈 데이터를 정의

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> 스피너 객체를 findViewById 메소드를 통해 찾은 후 spinner변수에 할당.

	1) 현재 클래스에서 사용하기 위한 spinner 변수

	2) (Spinner) : resource에 있는 id가 spinner인 위젯을 사용할 것인데,
	 그 위젯은 Spinner이다. 라고 알려주기 위함. (캐스팅) , 일종의 연결.

	3) findViewById : xml에 포함된 요소들 중에서 id값을 검색하여
	 매치되는 요소를 반환. -> 뷰가 아닐 경우 null 반환.

	4) 스피너의 ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) 배열로 된 데이터를 이용하는 ArrayAdapter 객체를 생성, < >안에 String이 들어갈 값의 타입. -> 자바에서 ArrayList<>

2) new를 통해 공간을 할당 받음.

3) this : 여기에서 사용하겠다!, Context객체로 현재 호출된 엑티비티인 this를 전달.  // context에서 자원을 쓰겠다.

4) android.R.layout.simple_list_item_1 : 안드로이드 API에 들어 있는 미리 정의된 레이아웃, 문자열을 아이템으로 보여주는 단순 스피너 레이아웃.

5) datas : 값이 들어있는 문자열 데이터들의 배열.
```

***

```java
spinner.setAdapter(adapter);
        // 어답터를 쓰는 이유 : 데이터가 변경되도
        // 어답터 : 완벽한 mvp구조
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // 스위치문에서는 지역변수를 전역변수로 고쳐야함. -> 지역변수의 범위는 스코프 안이기 때문.

                switch (position){
                    case LIST:                    // this만 쓰면 에러나는 이유 : 메인엑티비티의 this라고 명시하지 않았기 때문..
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case GRID:
                        intent = new Intent(MainActivity.this, GridActivity.class);
                        startActivity(intent);
                        break;
                    case RECYCLER:
                        intent = new Intent(MainActivity.this, RecyclerActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM:
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
//                        startActivity(intent);
                        break;
                    default: // 선택하세요
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
```
```
4. Spinner.setAdapter(adapter);
  :  뷰와 어댑터 연결.  -> 연결이 되면 화면에 표시가 된다.
	 (여기까지만 코딩해도 화면에 보여지기는 한다.)
	 
5. Spinner.setOnItemSelectedListener
   : 아이템을 선택했을 때 발생할 이벤트에 대한 리스너 등록.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this 라고 만 쓰면 에러 -> 블럭안을 벗어나지 못함.(스위치문 밖으로 못나감, MainActivity라고 생각못함) -> MainActivity.this라고 명시해야됨.                  

2) 이동할 클래스 ( 1 번에서 2 번으로 가겠다 (목적지) )
```

**Spinner 선언 할 때**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
로 하지 않고  선언부를 onCreate 스코프 밖에 빼서 사용한 이유는
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner객체의 spinner 변수를 전역변수화 함으로써, onCreate스코프 외에 다른 영역에서도 사용할 수 있게 하기 위함이다.

이 코드 에서는 한번만 쓰이기 때문에 첫번째와 같이 선언해도 무관하지만, 자주 쓰이는 변수 혹은 다른 영역에서 참조하는 변수는 모두 전역변수화 시키는 것이 가독성이 좋다. 또한 중복되는 패턴의 액션은 함수로 묶어서 따로 관리하는 것이 좋다.
# Spinner

**스피너** : 여러 아이템 중에서 하나를 선택하는 전형적인 위젯.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"선택하세요", "ListView", "CustomList", "GridView", "RecyclerView"};

    // 스위치문 쉽게 사용하기 위한
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List뷰에 데이터 연결하기
        // 1. 데이터 정의 -> datas 정의함.
        // 2. 어답터 생성  // 어답터는 곧 데이터?
        // 3. 뷰에 어답터 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        // 제네릭                                            // this : context에서 자원을 쓰겠다고 했던 것과 같은 이치
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // 어답터가 데이터를 뿌려주고 컨트롤하는 역할.
```
```java
1. 스피너에 들어갈 데이터를 정의

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> 스피너 객체를 findViewById 메소드를 통해 찾은 후 spinner변수에 할당.

	1) 현재 클래스에서 사용하기 위한 spinner 변수

	2) (Spinner) : resource에 있는 id가 spinner인 위젯을 사용할 것인데,
	 그 위젯은 Spinner이다. 라고 알려주기 위함. (캐스팅) , 일종의 연결.

	3) findViewById : xml에 포함된 요소들 중에서 id값을 검색하여
	 매치되는 요소를 반환. -> 뷰가 아닐 경우 null 반환.

	4) 스피너의 ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) 배열로 된 데이터를 이용하는 ArrayAdapter 객체를 생성, < >안에 String이 들어갈 값의 타입. -> 자바에서 ArrayList<>

2) new를 통해 공간을 할당 받음.

3) this : 여기에서 사용하겠다!, Context객체로 현재 호출된 엑티비티인 this를 전달.  // context에서 자원을 쓰겠다.

4) android.R.layout.simple_list_item_1 : 안드로이드 API에 들어 있는 미리 정의된 레이아웃, 문자열을 아이템으로 보여주는 단순 스피너 레이아웃.

5) datas : 값이 들어있는 문자열 데이터들의 배열.
```

***

```java
spinner.setAdapter(adapter);
        // 어답터를 쓰는 이유 : 데이터가 변경되도
        // 어답터 : 완벽한 mvp구조
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // 스위치문에서는 지역변수를 전역변수로 고쳐야함. -> 지역변수의 범위는 스코프 안이기 때문.

                switch (position){
                    case LIST:                    // this만 쓰면 에러나는 이유 : 메인엑티비티의 this라고 명시하지 않았기 때문..
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case GRID:
                        intent = new Intent(MainActivity.this, GridActivity.class);
                        startActivity(intent);
                        break;
                    case RECYCLER:
                        intent = new Intent(MainActivity.this, RecyclerActivity.class);
                        startActivity(intent);
                        break;
                    case CUSTOM:
                        intent = new Intent(MainActivity.this, CustomListActivity.class);
//                        startActivity(intent);
                        break;
                    default: // 선택하세요
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
```
```
4. Spinner.setAdapter(adapter);
  :  뷰와 어댑터 연결.  -> 연결이 되면 화면에 표시가 된다.
	 (여기까지만 코딩해도 화면에 보여지기는 한다.)
	 
5. Spinner.setOnItemSelectedListener
   : 아이템을 선택했을 때 발생할 이벤트에 대한 리스너 등록.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this 라고 만 쓰면 에러 -> 블럭안을 벗어나지 못함.(스위치문 밖으로 못나감, MainActivity라고 생각못함) -> MainActivity.this라고 명시해야됨.                  

2) 이동할 클래스 ( 1 번에서 2 번으로 가겠다 (목적지) )
```

**Spinner 선언 할 때**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
로 하지 않고  선언부를 onCreate 스코프 밖에 빼서 사용한 이유는
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner객체의 spinner 변수를 전역변수화 함으로써, onCreate스코프 외에 다른 영역에서도 사용할 수 있게 하기 위함이다.

이 코드 에서는 한번만 쓰이기 때문에 첫번째와 같이 선언해도 무관하지만, 자주 쓰이는 변수 혹은 다른 영역에서 참조하는 변수는 모두 전역변수화 시키는 것이 가독성이 좋다. 또한 중복되는 패턴의 액션은 함수로 묶어서 따로 관리하는 것이 좋다.
