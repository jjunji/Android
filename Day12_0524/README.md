# ListView

일반적으로 리스트 형태로 된 화면 컨트롤, 여러 개의 아이템 중에 선택하는 기능을 넣을 때 자주 사용.

![](https://github.com/jjunji/Android/blob/master/Day12_0524/image/adapter.PNG)
위 그림은 리스트 뷰와 같은 선택 위젯이 사용되는 방식이다.

원본 데이터를 위젯에 직접 설정하지 않고, 어댑터라는 클래스를 사용하도록 되어 있다.

***

1. 선택 위젯의 특징
	* 선택 위젯은 어댑터(Adapter)를 통해 각각의 아이템을 화면에 디스플레이함.
	* 원본 데이터는 어댑터에 설정해야 하며, 어댑터가 데이터 관리 기능을 담당한다.
	* 각각의 아이템이 화면에 디스플레이되기 전에 어댑터의 getView( ) 메소드가 호출. 
	* getView( ) 메소드에서 리턴하는 뷰가 하나의 아이템으로 디스플레이됨. 
ex) getView( ) 에서 리턴하는 객체가 텍스트뷰 객체라면 선택 위젯의 각 아이템은 텍스트 뷰로 표시.
	* 만약 리턴하는 객체가 하나의 뷰가 아니라 리니어 레이아웃처럼 여러 개의 뷰들을 담고 있는 컨테이너 객체라면 하나의 아이템에 여러 정보를 보여 줄 수 있다.

2. 리스트 뷰를 사용할 때 해야 할 일<br><br>
		1)  아이템을 위한 XML 레이아웃 정의하기
		: 리스트뷰에 들어갈 각 아이템의 레이아웃을 XML로 정의.<br><br>
	    2)  아이템을 위한 뷰 정의
	: 리스트뷰에 들어갈 각 아이템을 하나의 뷰로 정의.
	 -> 여러 개의 뷰를 담고 있는 뷰 그룹이어야 한다.<br><br>
	    3)  어댑터 정의하기
	: 데이터 관리 역할을 하는 어댑터 클래스를 만들고, 그 안에 각 아이템으로 표시할 뷰를 반환하는 getView( ) 메소드 정의<br><br>
	    4)  리스트뷰 다루기
		: 화면에 리스트뷰를 추가하고 아이템이 선택되었을 때 호출될 리스너 객체를 정의. 
		
***
CustomAdapter.class
```java
class CustomAdapter extends BaseAdapter {
    // 1. 데이터
    ArrayList<Data> datas;
    // 2. 인플레이터
    LayoutInflater inflater;
    // 3. 컨텍스트
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
        // 1. 컨버터뷰를 체크해서 null 일경우만 item layout 을 생성해준다
        Holder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new Holder(convertView,context);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        // 2. 내 아이템에 해당하는 데이터를 가져온다
        Data data = datas.get(position);
        // 3. 뷰에 데이터를 세팅한다.
        holder.setPosition(position);
        holder.setNo(data.no);
        holder.setTitle(data.title);
        holder.setImage(data.resId);

        return convertView;
    }
```

CustomAdapter 클래스는 BaseAdapter 클래스를 상속한다.
BaseAdapter에는 기본적으로 우리가 사용하는 기능들이 정의 되어 있다.
해당 인터페이스에 정의된 것들을 필수적으로 구현해야 한다.

* getCount( ) : 어댑터에서 관리하는 아이템의 개수를 리턴.
* getView( ) : 각 아이템에 보일 뷰를 반환.
*  getView(int position, View convertView, ViewGroup parent)
	* int position : 리스트뷰에서 보일 아이템의 위치 정보.
	*  View convertView : 현재 인덱스에 해당하는 뷰 객체.
	* ViewGroup Parent : 이 뷰를 포함하고 있는 부모 컨테이너 객체.

***
getView( ) 메소드 내에서 아래와 같이 나누는 이유
```java
if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
```

![](https://github.com/jjunji/Android/blob/master/Day12_0524/image/listview_recycler.jpg)

리스트 뷰의 재활용 흐름도이다.
한 아이템이 화면 밖으로 나가게되면 해당 뷰는 재활용 가능한 영역으로 들어가게 된다.
그리고 새로운 뷰가 나타나야 할 때 getView( ) 메소드가 호출된다.
처음 리스트 뷰가 보일 때는 재활용 영역에 들어간 뷰가 없기 때문에 convertView 가 null 이지만 스크롤을 내린다던지 화면 밖으로 리스트의 한 아이템 뷰가 나가게 된다면 재활용 영역에 convertView 가 생길 것이다.
즉, 재활용 영역에 convertView가 존재하지 않을 경우에만 inflate 해서 view를 구현한다면 시스템 자원을 절약할 수 있다.

여기서, Holder 를 사용하면 자원을 보다 많이 절약할 수 있는데, convertView가 null이 아니어서 뷰를 재사용한다고 했을 때도 findViewById	를 통해 뷰를 맵핑하는 과정이 필요하다. 이 과정을 생략하기 위해 Holder 객체에 맵핑하는 과정을 만들어 놓으면 해결 할 수 있다.

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
            // 1. 이미지뷰에 onclick listener 를 달고 상세페이지로 이동시킨다.
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
            // 2. 타이틀 텍스트뷰에 onclick listener 를 달고 Toast 로 내용을 출력한다.
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, title.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
```

<br>
intent( ) 를 통해 값을 어딘가로 넘길 때 필요한 키 값을 상수로 정해놓고 사용하면 편리하다.

```java
    public static final String DATA_KEY = "position";
    public static final String DATA_RES_ID = "resid";
    public static final String DATA_TITLE = "title";
```
***
리스트 뷰에 이미지 넣기
```java
int id = context.getResources().getIdentifier(holder.image, "mipmap", context.getPackageName));

holder.image.setImageResource(id);
```
기존에 holder에 이미지를 넣는 로직을 구현했을 때 문제점은 리스트뷰의 한 아이템 뷰가 갱신될 때마다 위와 같은 작업이 계속 발생한다는 것.

**해결책**

이미지 id를 가져오는 과정을 Loader 클래스에서 진행.
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
            data.title = "피카";

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
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
    }
}
```
