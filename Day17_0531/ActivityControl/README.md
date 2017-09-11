# ActivityControl

startActivityForResult - Activity 사이에서 값 주고받기
<br>

MainActivity
```java
intent = new Intent(this, SubActivity.class);
```

```java
	public static final int BTN_START = 98;
    public static final int BTN_RESULT = 99;  // int 값으로 받는 이유는 아마 빠른 연산을 하기 위함이 아닐까.

    @Override
    public void onClick(View v) {
        // Intent intent = new Intent(this, SubActivity.class);  // intent가 바뀌지 않으므로 여기 쓸 필요 없다, 클릭할 때 마다 new -> 메모리 낭비
        switch (v.getId()){
            // 일반적인 Activity start
            case R.id.btnStart :
                startActivity(intent);  // intent에는 목적지 정보가 있으므로 SubActivity.class 호출.
                break;
            // 값을 돌려받는 Activity start
            case R.id.btnResult :
                                            // 변수 = "값" / 특정 값을 꺼내기 위해 값에 해당하는 이름을 지어주는 행위
                //intent.putExtra("key","From button result");
                intent.putExtra("key",editText.getText().toString());
                // 여러 버튼이 같은 함수(sub액티비티 호출)를 호출하고 각각의 다른결과를 얻어야한다면 인식표가 필요하겠지
                // 구분자, 어디서 오는 것인지 알기 위함. (sub액티비티를 호출하는 버튼이 많을 때)
                startActivityForResult(intent, BTN_START);
                // start subActivity > finish() > 결과값을 돌려준다. > MainActivity.onActivityResult
                break;
        }
    }
```
(1) case R.id.btnStart

startActivity(intent); 
 -> 현재 intent에는 SubActivity.class를 호출하라는 정보를 가지고 있다.
-> 버튼 클릭시 해당 클래스를 호출.

 (2) case R.id.btnResult :
intent.putExtra("key", editText.getText().toString());
startActivityForResult(intent, BTN_RESULT);
break;

1) id가 btnResult인 버튼을 눌렀을 때 아래 로직이 수행 된다.

2) intent객체에 데이터를 넣으면 Intent가 가지고 있는 Bundle의 인스턴스인 bundle에 아래와 같은 형태로 저장된다.
   "key" 값을 설정하는 이유는 데이터의 묶음인 bundle에서 내가 원하는 특정 값을 key를 통해 찾고, 꺼내서 사용하기 위함이다.
![]()

3) startActivityForResult(intent, BTN_START);

여기서 BTN_START는 내가 보내는 데이터에 대한 식별자이며, 사용하는 이유는 데이터를 받는 SubActivity클래스에서 이 데이터가 어디로부터 온 데이터인지 식별하기 위함이다.

***

```java
 @Override                           // 구분 코드   // 반환하는 쪽에서 세팅한 값
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                                                                        // 결과값이 담겨온다.
        // super.onActivityResult(requestCode, resultCode, data); // ->
        Toast.makeText(this, "Result Code="+resultCode, Toast.LENGTH_SHORT).show();

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case BTN_RESULT:
                                // Intent 인 data에서 result 변수로 값을 꺼내는데
                                // 값이 없을경우 디폴트값으로 0 을 사용한다.
                    int result = data.getIntExtra("result",0);
                    editText.setText("결과값="+result);
                    break;
                case BTN_START:
                    Toast.makeText(this, "Start 버튼을 눌렀다가 돌아옴", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
```
 onActivityResult(int requestCode, int resultCode, Intent data) 
 
    1) requestCode : startActivityForResult를 실행한 주체를 구분하기 위한 플래그

    2) resultCode : 결과처리의 성공여부 | RESULT_OK = 성공

    3) data : 돌려받은 값이 담겨있는 Intent



 서브 클래스에서 결과 처리를 완료한 intent에 RESULT_OK 사인을 받은 후
내가 클릭했던 버튼의  case를 찾아 그 에 해당하는 로직을 수행한다.

startActivityForResult(intent, BTN_RESULT); -> BTN_START 였다면, 해당 로직이 수행되는 것.

***

SubActivity

```java
// 1. 이전 액티비티에서 넘어온 intent 객체
        final Intent intent = getIntent();  // 여기는 null 이 안된다.
        // 2. 값의 묶음을 꺼낸다.
        Bundle bundle  = intent.getExtras(); // 여기는 전달된 값이 없으면 null 이 된다.
        // 3. 단일 값을 꺼낸다. 꺼내기 전에 null 체크를 해줘야함.
        if(bundle != null){
            value = bundle.getString("key");
            // 3.1 값이 있으면 textView에 출력한다.
            textView.setText(value);
        }
        // 위에 두줄(2,3번)을 합쳐놓은 method
        // 자체적으로 bundle에 대한 null 처리 로직을 포함하고 있다.
        // String value = intent.getStringExtra("key");
```

![]()

(1) final Intent intent = getIntent(); 
	현재 액티비티의 bundle

(2) Bundle bundle  = intent.getExtras();
    현재 액티비티의 bundle에 이전 액티비티에서 넘어온 intent의 bundle안에 있는 값들을 넣어준다.

***

```java
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity에서 넘겨받은 값을 int로 변환
                int num1 = Integer.parseInt(value);
                // 현재 Activity에 입력된 값을 받아서
                String temp = editText.getText().toString();
                // int 로 변환
                int num2 = Integer.parseInt(temp);

                int result = num1 + num2;

                /* 값 반환하기 */

                // 결과값을 intent에 담아서     //                              시스템 자원을 이용하는 것이 아니고
                Intent intent = new Intent(); // context를 넘기지 얂는다. 왜? -> 이미 있는 액티비티에 값만 넘겨주는 것이기 때문
                intent.putExtra("result", result); //

                // setResult에 넘겨준다.
                setResult(RESULT_OK, intent); // setResult : 현재 액티비티의 intent에 값만 담아 놓는 것. finish()하면 onResume 처럼 시스템에서 호출.

                // 현재 activity를 종료한다.
                finish();  //finish()를 하면 onActivityResult 호출
            }
        });
    }
```
1) 이전 액티비티로 보낼 값을 bundle에 담는 과정.

2) setResult(RESULT_OK, intent) : setResult( ) 하므로써, 액티비티가 종료되어도 값을 가지고 있다가 MainActivity로 전달.

3) 버튼 클릭시 액티비티를 종료한다. -> 종료하면서 onActivityResult 가 호출됨.
