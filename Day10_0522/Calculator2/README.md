# 공학용 계산기 만들기

**핵심 로직**

* ArrayList<>
* split()
* else if문
* 연산자 우선순위

***

**1. 각 숫자 버튼에 대한 클릭 리스너 등록**
```java
findViewById(R.id.btn1).setOnClickListener(this);
findViewById(R.id.btn2).setOnClickListener(this);
findViewById(R.id.btn3).setOnClickListener(this);
findViewById(R.id.btn4).setOnClickListener(this);
findViewById(R.id.btn5).setOnClickListener(this);
findViewById(R.id.btn6).setOnClickListener(this);
findViewById(R.id.btn7).setOnClickListener(this);
findViewById(R.id.btn8).setOnClickListener(this);
findViewById(R.id.btn9).setOnClickListener(this);
findViewById(R.id.btn0).setOnClickListener(this);
```
**2. 각 연산자 버튼에 대한 클릭 리스너 등록**
```java
findViewById(R.id.btnPlus).setOnClickListener(this);
findViewById(R.id.btnMinus).setOnClickListener(this);
findViewById(R.id.btnMultiply).setOnClickListener(this);
findViewById(R.id.btnDivide).setOnClickListener(this);
findViewById(R.id.btnResult).setOnClickListener(this);
findViewById(R.id.btnClear).setOnClickListener(this);
```

**3. 정의된 위젯변수에 xml의 위젯 id를 가져와서 담는다.**
```java
txtPreview = (TextView) findViewById(R.id.txtPreview);
txtResult = (TextView) findViewById(R.id.txtResult);
```

**4. 각 버튼을 누르면 setPreview( ) 함수에 인자로 전달함으로써, 수식을 저장한다.**
```java
 @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn1: setPreview(1); break;
            case R.id.btn2: setPreview(2); break;
            case R.id.btn3: setPreview(3); break;
            case R.id.btn4: setPreview(4); break;
            case R.id.btn5: setPreview(5); break;
            case R.id.btn6: setPreview(6); break;
            case R.id.btn7: setPreview(7); break;
            case R.id.btn8: setPreview(8); break;
            case R.id.btn9: setPreview(9); break;
            case R.id.btn0: setPreview(0); break;

            case R.id.btnPlus: setPreview("+"); break;
            case R.id.btnMinus: setPreview("-"); break;
            case R.id.btnMultiply: setPreview("*"); break;
            case R.id.btnDivide: setPreview("/"); break;
            case R.id.btnResult:
                result();
                break;
            case R.id.btnClear:
                clear();
                break;
        }
    }
```

***

**5. 최종 값을 구하는 calculate 함수**

```java
ex) 30+5*2/2 라는 값이 preview에 저장되어 있다면, 그 값을 가져와 정규식으로 split하여 분해한다.  -> (30), (+), (5), (*), (2), (/), (2) 로 분해됨.

splited[ ] 배열에 분해되어 담긴 값들을 동적 배열인, ArrayList<String> 에 순서대로 담는다.

다음 연산자 우선순위를 고려하여 *,/ 를 만나면 먼저 계산한 뒤, +,- 값을 계산했다.

	 (0)   (1)  (2)  (3)  (4)  (5)  (6)
원리는 30    +    5    *    2    /    2 
과 같이 ArrayList<>에 담겨 있는데, *를 발견하면 그 시점의 i값은 * 를 가리킨다.

그렇다면 i-1 값과 i+1 을 곱하고 i-1 인덱스에 set( )을 하고 나머지 계산한 부분(i,i+1)인덱스를 remove( )로 삭제한다.

ArrayList는 동적배열이므로 삭제된 인덱스로 다음 인덱스가 당겨지며 여기까지의 결과는 아래와 같이 나타난다.

(0)   (1)   (2)  (3)  (4)  (5)  (6)       (0)   (1)   (2)   (3)  (4)
30     +     5    *    2    /    2        30     +     10    /    2

```

주의

1. set( ) 함수를 쓰지 않고 add( )를 사용하여 에러가 발생했었다 -> add( )를 할 경우, 값이 해당 순번에 값이 입력되고 그 순번에 있던 원래 값이 뒤로 밀림.

2. Integer.getInteger( ) 함수와 Integer.parseInt( )를 헷갈림.

```java
private void result(){
        String current = txtPreview.getText().toString();
        txtResult.setText(calculate(current));
    }
    private String calculate(String preview){

        // TODO 문자열을 쪼갠후 우선순위에 따라 연산하시오
        // 1. 문자열을 정규식으로 * / + - 을 이용해서 배열로 자른다                       (0) (1) (2) (3) (4) (5) (6)
        String splited[] = preview.split("(?<=[*/+-])|(?=[*/+-])");  // ex) 30+5x2/2  ->  30 | + | 5 | X | 2 | / | 2
        ArrayList<String> splited2 =  new ArrayList<>();

        for(int i=0; i < splited.length; i++){
            splited2.add(splited[i]);
        }

            for (int i = 0; i < splited2.size(); i++) {
                String temp = "";
                int temp_int = 0;

                if (splited2.get(i).equals("*")) {
                    //int a = Integer.getInteger(splited[i-1]) * Integer.getInteger(splited[i+1]);
                    temp_int = Integer.parseInt(splited2.get(i - 1)) * Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if (splited2.get(i).equals("/")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) / Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if(splited2.get(i).equals("+")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) + Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                } else if (splited2.get(i).equals("-")) {
                    temp_int = Integer.parseInt(splited2.get(i - 1)) - Integer.parseInt(splited2.get(i + 1));
                    temp = temp_int + "";
                    splited2.set(i - 1, temp);
                    splited2.remove(i+1);
                    splited2.remove(i);
                    i--;
                }
            }
            return splited2.get(0);
    }
```

**6. Preview 정보를 가지는 함수와 C버튼 클릭시 초기화 되는 부분.**

```java
 private void setPreview(int number){
        String current = txtPreview.getText().toString();
        txtPreview.setText(current + number);
    }

    private void setPreview(String str){
        String current = txtPreview.getText().toString();
        txtPreview.setText(current + str);
    }

    private void clear(){
        txtPreview.setText("");
        txtResult.setText("0");
    }
```