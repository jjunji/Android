# RecyclerView

**RecyclerView(리사이클러뷰)**

리스트를 표시하는 위젯, RecyclerView는 많은 데이터를 한정된 View를 재사용해서 표시한다. 

ListView로 구현한 코드를 RecyclerView로 바꿔보기.

리사이클러 뷰는 뷰 홀더 패턴을 의무화하고 다양한 추가 기능을 리스트에 구현한 것. 
***
RecyclerView 는 잘 만들어진 mvp 패턴.
![MVP](이미지 URL)

data : Model
holder : View
adapter : Presenter
***
MainActivity
```java
public class MainActivity extends AppCompatActivity {

    RecyclerView listView;
    ArrayList<Data> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.listView);
        // 1. 데이터
        datas = Loader.getData(this);
        // 2. 어답터
        CustomRecycler adapter = new CustomRecycler(datas, this);
        // 3. 연결
        listView.setAdapter(adapter);
        // 4. 레이아웃 매니저
        listView.setLayoutManager(new LinearLayoutManager(this));
    }
}
```
CustomRecycler (어댑터)
```java
class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    ArrayList<Data> datas;
    Context context;

    public CustomRecycler(ArrayList<Data>datas , Context context){
        this.datas = datas;
        this.context = context;
    }

    // List View 에서 convertView == null 일때 처리
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                                                                                // null과 parent의 차이
        View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,null,false);
        //View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,parent,false);
        //Holder holder = new Holder(View);
        return new Holder(view);
    }

    // 각 데이터 셀이 나타날때 호출되는 함수
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 꺼내고
        Data data = datas.get(position);
        // 2. 데이터를 세팅
        holder.setImage(data.resId);
        holder.setNo(data.no);
        holder.setTitle(data.title);
    }

    // 데이터의 전체 개수
    @Override
    public int getItemCount() {
        return datas.size();
    }
```
RecyclerView에서는 Context를 통해 inflate 하지 않고 View를 통해서 inflate 작업을 한다.
getItemCount( ) : 리스트에 표현될 아이템 개수.
onCreateViewHolder( ) : listView에서 holder == null 인 경우, Holder를 생성해주던 부분.
onBindViewHolder( ) : listView에서 getView( ) 가 호출 될 때 값을 세팅해주던 부분.

* Adapter의 onCreateViewHolder( ) 메서드로 ViewHolder의 인스턴스를 생성해서 반환한다.
* onBindViewHolder( ) 메서드로 ViewHolder에 설정한 View 데이터를 설정한다.     ViewHolder의 멤버 변수에 View를 저장해 둠으로써, findViewById( )를 매번 실행할 필요가  없어지고 성능이 향상됨.

***
Holder.class
```java
class Holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView no;
        TextView title;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            no = (TextView) itemView. findViewById(R.id.txtNo);
            title = (TextView) itemView. findViewById(R.id.txtTitle);
        }

        public void setImage(int resId){
            image.setImageResource(resId);
        }

        public void setNo(int no){
            this.no.setText(no+"");
        }

        public void setTitle(String title){
            this.title.setText(title);
        }
    }
}
```
* 일반적으로 Adapter 내에서 RecyclerView.ViewHolder 를 상속하는 클래스를 만들어 사용.
* ViewHolder 는 View에 대한 참조를 유지.
생성자로 뷰를 넘겨주고 super(itemView) 를 했다는 것은 ViewHolder는 View에 대한 참조를 유지한다는 것을 의미한다.

***
Loader & Data 클래스는 전과 동일.