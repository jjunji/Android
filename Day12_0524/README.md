# ListView

�Ϲ������� ����Ʈ ���·� �� ȭ�� ��Ʈ��, ���� ���� ������ �߿� �����ϴ� ����� ���� �� ���� ���.

![](https://github.com/jjunji/Android/blob/master/Day12_0524/image/adapter.PNG)
�� �׸��� ����Ʈ ��� ���� ���� ������ ���Ǵ� ����̴�.

���� �����͸� ������ ���� �������� �ʰ�, ����Ͷ�� Ŭ������ ����ϵ��� �Ǿ� �ִ�.

***

1. ���� ������ Ư¡
	* ���� ������ �����(Adapter)�� ���� ������ �������� ȭ�鿡 ���÷�����.
	* ���� �����ʹ� ����Ϳ� �����ؾ� �ϸ�, ����Ͱ� ������ ���� ����� ����Ѵ�.
	* ������ �������� ȭ�鿡 ���÷��̵Ǳ� ���� ������� getView( ) �޼ҵ尡 ȣ��. 
	* getView( ) �޼ҵ忡�� �����ϴ� �䰡 �ϳ��� ���������� ���÷��̵�. 
ex) getView( ) ���� �����ϴ� ��ü�� �ؽ�Ʈ�� ��ü��� ���� ������ �� �������� �ؽ�Ʈ ��� ǥ��.
	* ���� �����ϴ� ��ü�� �ϳ��� �䰡 �ƴ϶� ���Ͼ� ���̾ƿ�ó�� ���� ���� ����� ��� �ִ� �����̳� ��ü��� �ϳ��� �����ۿ� ���� ������ ���� �� �� �ִ�.

2. ����Ʈ �並 ����� �� �ؾ� �� ��<br><br>
		1)  �������� ���� XML ���̾ƿ� �����ϱ�
		: ����Ʈ�信 �� �� �������� ���̾ƿ��� XML�� ����.<br><br>
	    2)  �������� ���� �� ����
	: ����Ʈ�信 �� �� �������� �ϳ��� ��� ����.
	 -> ���� ���� �並 ��� �ִ� �� �׷��̾�� �Ѵ�.<br><br>
	    3)  ����� �����ϱ�
	: ������ ���� ������ �ϴ� ����� Ŭ������ �����, �� �ȿ� �� ���������� ǥ���� �並 ��ȯ�ϴ� getView( ) �޼ҵ� ����<br><br>
	    4)  ����Ʈ�� �ٷ��
		: ȭ�鿡 ����Ʈ�並 �߰��ϰ� �������� ���õǾ��� �� ȣ��� ������ ��ü�� ����. 
		
***
CustomAdapter.class
```java
class CustomAdapter extends BaseAdapter {
    // 1. ������
    ArrayList<Data> datas;
    // 2. ���÷�����
    LayoutInflater inflater;
    // 3. ���ؽ�Ʈ
    Context context;

    public CustomAdapter(ArrayList<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. �����ͺ並 üũ�ؼ� null �ϰ�츸 item layout �� �������ش�
        Holder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new Holder(convertView,context);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        // 2. �� �����ۿ� �ش��ϴ� �����͸� �����´�
        Data data = datas.get(position);
        // 3. �信 �����͸� �����Ѵ�.
        holder.setPosition(position);
        holder.setNo(data.no);
        holder.setTitle(data.title);
        holder.setImage(data.resId);

        return convertView;
    }
```

CustomAdapter Ŭ������ BaseAdapter Ŭ������ ����Ѵ�.
BaseAdapter���� �⺻������ �츮�� ����ϴ� ��ɵ��� ���� �Ǿ� �ִ�.
�ش� �������̽��� ���ǵ� �͵��� �ʼ������� �����ؾ� �Ѵ�.

* getCount( ) : ����Ϳ��� �����ϴ� �������� ������ ����.
* getView( ) : �� �����ۿ� ���� �並 ��ȯ.
*  getView(int position, View convertView, ViewGroup parent)
	* int position : ����Ʈ�信�� ���� �������� ��ġ ����.
	*  View convertView : ���� �ε����� �ش��ϴ� �� ��ü.
	* ViewGroup Parent : �� �並 �����ϰ� �ִ� �θ� �����̳� ��ü.

***
getView( ) �޼ҵ� ������ �Ʒ��� ���� ������ ����
```java
if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
```

![](https://github.com/jjunji/Android/blob/master/Day12_0524/image/listview_recycler.jpg)

����Ʈ ���� ��Ȱ�� �帧���̴�.
�� �������� ȭ�� ������ �����ԵǸ� �ش� ��� ��Ȱ�� ������ �������� ���� �ȴ�.
�׸��� ���ο� �䰡 ��Ÿ���� �� �� getView( ) �޼ҵ尡 ȣ��ȴ�.
ó�� ����Ʈ �䰡 ���� ���� ��Ȱ�� ������ �� �䰡 ���� ������ convertView �� null ������ ��ũ���� �����ٴ��� ȭ�� ������ ����Ʈ�� �� ������ �䰡 ������ �ȴٸ� ��Ȱ�� ������ convertView �� ���� ���̴�.
��, ��Ȱ�� ������ convertView�� �������� ���� ��쿡�� inflate �ؼ� view�� �����Ѵٸ� �ý��� �ڿ��� ������ �� �ִ�.

���⼭, Holder �� ����ϸ� �ڿ��� ���� ���� ������ �� �ִµ�, convertView�� null�� �ƴϾ �並 �����Ѵٰ� ���� ���� findViewById	�� ���� �並 �����ϴ� ������ �ʿ��ϴ�. �� ������ �����ϱ� ���� Holder ��ü�� �����ϴ� ������ ����� ������ �ذ� �� �� �ִ�.

***
Holder.class
```java
class Holder {
        int position;
        TextView no;
        TextView title;
        ImageView image;
        int resId;

        public Holder(View view, final Context context) {
            no = (TextView) view.findViewById(R.id.txtNo);
            title = (TextView) view.findViewById(R.id.txtTitle);
            image = (ImageView) view.findViewById(R.id.detail);
            // 1. �̹����信 onclick listener �� �ް� ���������� �̵���Ų��.
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    intent.putExtra(DATA_KEY, Holder.this.position);
                    intent.putExtra(DATA_RES_ID, resId);
                    intent.putExtra(DATA_TITLE, title.getText().toString());

                    context.startActivity(intent);
                }
            });
            // 2. Ÿ��Ʋ �ؽ�Ʈ�信 onclick listener �� �ް� Toast �� ������ ����Ѵ�.
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, title.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
```

<br>
intent( ) �� ���� ���� ��򰡷� �ѱ� �� �ʿ��� Ű ���� ����� ���س��� ����ϸ� ���ϴ�.

```java
    public static final String DATA_KEY = "position";
    public static final String DATA_RES_ID = "resid";
    public static final String DATA_TITLE = "title";
```
***
����Ʈ �信 �̹��� �ֱ�
```java
int id = context.getResources().getIdentifier(holder.image, "mipmap", context.getPackageName));

holder.image.setImageResource(id);
```
������ holder�� �̹����� �ִ� ������ �������� �� �������� ����Ʈ���� �� ������ �䰡 ���ŵ� ������ ���� ���� �۾��� ��� �߻��Ѵٴ� ��.

**�ذ�å**

�̹��� id�� �������� ������ Loader Ŭ�������� ����.
```java
holder.setImage(data.resId);
```
```java
class Loader {
    public static ArrayList<Data> getData(Context context){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=1 ; i<=10 ; i++){
            Data data = new Data();
            data.no = i;
            data.title = "��ī";

            data.setImage("p"+i, context);

            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
    public int resId;

    public void setImage(String str, Context context){
        image = str;
        // ���ڿ��� ���ҽ� ���̵� ��������
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
    }
}
```
