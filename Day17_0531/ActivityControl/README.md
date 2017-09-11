# ActivityControl

startActivityForResult - Activity ���̿��� �� �ְ�ޱ�
<br>

MainActivity
```java
intent = new Intent(this, SubActivity.class);
```

```java
	public static final int BTN_START = 98;
    public static final int BTN_RESULT = 99;  // int ������ �޴� ������ �Ƹ� ���� ������ �ϱ� ������ �ƴұ�.

    @Override
    public void onClick(View v) {
        // Intent intent = new Intent(this, SubActivity.class);  // intent�� �ٲ��� �����Ƿ� ���� �� �ʿ� ����, Ŭ���� �� ���� new -> �޸� ����
        switch (v.getId()){
            // �Ϲ����� Activity start
            case R.id.btnStart :
                startActivity(intent);  // intent���� ������ ������ �����Ƿ� SubActivity.class ȣ��.
                break;
            // ���� �����޴� Activity start
            case R.id.btnResult :
                                            // ���� = "��" / Ư�� ���� ������ ���� ���� �ش��ϴ� �̸��� �����ִ� ����
                //intent.putExtra("key","From button result");
                intent.putExtra("key",editText.getText().toString());
                // ���� ��ư�� ���� �Լ�(sub��Ƽ��Ƽ ȣ��)�� ȣ���ϰ� ������ �ٸ������ �����Ѵٸ� �ν�ǥ�� �ʿ��ϰ���
                // ������, ��� ���� ������ �˱� ����. (sub��Ƽ��Ƽ�� ȣ���ϴ� ��ư�� ���� ��)
                startActivityForResult(intent, BTN_START);
                // start subActivity > finish() > ������� �����ش�. > MainActivity.onActivityResult
                break;
        }
    }
```
(1) case R.id.btnStart

startActivity(intent); 
 -> ���� intent���� SubActivity.class�� ȣ���϶�� ������ ������ �ִ�.
-> ��ư Ŭ���� �ش� Ŭ������ ȣ��.

 (2) case R.id.btnResult :
intent.putExtra("key", editText.getText().toString());
startActivityForResult(intent, BTN_RESULT);
break;

1) id�� btnResult�� ��ư�� ������ �� �Ʒ� ������ ���� �ȴ�.

2) intent��ü�� �����͸� ������ Intent�� ������ �ִ� Bundle�� �ν��Ͻ��� bundle�� �Ʒ��� ���� ���·� ����ȴ�.
   "key" ���� �����ϴ� ������ �������� ������ bundle���� ���� ���ϴ� Ư�� ���� key�� ���� ã��, ������ ����ϱ� �����̴�.
![]()

3) startActivityForResult(intent, BTN_START);

���⼭ BTN_START�� ���� ������ �����Ϳ� ���� �ĺ����̸�, ����ϴ� ������ �����͸� �޴� SubActivityŬ�������� �� �����Ͱ� ���κ��� �� ���������� �ĺ��ϱ� �����̴�.

***

```java
 @Override                           // ���� �ڵ�   // ��ȯ�ϴ� �ʿ��� ������ ��
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                                                        // ������� ��ܿ´�.
        // super.onActivityResult(requestCode, resultCode, data); // ->
        Toast.makeText(this, "Result Code="+resultCode, Toast.LENGTH_SHORT).show();

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case BTN_RESULT:
                                // Intent �� data���� result ������ ���� �����µ�
                                // ���� ������� ����Ʈ������ 0 �� ����Ѵ�.
                    int result = data.getIntExtra("result",0);
                    editText.setText("�����="+result);
                    break;
                case BTN_START:
                    Toast.makeText(this, "Start ��ư�� �����ٰ� ���ƿ�", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
```
 onActivityResult(int requestCode, int resultCode, Intent data) 
 
    1) requestCode : startActivityForResult�� ������ ��ü�� �����ϱ� ���� �÷���

    2) resultCode : ���ó���� �������� | RESULT_OK = ����

    3) data : �������� ���� ����ִ� Intent



 ���� Ŭ�������� ��� ó���� �Ϸ��� intent�� RESULT_OK ������ ���� ��
���� Ŭ���ߴ� ��ư��  case�� ã�� �� �� �ش��ϴ� ������ �����Ѵ�.

startActivityForResult(intent, BTN_RESULT); -> BTN_START ���ٸ�, �ش� ������ ����Ǵ� ��.

***

SubActivity

```java
// 1. ���� ��Ƽ��Ƽ���� �Ѿ�� intent ��ü
        final Intent intent = getIntent();  // ����� null �� �ȵȴ�.
        // 2. ���� ������ ������.
        Bundle bundle  = intent.getExtras(); // ����� ���޵� ���� ������ null �� �ȴ�.
        // 3. ���� ���� ������. ������ ���� null üũ�� �������.
        if(bundle != null){
            value = bundle.getString("key");
            // 3.1 ���� ������ textView�� ����Ѵ�.
            textView.setText(value);
        }
        // ���� ����(2,3��)�� ���ĳ��� method
        // ��ü������ bundle�� ���� null ó�� ������ �����ϰ� �ִ�.
        // String value = intent.getStringExtra("key");
```

![]()

(1) final Intent intent = getIntent(); 
	���� ��Ƽ��Ƽ�� bundle

(2) Bundle bundle  = intent.getExtras();
    ���� ��Ƽ��Ƽ�� bundle�� ���� ��Ƽ��Ƽ���� �Ѿ�� intent�� bundle�ȿ� �ִ� ������ �־��ش�.

***

```java
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity���� �Ѱܹ��� ���� int�� ��ȯ
                int num1 = Integer.parseInt(value);
                // ���� Activity�� �Էµ� ���� �޾Ƽ�
                String temp = editText.getText().toString();
                // int �� ��ȯ
                int num2 = Integer.parseInt(temp);

                int result = num1 + num2;

                /* �� ��ȯ�ϱ� */

                // ������� intent�� ��Ƽ�     //                              �ý��� �ڿ��� �̿��ϴ� ���� �ƴϰ�
                Intent intent = new Intent(); // context�� �ѱ��� ��´�. ��? -> �̹� �ִ� ��Ƽ��Ƽ�� ���� �Ѱ��ִ� ���̱� ����
                intent.putExtra("result", result); //

                // setResult�� �Ѱ��ش�.
                setResult(RESULT_OK, intent); // setResult : ���� ��Ƽ��Ƽ�� intent�� ���� ��� ���� ��. finish()�ϸ� onResume ó�� �ý��ۿ��� ȣ��.

                // ���� activity�� �����Ѵ�.
                finish();  //finish()�� �ϸ� onActivityResult ȣ��
            }
        });
    }
```
1) ���� ��Ƽ��Ƽ�� ���� ���� bundle�� ��� ����.

2) setResult(RESULT_OK, intent) : setResult( ) �ϹǷν�, ��Ƽ��Ƽ�� ����Ǿ ���� ������ �ִٰ� MainActivity�� ����.

3) ��ư Ŭ���� ��Ƽ��Ƽ�� �����Ѵ�. -> �����ϸ鼭 onActivityResult �� ȣ���.
