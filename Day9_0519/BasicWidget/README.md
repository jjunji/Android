# BasicWidget

```java
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    // 1. 위젯 변수를 선언
    Button btnDog, btnPig, btnHorse;
    ToggleButton toggleButton;
    RadioGroup radiogroup;
    SeekBar seekBar;
    TextView seekCount;
```
라디오 버튼을 그룹으로 묶어서 만들지 않으면 하나를 켰을 때 하나가 꺼지는 컨트롤이 안됨.
대신 하지 않으면 다중선택이 가능하지만 이것은 이미 체크박스라는 ui가 존재.

```java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 위젯변수를 화면과 연결
        btnDog = (Button) findViewById(R.id.btnDog);
        btnPig = (Button) findViewById(R.id.btnPig);
        btnHorse = (Button) findViewById(R.id.btnHorse);

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekCount = (TextView) findViewById(R.id.seekCount);

        // 3. 클릭리스너 연결
        btnDog.setOnClickListener(this); // 리스너에 this(무언가)를 넘겨주면
        btnPig.setOnClickListener(this); // 해당 이벤트가 발생시 this(무언가)를 호출해준다.
        btnHorse.setOnClickListener(this);

        toggleButton.setOnCheckedChangeListener(this); // 체크드체인지 리스너 <- 클릭 리스너가 아님.
/*        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });*/  // 이처럼 안하고 밑으로 빼서 사용하는 것은 위처럼 할 경우 작업하나만 가능해짐. 당장 토글이 3개 추가된다고 한다면 위 소스를 3번 써야함.
        radiogroup.setOnCheckedChangeListener(this);

        seekBar.setOnSeekBarChangeListener(listener);
    }
```
위젯변수를 화면과 연결하고 이벤트 리스너를 달아준다.
```java
@Override                       // View는 최상위 클래스 (Object 같은)
    public void onClick(View v) {  // 시스템의 이벤트 리스너를 통해 호출된다. 문제는 시스템이 어떻게 알고 호출해주느냐
        switch(v.getId()){  // 다형성 캐스팅 ? 으로 Dog누르면 Dog가 넘어오고 Pig를 누르면 Pig가 넘어온다.
            case R.id.btnDog:
                Toast.makeText(this, "멍멍", Toast.LENGTH_SHORT).show(); // this를 주는 이유 : toast는 독립적으로 사용하지만 앱의 자원을 사용하기 위해.
                break;                                                  // activity는 이미 context를 상속받아서 사용하고 있다.
            case R.id.btnPig:
                Toast.makeText(this, "꿀꿀", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnHorse:
                Toast.makeText(this, "이힝", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toggleButton:

                break;
        }
    }
```
토글 버튼 컨트롤하는 부분.
```
@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.toggleButton:
                if(isChecked){
                    Toast.makeText(this, "스위치가 켜짐", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "꺼짐", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
```
라디오 버튼 컨트롤하는 부분.
```java
 @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()){
            case R.id.radiogroup:
                switch(checkedId){
                    case R.id.RadioRed :
                    Toast.makeText(this,"빨간불",Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.RadioGreen :
                        Toast.makeText(this,"초록불",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioBlue :
                        Toast.makeText(this,"파란불",Toast.LENGTH_SHORT).show();
                        break;
                }
        }
    }
```
SeekBar 컨트롤하는 부분.
```java
SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekCount.setText(progress+"");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
```