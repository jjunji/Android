# RecyclerView

**RecyclerView(������Ŭ����)**

����Ʈ�� ǥ���ϴ� ����, RecyclerView�� ���� �����͸� ������ View�� �����ؼ� ǥ���Ѵ�. 

ListView�� ������ �ڵ带 RecyclerView�� �ٲ㺸��.

������Ŭ�� ��� �� Ȧ�� ������ �ǹ�ȭ�ϰ� �پ��� �߰� ����� ����Ʈ�� ������ ��. 
***
RecyclerView �� �� ������� mvp ����.
![MVP](�̹��� URL)

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
        // 1. ������
        datas = Loader.getData(this);
        // 2. �����
        CustomRecycler adapter = new CustomRecycler(datas, this);
        // 3. ����
        listView.setAdapter(adapter);
        // 4. ���̾ƿ� �Ŵ���
        listView.setLayoutManager(new LinearLayoutManager(this));
    }
}
```
CustomRecycler (�����)
```java
class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    ArrayList<Data> datas;
    Context context;

    public CustomRecycler(ArrayList<Data>datas , Context context){
        this.datas = datas;
        this.context = context;
    }

    // List View ���� convertView == null �϶� ó��
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                                                                                // null�� parent�� ����
        View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,null,false);
        //View view  = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_list,parent,false);
        //Holder holder = new Holder(View);
        return new Holder(view);
    }

    // �� ������ ���� ��Ÿ���� ȣ��Ǵ� �Լ�
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. �����͸� ������
        Data data = datas.get(position);
        // 2. �����͸� ����
        holder.setImage(data.resId);
        holder.setNo(data.no);
        holder.setTitle(data.title);
    }

    // �������� ��ü ����
    @Override
    public int getItemCount() {
        return datas.size();
    }
```
RecyclerView������ Context�� ���� inflate ���� �ʰ� View�� ���ؼ� inflate �۾��� �Ѵ�.
getItemCount( ) : ����Ʈ�� ǥ���� ������ ����.
onCreateViewHolder( ) : listView���� holder == null �� ���, Holder�� �������ִ� �κ�.
onBindViewHolder( ) : listView���� getView( ) �� ȣ�� �� �� ���� �������ִ� �κ�.

* Adapter�� onCreateViewHolder( ) �޼���� ViewHolder�� �ν��Ͻ��� �����ؼ� ��ȯ�Ѵ�.
* onBindViewHolder( ) �޼���� ViewHolder�� ������ View �����͸� �����Ѵ�.     ViewHolder�� ��� ������ View�� ������ �����ν�, findViewById( )�� �Ź� ������ �ʿ䰡  �������� ������ ����.

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
* �Ϲ������� Adapter ������ RecyclerView.ViewHolder �� ����ϴ� Ŭ������ ����� ���.
* ViewHolder �� View�� ���� ������ ����.
�����ڷ� �並 �Ѱ��ְ� super(itemView) �� �ߴٴ� ���� ViewHolder�� View�� ���� ������ �����Ѵٴ� ���� �ǹ��Ѵ�.

***
Loader & Data Ŭ������ ���� ����.