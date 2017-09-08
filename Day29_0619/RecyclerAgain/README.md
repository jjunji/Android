# RecyclerView

**1. ������� ����**
![](https://github.com/jjunji/Android/blob/master/Day13_0525/image/adapter.PNG)
<br>
Adapter�� ����

 - List�� �� �ϳ��ϳ��� �°� �����͸� �����Ѵ�.
 - �����Ͱ� ��°�� �ٲ�ٰ� �ص� (������ Title, Name, Number �� Car number, Address) ����Ͱ� �ѹ� �����ϰ� ���� �ѷ� �� ���� �����ϱ� ������ ������Ŭ����(Ȥ�� ����Ʈ��)�� �״�� ����� �� �ִ�.
 - ����Ͱ� ���ٸ� �����Ͱ� �ٲ� �� ���� ������Ŭ���並 Ŀ���͸���¡ �ؼ� ������� ��.


**Adapter�� ������ �� �ǹ������� �����ؾ��ϴ� �޼ҵ�**

 1) onCreateViewHolder( )
: ����Ʈ�� �� ���� ������ �並 ����.(inflation), ��ȯ ���� Holder�� �並 ���� ����.(�ڵ� ����)

 2) onBindViewHolder( )
: ViewHolder�� ������ View�ȿ� �ִ� �����鿡 �����͸� �����Ѵ�.

 3) getItemCount( )
: �������� ���� ��ȯ.

***

**2. ������Ŭ������ ����**

1) RecyclerView�� View�׷��� ����Ŭ������, �ڽ� View ��ü���� ����Ʈ�� �����ش�.

 2) ȭ���� ��ũ�ѵǸ鼭 View�� ȭ���� ��� �� �� View�� ������ �ʰ� ��Ȱ���Ѵ�.
***
**3. ������Ŭ���並 �����ϴ� ����.**
![](https://github.com/jjunji/Android/blob/master/Day13_0525/image/recyclerView.PNG)
***
Ŭ���� ���
	
 1. A_Data.class : ������ ����
 2. B_Loader.class : ������ �ε�
 3. C_Adapter.class : ����� ����
 4. D_Holder.class : ���� ���� �ϳ��ϳ��� ��Ʈ���ϱ� ���� Ȧ�� Ŭ����


***
������Ŭ�� �並 ����� �� �۾� ����

 1. ������ ���� �ľ� -> A_Data
 2. ������ �������� -> B_Loader
 3. �� ���� - ������Ŭ���並 ����� xml ���� & ����Ʈ�� �� ���� ����ϴ� xml ������ ����. -> ex) activity_main.xml & row_item.xml
 4. Adapter ���� -> C_Adapter
 5. Holder ���� -> D_Holder

***

A_Data.class
```java
public class A_Data {
    private int id;
    private String name;
    private String tel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
```
���� �����͸� �����Ѵ�.

��ȭ��ȣ���� �����͸� ������ ���̹Ƿ� �ּҷ����� ���� ���� ������ �����͸� �����Ѵ�.

id, name, tel�� ����� ���� �����߰�, ������ private���� �����Ͽ� getter & setter �� �����ߴ�.
***
B_Loader.class
```java
public class B_Loader {

    public static List<A_Data> getData(Context context){
        List<A_Data> datas = new ArrayList<>();
        // ����

        // ��ȭ��ȣ�θ� ������ ���� ���� contentResolver�� ���. -> context�� �ʿ���.
        // ��ȭ��ȣ �����͸� �������� Ŀ���� ����.
        ContentResolver resolver = context.getContentResolver();

        // �����Ͱ� �ִ� ���̺� �ּ� ��������
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // ���̺��� ������ �÷����� ����
        String proj[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                         ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                         ContactsContract.CommonDataKinds.Phone.NUMBER};

        // ����Ʈ �������� ������ ��������. ������ ���� -> Ŀ��
        Cursor cursor = resolver.query(phoneUri, proj, null, null, null);

        // cursor�� ������ ���翩��
        if(cursor != null ){
            while (cursor.moveToNext()){
                int index = cursor.getColumnIndex(proj[0]);
                int id = cursor.getInt(index);

                index = cursor.getColumnIndex(proj[1]);
                String name = cursor.getString(index);

                index = cursor.getColumnIndex(proj[2]);
                String tel = cursor.getString(index);

                A_Data data = new A_Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                datas.add(data);  // ���� ������ ������ ����ҿ� add
                // datas�� �� �ϳ��ϳ� ������ ������ Ŭ������ ����� -> �ּҷϸ�ŭ datas�� ������ ������.
            }
        }
        cursor.close(); // ���� ������ ��� ��������.
        //

        return datas;
    }
}
```
����, �������� ������ ���������� ���� �����͸� �����;��Ѵ�. 

�����͸� �������� ����.(Recycler���� ���� �����̹Ƿ� ���� ����)
***
C_Adapter.class
```java
// Ȧ���� �� �ϳ��ϳ��� �� ��Ʈ���� ���� ����� ��.
public class C_Adapter extends RecyclerView.Adapter<D_Holder>{

    List<A_Data> datas;

    public C_Adapter(List<A_Data> datas){
        this.datas = datas;
    }

    @Override
    public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);
    }

    @Override // ȭ�鿡 ���� �׷��� �� ���� ȣ���.
    public void onBindViewHolder(D_Holder holder, int position) {
        // ������ ������� ��Ȧ���� �¹��ε��Ȧ�� �Լ��� ���ؼ� �ý����� ������ �Ѱ��ָ� �̰����� �����͸� �����Ѵ�.
        A_Data data = datas.get(position); // ������ �������� ����Ʈ
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
```
����͸� �����ϴ� Ŭ������, ����ʹ� ������ ���� å���� ���´�.

1) �ʿ��� ViewHolder ��ü ����

2) �� ������ �����͸� ViewHolder�� ����.

�� RecyclerView�� �ڽ��� ViewHolder�� �������� �ʴ´�. ��ſ� �� ���� ����Ϳ� ��û�Ѵ�.
```java
public D_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new D_Holder(view);
}
```

�� ���� ��ȯ���� HolderŸ���� onCreateViewHolder�޼ҵ带 �����ߴµ�,
�� �޼ҵ�� ���� ��Ƽ��Ƽ����
```java
List<A_Data> datas = B_Loader.getData(this);
```
<A_Data>Ÿ���� ���� datas�� B_Loader Ŭ������ ���� ������ �����͸� ���
```java
C_Adapter adapter = new C_Adapter(datas);
```
���� ���� adapter��ü�� ������ �� �ڵ����� ����ȴ�.
view �������� ����Ʈ�� �� ���� �ش��ϴ� �䰡 ������ִ� ���̴�.
�並 inflate�� ���� �޸𸮿� ��üȭ �� ��
Holder�� �並 �ٿ� ��ȯ�Ѵ�. �� ��ȯ�� Ȧ���� Ȧ�� Ŭ������ �̵�.
Ȧ�� Ŭ������ �� ���� �ش��ϴ� �� ���� �������� ��Ʈ���ϱ� ���� ��������̴�.

�� getData(this) -> this�� context ������ ���ڷ� ���� ���� ��ȭ��ȣ���� �����͸� �������� ���� contentResolver�� ����ϱ� ����.
```java
 public void onBindViewHolder(D_Holder holder, int position) {
        // ������ ������� ��Ȧ���� �¹��ε��Ȧ�� �Լ��� ���ؼ� �ý����� ������ �Ѱ��ָ� �̰����� �����͸� �����Ѵ�.
        A_Data data = datas.get(position); // ������ �������� ����Ʈ
        holder.setName(data.getName());
        holder.setTel(data.getTel());
    }
```
Ȧ�� Ŭ������ ���� �� ���� �������� ��Ʈ�� �� �� �ְԵǸ� onBindViewHolder( ) �޼ҵ带 ���� ���� �����Ѵ�.

��, �޼ҵ� ���������
onCreateViewHolder( )    ->    Holder Ŭ���� ȣ��    ->    onBindViewHolder( )

�� onCreateViewHolder �޼��尡 onBindViewHolder ���� ���� ȣ�� �� �� �ִ�.

-> ����� ������ ViewHolder ��ü�� �����Ǹ� RecyclerView�� onCreateHolder�� ȣ���� �ߴ��ϱ� ����.
������ ������ ViewHolder ��ü�� �����Ͽ� �ð�&�޸� ����.
***
D_Holder.class
```java
// Ȧ�� : �� �ϳ��ϳ��� �ٷ��ִ� �� ��ü�� ���� ��.
public class D_Holder extends RecyclerView.ViewHolder{
    // ���� ����
    private TextView textTel;
    private TextView textName;

    public D_Holder(View itemView) {
        super(itemView);
        // �����ڰ� ������ �� xml�� �ִ� ������ �ҽ��ڵ�� ����.
        textTel = (TextView) itemView.findViewById(R.id.textTel);
        textName = (TextView) itemView.findViewById(R.id.textName);
    }

    public String getTel(){
        return textTel.getText().toString();
    }

    public void setTel(String value){
        textTel.setText(value);
    }

    public String getName(){
        return textName.getText().toString();
    }

    public void setName(String value){
        textName.setText(value);
    }
}
```
***
MainActivity.class
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. ��Ÿ�� ����üũ
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                Toast.makeText(getBaseContext(), " ������ ������ �մϴ�.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            String perms[] = {Manifest.permission.READ_CONTACTS};
            requestPermissions(perms, 100);
        }


    }

    private void init(){
        // 1. ������
        List<A_Data> datas = B_Loader.getData(this);
        // 2. �����
        C_Adapter adapter = new C_Adapter(datas);             // 1,2���� �����Ϳ� ����� ���� ����.
        // 3. ����Ʈ��� ����� ����                              // 3���� ����Ʈ��� ����� �������
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        // 4. ���̾ƿ��Ŵ���
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

```
recyclerView.setAdapter(adapter);
	: ������Ŭ���信 ����͸� ����. (��ġ)

recyclerView.setLayoutManager(new LinearLayoutManager(this));
	: ����Ʈ�� ������ ���¸� �����Ѵ�. -> ���Ͼ�̾ƿ� ���·� �����߱� ������ �Ϲ����� ����Ʈ ���·� ������.