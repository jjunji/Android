# BasicWidget

```java
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    // 1. ���� ������ ����
    Button btnDog, btnPig, btnHorse;
    ToggleButton toggleButton;
    RadioGroup radiogroup;
    SeekBar seekBar;
    TextView seekCount;
```
���� ��ư�� �׷����� ��� ������ ������ �ϳ��� ���� �� �ϳ��� ������ ��Ʈ���� �ȵ�.
��� ���� ������ ���߼����� ���������� �̰��� �̹� üũ�ڽ���� ui�� ����.

```java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. ���������� ȭ��� ����
        btnDog = (Button) findViewById(R.id.btnDog);
        btnPig = (Button) findViewById(R.id.btnPig);
        btnHorse = (Button) findViewById(R.id.btnHorse);

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekCount = (TextView) findViewById(R.id.seekCount);

        // 3. Ŭ�������� ����
        btnDog.setOnClickListener(this); // �����ʿ� this(����)�� �Ѱ��ָ�
        btnPig.setOnClickListener(this); // �ش� �̺�Ʈ�� �߻��� this(����)�� ȣ�����ش�.
        btnHorse.setOnClickListener(this);

        toggleButton.setOnCheckedChangeListener(this); // üũ��ü���� ������ <- Ŭ�� �����ʰ� �ƴ�.
/*        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });*/  // ��ó�� ���ϰ� ������ ���� ����ϴ� ���� ��ó�� �� ��� �۾��ϳ��� ��������. ���� ����� 3�� �߰��ȴٰ� �Ѵٸ� �� �ҽ��� 3�� �����.
        radiogroup.setOnCheckedChangeListener(this);

        seekBar.setOnSeekBarChangeListener(listener);
    }
```
���������� ȭ��� �����ϰ� �̺�Ʈ �����ʸ� �޾��ش�.
```java
@Override                       // View�� �ֻ��� Ŭ���� (Object ����)
    public void onClick(View v) {  // �ý����� �̺�Ʈ �����ʸ� ���� ȣ��ȴ�. ������ �ý����� ��� �˰� ȣ�����ִ���
        switch(v.getId()){  // ������ ĳ���� ? ���� Dog������ Dog�� �Ѿ���� Pig�� ������ Pig�� �Ѿ�´�.
            case R.id.btnDog:
                Toast.makeText(this, "�۸�", Toast.LENGTH_SHORT).show(); // this�� �ִ� ���� : toast�� ���������� ��������� ���� �ڿ��� ����ϱ� ����.
                break;                                                  // activity�� �̹� context�� ��ӹ޾Ƽ� ����ϰ� �ִ�.
            case R.id.btnPig:
                Toast.makeText(this, "�ܲ�", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnHorse:
                Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toggleButton:

                break;
        }
    }
```
��� ��ư ��Ʈ���ϴ� �κ�.
```
@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.toggleButton:
                if(isChecked){
                    Toast.makeText(this, "����ġ�� ����", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
```
���� ��ư ��Ʈ���ϴ� �κ�.
```java
 @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()){
            case R.id.radiogroup:
                switch(checkedId){
                    case R.id.RadioRed :
                    Toast.makeText(this,"������",Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.RadioGreen :
                        Toast.makeText(this,"�ʷϺ�",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RadioBlue :
                        Toast.makeText(this,"�Ķ���",Toast.LENGTH_SHORT).show();
                        break;
                }
        }
    }
```
SeekBar ��Ʈ���ϴ� �κ�.
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