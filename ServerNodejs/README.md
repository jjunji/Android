
# Android - Mysql db ������ �б�, ����

---
### MainActivity
�ʱ� ����
```java
public class MainActivity extends AppCompatActivity {

    private Button btnWrite;
    private RecyclerView recyclerView;
    private List<Bbs> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        data = new ArrayList<>();
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loader();
    }
```
* DB�� ���� �����͸� �о�� ��Ͽ� �ѷ��ֱ� ���� Recycler�並 �����ϰ�, ����� ����.
*  ���ø����̼� ����� initView( ) �� �並 �׸���, loader( ) �� ���� �����͸� �о�´�.

---
### initView( )
�ʱ� �� ������ �۾��� ��ư�� ������ �ޱ�.
```java
private void initView() {
        btnWrite = (Button) findViewById(R.id.btnWrite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnWrite.setOnClickListener(v->{
            /*
                ȣ��� startActivity �� ����ϸ� onResume ó���� ���� ����� �ȴ�.
             */
            Intent intent = new Intent(this, WriteActivity.class);
            startActivityForResult(intent, REQ_CODE);
        });
    }
```
* startActivity( ) �� ���� �׼��� ���Ѵٸ�, ��� ����� ���� �ް� ���� �� �����ֱ⿡��
   onResume( ) �� ���� ó�� ����� �Ѵ�.
* startActivityForResult( )
  * WriteActivity.class�� ȣ���Ѵ�.
  * ȣ�� �� requestcode �� RESULT_OK �̰�, ��� �ڵ尡 ȣ�� ������ ������ REQ_CODE�� ���, onActivityResult( ) �� ���� ��� ó�� (�ݹ�) 

---
### protected void onActivityResult( )
��� ó�� �޼ҵ�
```java
private final static int REQ_CODE = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQ_CODE:
                    this.data.clear(); // ������ �ִ� �����͸� �������ش�...
                    // �ߺ��Ǵ� �����Ͱ� �ִٸ� �������� �ʴ� ��������...
                    loader();
                    break;
            }
        }
    }
```
* WriteActivity.class �� ����� �� ȣ��Ǵ� �Լ���, ȣ�� ������ ������ resultCode �� RESULT_OK�� ���, requestCode�� �ش��ϴ� �׼��� �����Ѵ�.
*  this.data.clear( ) -> 
*  loader( ) �޼ҵ�� �����͸� ����

---
### loader( )
��Ʈ���� �̿� �����͸� �д� �Լ�
```java
    private void loader() {
        // 1. ��Ʈ���� ����
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. ���� ����
        IBbs myServer = client.create(IBbs.class);

        // 3. ������ Ư�� �Լ� ȣ�� -> Observable ����
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe ���
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                responseBody -> {
                    // 1. �����͸� ������
                    String jsonString = responseBody.string();
                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<Bbs>>(){}.getType(); // ������ �ϱ� ���� Ÿ������
//                    List<Bbs> data = gson.fromJson(jsonString, type);
                    Bbs data[] = gson.fromJson(jsonString, Bbs[].class);
                    // 2. �����͸� �ƴ��Ϳ� �����ϰ�
                    for(Bbs bbs : data){
                        this.data.add(bbs);
                    }
                    // 3. �ƴ��� ����
                    adapter.notifyDataSetChanged();
                }
            );
    }
```
---
### WriteActivity
�� ���� ��Ƽ��Ƽ
```java
public class WriteActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editAuthor;
    private EditText editContent;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();

        btnPost.setOnClickListener(
                v -> {
                    String title = editTitle.getText().toString();
                    String author = editAuthor.getText().toString();
                    String content = editContent.getText().toString();

                    postData(title, author, content);
                }
        );
    }

    private void postData(String title, String author, String content){
        // 0. �Է��� ��ü ����
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 1. ��Ʈ���� ����
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. ���� ����
        IBbs myServer = client.create(IBbs.class);

        // 3. ������ Ư�� �Լ� ȣ�� -> Observable ����
        Gson gson = new Gson();
        // bbs ��ü�� �������� �����ϱ� ���ؼ���
        // bbs ��ü -> json String ��ȯ
        // RequestBody �� �̵��Ÿ�԰�, String ���� ��ȯ�� �����͸� ��Ƽ� ����
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                gson.toJson(bbs)
        );

        Observable<ResponseBody> observable = myServer.write(body);

        // 4. subscribe ���
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            String result = responseBody.string(); // ����ڵ带 �Ѱܼ� ó��...
                    /*
                        1. ���� ���������� ������ �Է��� �� �����͸� �����ϰ� ����
                        2. ������ �������� �ʰ� ȭ���� �׳� ����
                        ���� �� ������ �����ϰ�
                        1�� �Ǵ� 2�� ������� MainActivity�� �Ѱܼ� ó��
                     */
                            setResult(Activity.RESULT_OK);  // ���� ����Ʈ�� ��� �ֱ⸸ �ϴ� ��, -> onActivityResult������ ���� ������ ����
                            finish();
                        }
                );
    }
    // ����Ʈ�� �ϰ� ���� � ����� ���ȿ��� ó���Ǳ� ������ onDestroy() �� ȣ������ �ʾƵ� �� �� ����.

    private void initView() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnPost = (Button) findViewById(R.id.btnPost);
    }
}
```
*  post ��ư Ŭ����, editText�� �Էµ� ���� String Ÿ������ ���� ��
   postdata( ) �޼ҵ��� ���ڷ� ����.
   * postdata(����1, ����2, ����3 ) �� �Է��� ��ü�� ���� -> bbs
   * RequestBody�� �̵��Ÿ�԰�, String���� ��ȯ�� �����͸� ��Ƽ� ����.
  
  ---
### interface iBbs
```java
public interface IBbs {
    public static final String SERVER = "http://192.168.151.15/";

    @GET("bbs")
    public Observable<ResponseBody> read();

    @POST("bbs")
    public Observable<ResponseBody> write(@Body RequestBody body);

    @PUT("bbs")
    public Observable<ResponseBody> update(Bbs bbs);

    @DELETE("bbs")
    public Observable<ResponseBody> delete(Bbs bbs);
}
```
---
### bbs.class
```java
// ������ ����
public class Bbs {
    int id;
    String title;
    String content;
    String author;
    String date;
}
```

