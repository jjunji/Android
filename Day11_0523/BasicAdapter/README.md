# Spinner

**���ǳ�** : ���� ������ �߿��� �ϳ��� �����ϴ� �������� ����.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"�����ϼ���", "ListView", "CustomList", "GridView", "RecyclerView"};

    // ����ġ�� ���� ����ϱ� ����
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List�信 ������ �����ϱ�
        // 1. ������ ���� -> datas ������.
        // 2. ����� ����  // ����ʹ� �� ������?
        // 3. �信 ����� ����
        spinner = (Spinner) findViewById(R.id.spinner);
        // ���׸�                                            // this : context���� �ڿ��� ���ڴٰ� �ߴ� �Ͱ� ���� ��ġ
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // ����Ͱ� �����͸� �ѷ��ְ� ��Ʈ���ϴ� ����.
```
```java
1. ���ǳʿ� �� �����͸� ����

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> ���ǳ� ��ü�� findViewById �޼ҵ带 ���� ã�� �� spinner������ �Ҵ�.

	1) ���� Ŭ�������� ����ϱ� ���� spinner ����

	2) (Spinner) : resource�� �ִ� id�� spinner�� ������ ����� ���ε�,
	 �� ������ Spinner�̴�. ��� �˷��ֱ� ����. (ĳ����) , ������ ����.

	3) findViewById : xml�� ���Ե� ��ҵ� �߿��� id���� �˻��Ͽ�
	 ��ġ�Ǵ� ��Ҹ� ��ȯ. -> �䰡 �ƴ� ��� null ��ȯ.

	4) ���ǳ��� ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) �迭�� �� �����͸� �̿��ϴ� ArrayAdapter ��ü�� ����, < >�ȿ� String�� �� ���� Ÿ��. -> �ڹٿ��� ArrayList<>

2) new�� ���� ������ �Ҵ� ����.

3) this : ���⿡�� ����ϰڴ�!, Context��ü�� ���� ȣ��� ��Ƽ��Ƽ�� this�� ����.  // context���� �ڿ��� ���ڴ�.

4) android.R.layout.simple_list_item_1 : �ȵ���̵� API�� ��� �ִ� �̸� ���ǵ� ���̾ƿ�, ���ڿ��� ���������� �����ִ� �ܼ� ���ǳ� ���̾ƿ�.

5) datas : ���� ����ִ� ���ڿ� �����͵��� �迭.
```

***

```java
spinner.setAdapter(adapter);
        // ����͸� ���� ���� : �����Ͱ� ����ǵ�
        // ����� : �Ϻ��� mvp����
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // ����ġ�������� ���������� ���������� ���ľ���. -> ���������� ������ ������ ���̱� ����.

                switch (position){
                    case LIST:                    // this�� ���� �������� ���� : ���ο�Ƽ��Ƽ�� this��� ������� �ʾұ� ����..
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
                    default: // �����ϼ���
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
  :  ��� ����� ����.  -> ������ �Ǹ� ȭ�鿡 ǥ�ð� �ȴ�.
	 (��������� �ڵ��ص� ȭ�鿡 ��������� �Ѵ�.)
	 
5. Spinner.setOnItemSelectedListener
   : �������� �������� �� �߻��� �̺�Ʈ�� ���� ������ ���.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this ��� �� ���� ���� -> ������ ����� ����.(����ġ�� ������ ������, MainActivity��� ��������) -> MainActivity.this��� ����ؾߵ�.                  

2) �̵��� Ŭ���� ( 1 ������ 2 ������ ���ڴ� (������) )
```

**Spinner ���� �� ��**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
�� ���� �ʰ�  ����θ� onCreate ������ �ۿ� ���� ����� ������
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner��ü�� spinner ������ ��������ȭ �����ν�, onCreate������ �ܿ� �ٸ� ���������� ����� �� �ְ� �ϱ� �����̴�.

�� �ڵ� ������ �ѹ��� ���̱� ������ ù��°�� ���� �����ص� ����������, ���� ���̴� ���� Ȥ�� �ٸ� �������� �����ϴ� ������ ��� ��������ȭ ��Ű�� ���� �������� ����. ���� �ߺ��Ǵ� ������ �׼��� �Լ��� ��� ���� �����ϴ� ���� ����.
# Spinner

**���ǳ�** : ���� ������ �߿��� �ϳ��� �����ϴ� �������� ����.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"�����ϼ���", "ListView", "CustomList", "GridView", "RecyclerView"};

    // ����ġ�� ���� ����ϱ� ����
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List�信 ������ �����ϱ�
        // 1. ������ ���� -> datas ������.
        // 2. ����� ����  // ����ʹ� �� ������?
        // 3. �信 ����� ����
        spinner = (Spinner) findViewById(R.id.spinner);
        // ���׸�                                            // this : context���� �ڿ��� ���ڴٰ� �ߴ� �Ͱ� ���� ��ġ
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // ����Ͱ� �����͸� �ѷ��ְ� ��Ʈ���ϴ� ����.
```
```java
1. ���ǳʿ� �� �����͸� ����

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> ���ǳ� ��ü�� findViewById �޼ҵ带 ���� ã�� �� spinner������ �Ҵ�.

	1) ���� Ŭ�������� ����ϱ� ���� spinner ����

	2) (Spinner) : resource�� �ִ� id�� spinner�� ������ ����� ���ε�,
	 �� ������ Spinner�̴�. ��� �˷��ֱ� ����. (ĳ����) , ������ ����.

	3) findViewById : xml�� ���Ե� ��ҵ� �߿��� id���� �˻��Ͽ�
	 ��ġ�Ǵ� ��Ҹ� ��ȯ. -> �䰡 �ƴ� ��� null ��ȯ.

	4) ���ǳ��� ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) �迭�� �� �����͸� �̿��ϴ� ArrayAdapter ��ü�� ����, < >�ȿ� String�� �� ���� Ÿ��. -> �ڹٿ��� ArrayList<>

2) new�� ���� ������ �Ҵ� ����.

3) this : ���⿡�� ����ϰڴ�!, Context��ü�� ���� ȣ��� ��Ƽ��Ƽ�� this�� ����.  // context���� �ڿ��� ���ڴ�.

4) android.R.layout.simple_list_item_1 : �ȵ���̵� API�� ��� �ִ� �̸� ���ǵ� ���̾ƿ�, ���ڿ��� ���������� �����ִ� �ܼ� ���ǳ� ���̾ƿ�.

5) datas : ���� ����ִ� ���ڿ� �����͵��� �迭.
```

***

```java
spinner.setAdapter(adapter);
        // ����͸� ���� ���� : �����Ͱ� ����ǵ�
        // ����� : �Ϻ��� mvp����
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // ����ġ�������� ���������� ���������� ���ľ���. -> ���������� ������ ������ ���̱� ����.

                switch (position){
                    case LIST:                    // this�� ���� �������� ���� : ���ο�Ƽ��Ƽ�� this��� ������� �ʾұ� ����..
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
                    default: // �����ϼ���
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
  :  ��� ����� ����.  -> ������ �Ǹ� ȭ�鿡 ǥ�ð� �ȴ�.
	 (��������� �ڵ��ص� ȭ�鿡 ��������� �Ѵ�.)
	 
5. Spinner.setOnItemSelectedListener
   : �������� �������� �� �߻��� �̺�Ʈ�� ���� ������ ���.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this ��� �� ���� ���� -> ������ ����� ����.(����ġ�� ������ ������, MainActivity��� ��������) -> MainActivity.this��� ����ؾߵ�.                  

2) �̵��� Ŭ���� ( 1 ������ 2 ������ ���ڴ� (������) )
```

**Spinner ���� �� ��**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
�� ���� �ʰ�  ����θ� onCreate ������ �ۿ� ���� ����� ������
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner��ü�� spinner ������ ��������ȭ �����ν�, onCreate������ �ܿ� �ٸ� ���������� ����� �� �ְ� �ϱ� �����̴�.

�� �ڵ� ������ �ѹ��� ���̱� ������ ù��°�� ���� �����ص� ����������, ���� ���̴� ���� Ȥ�� �ٸ� �������� �����ϴ� ������ ��� ��������ȭ ��Ű�� ���� �������� ����. ���� �ߺ��Ǵ� ������ �׼��� �Լ��� ��� ���� �����ϴ� ���� ����.
# Spinner

**���ǳ�** : ���� ������ �߿��� �ϳ��� �����ϴ� �������� ����.

```java
public class MainActivity extends AppCompatActivity {

    //
    String datas[] = {"�����ϼ���", "ListView", "CustomList", "GridView", "RecyclerView"};

    // ����ġ�� ���� ����ϱ� ����
    final int LIST = 1;
    final int CUSTOM = 2;
    final int GRID = 3;
    final int RECYCLER = 4;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List�信 ������ �����ϱ�
        // 1. ������ ���� -> datas ������.
        // 2. ����� ����  // ����ʹ� �� ������?
        // 3. �信 ����� ����
        spinner = (Spinner) findViewById(R.id.spinner);
        // ���׸�                                            // this : context���� �ڿ��� ���ڴٰ� �ߴ� �Ͱ� ���� ��ġ
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datas);
        // ����Ͱ� �����͸� �ѷ��ְ� ��Ʈ���ϴ� ����.
```
```java
1. ���ǳʿ� �� �����͸� ����

            (1)         (2)        (3)           (4)
2. Spinner spinner = (Spinner) findViewById(R.id.spinner);  -> ���ǳ� ��ü�� findViewById �޼ҵ带 ���� ã�� �� spinner������ �Ҵ�.

	1) ���� Ŭ�������� ����ϱ� ���� spinner ����

	2) (Spinner) : resource�� �ִ� id�� spinner�� ������ ����� ���ε�,
	 �� ������ Spinner�̴�. ��� �˷��ֱ� ����. (ĳ����) , ������ ����.

	3) findViewById : xml�� ���Ե� ��ҵ� �߿��� id���� �˻��Ͽ�
	 ��ġ�Ǵ� ��Ҹ� ��ȯ. -> �䰡 �ƴ� ��� null ��ȯ.

	4) ���ǳ��� ID

3. ArrayAdapter<String> adapter = new Arrayadapter<String>(this, android.R.layout.simple_list_item_1, datas);

1) �迭�� �� �����͸� �̿��ϴ� ArrayAdapter ��ü�� ����, < >�ȿ� String�� �� ���� Ÿ��. -> �ڹٿ��� ArrayList<>

2) new�� ���� ������ �Ҵ� ����.

3) this : ���⿡�� ����ϰڴ�!, Context��ü�� ���� ȣ��� ��Ƽ��Ƽ�� this�� ����.  // context���� �ڿ��� ���ڴ�.

4) android.R.layout.simple_list_item_1 : �ȵ���̵� API�� ��� �ִ� �̸� ���ǵ� ���̾ƿ�, ���ڿ��� ���������� �����ִ� �ܼ� ���ǳ� ���̾ƿ�.

5) datas : ���� ����ִ� ���ڿ� �����͵��� �迭.
```

***

```java
spinner.setAdapter(adapter);
        // ����͸� ���� ���� : �����Ͱ� ����ǵ�
        // ����� : �Ϻ��� mvp����
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SpinnerValue", datas[position] + ", id : "+id);

                Intent intent; // ����ġ�������� ���������� ���������� ���ľ���. -> ���������� ������ ������ ���̱� ����.

                switch (position){
                    case LIST:                    // this�� ���� �������� ���� : ���ο�Ƽ��Ƽ�� this��� ������� �ʾұ� ����..
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
                    default: // �����ϼ���
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
  :  ��� ����� ����.  -> ������ �Ǹ� ȭ�鿡 ǥ�ð� �ȴ�.
	 (��������� �ڵ��ص� ȭ�鿡 ��������� �Ѵ�.)
	 
5. Spinner.setOnItemSelectedListener
   : �������� �������� �� �߻��� �̺�Ʈ�� ���� ������ ���.
   
6. Intent = new Intent(MainActivity.this, ListActivity.class);

1) this ��� �� ���� ���� -> ������ ����� ����.(����ġ�� ������ ������, MainActivity��� ��������) -> MainActivity.this��� ����ؾߵ�.                  

2) �̵��� Ŭ���� ( 1 ������ 2 ������ ���ڴ� (������) )
```

**Spinner ���� �� ��**

```java
Spinner spinner = (Spinner) findViewById(R.id.spinner);
```
�� ���� �ʰ�  ����θ� onCreate ������ �ۿ� ���� ����� ������
```java
spinner = (Spinner) findViewById(R.id.spinner);
```
Spinner��ü�� spinner ������ ��������ȭ �����ν�, onCreate������ �ܿ� �ٸ� ���������� ����� �� �ְ� �ϱ� �����̴�.

�� �ڵ� ������ �ѹ��� ���̱� ������ ù��°�� ���� �����ص� ����������, ���� ���̴� ���� Ȥ�� �ٸ� �������� �����ϴ� ������ ��� ��������ȭ ��Ű�� ���� �������� ����. ���� �ߺ��Ǵ� ������ �׼��� �Լ��� ��� ���� �����ϴ� ���� ����.
