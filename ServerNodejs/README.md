
# Android - Mysql db 데이터 읽기, 쓰기

---
### MainActivity
초기 설정
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
* DB로 부터 데이터를 읽어와 목록에 뿌려주기 위해 Recycler뷰를 선언하고, 어댑터 생성.
*  어플리케이션 실행시 initView( ) 로 뷰를 그리고, loader( ) 를 통해 데이터를 읽어온다.

---
### initView( )
초기 뷰 설정과 글쓰기 버튼에 리스너 달기.
```java
private void initView() {
        btnWrite = (Button) findViewById(R.id.btnWrite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnWrite.setOnClickListener(v->{
            /*
                호출시 startActivity 를 사용하면 onResume 처리를 따로 해줘야 된다.
             */
            Intent intent = new Intent(this, WriteActivity.class);
            startActivityForResult(intent, REQ_CODE);
        });
    }
```
* startActivity( ) 를 통해 액션을 취한다면, 어떠한 결과를 돌려 받고 싶을 때 생명주기에서
   onResume( ) 을 따로 처리 해줘야 한다.
* startActivityForResult( )
  * WriteActivity.class를 호출한다.
  * 호출 후 requestcode 가 RESULT_OK 이고, 결과 코드가 호출 측에서 정의한 REQ_CODE일 경우, onActivityResult( ) 를 통해 결과 처리 (콜백) 

---
### protected void onActivityResult( )
결과 처리 메소드
```java
private final static int REQ_CODE = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQ_CODE:
                    this.data.clear(); // 기존에 있던 데이터를 삭제해준다...
                    // 중복되는 데이터가 있다면 갱신하지 않는 방향으로...
                    loader();
                    break;
            }
        }
    }
```
* WriteActivity.class 가 종료된 후 호출되는 함수로, 호출 측에서 응답한 resultCode 가 RESULT_OK인 경우, requestCode에 해당하는 액션을 수행한다.
*  this.data.clear( ) -> 
*  loader( ) 메소드로 데이터를 읽음

---
### loader( )
레트로핏 이용 데이터를 읽는 함수
```java
    private void loader() {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                responseBody -> {
                    // 1. 데이터를 꺼내고
                    String jsonString = responseBody.string();
                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<Bbs>>(){}.getType(); // 컨버팅 하기 위한 타입지정
//                    List<Bbs> data = gson.fromJson(jsonString, type);
                    Bbs data[] = gson.fromJson(jsonString, Bbs[].class);
                    // 2. 데이터를 아답터에 세팅하고
                    for(Bbs bbs : data){
                        this.data.add(bbs);
                    }
                    // 3. 아답터 갱신
                    adapter.notifyDataSetChanged();
                }
            );
    }
```
---
### WriteActivity
글 쓰기 액티비티
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
        // 0. 입력할 객체 생성
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Gson gson = new Gson();
        // bbs 객체를 수동으로 전송하기 위해서는
        // bbs 객체 -> json String 변환
        // RequestBody 에 미디어타입과, String 으로 벼환된 데이터를 담아서 전송
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                gson.toJson(bbs)
        );

        Observable<ResponseBody> observable = myServer.write(body);

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            String result = responseBody.string(); // 결과코드를 넘겨서 처리...
                    /*
                        1. 내가 정상적으로 새글을 입력한 후 데이터를 전송하고 종료
                        2. 새글을 전송하지 않고 화면을 그냥 종료
                        위의 두 가지를 구분하고
                        1번 또는 2번 결과값을 MainActivity로 넘겨서 처리
                     */
                            setResult(Activity.RESULT_OK);  // 값을 인텐트에 담아 주기만 하는 것, -> onActivityResult에서는 값을 꺼내서 실행
                            finish();
                        }
                );
    }
    // 포스트를 하고 나서 어떤 결과가 블럭안에서 처리되기 때문에 onDestroy() 를 호출하지 않아도 될 것 같다.

    private void initView() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnPost = (Button) findViewById(R.id.btnPost);
    }
}
```
*  post 버튼 클릭시, editText에 입력된 값을 String 타입으로 받은 후
   postdata( ) 메소드의 인자로 보냄.
   * postdata(인자1, 인자2, 인자3 ) 는 입력할 객체를 생성 -> bbs
   * RequestBody에 미디어타입과, String으로 변환된 데이터를 담아서 전송.
  
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
// 데이터 정의
public class Bbs {
    int id;
    String title;
    String content;
    String author;
    String date;
}
```

