# ���п� ���� �����

**�ٽ� ����**

* ArrayList<>
* split()
* else if��
* ������ �켱����

***

**1. �� ���� ��ư�� ���� Ŭ�� ������ ���**
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
**2. �� ������ ��ư�� ���� Ŭ�� ������ ���**
```java
findViewById(R.id.btnPlus).setOnClickListener(this);
findViewById(R.id.btnMinus).setOnClickListener(this);
findViewById(R.id.btnMultiply).setOnClickListener(this);
findViewById(R.id.btnDivide).setOnClickListener(this);
findViewById(R.id.btnResult).setOnClickListener(this);
findViewById(R.id.btnClear).setOnClickListener(this);
```

**3. ���ǵ� ���������� xml�� ���� id�� �����ͼ� ��´�.**
```java
txtPreview = (TextView) findViewById(R.id.txtPreview);
txtResult = (TextView) findViewById(R.id.txtResult);
```

**4. �� ��ư�� ������ setPreview( ) �Լ��� ���ڷ� ���������ν�, ������ �����Ѵ�.**
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

**5. ���� ���� ���ϴ� calculate �Լ�**

```java
ex) 30+5*2/2 ��� ���� preview�� ����Ǿ� �ִٸ�, �� ���� ������ ���Խ����� split�Ͽ� �����Ѵ�.  -> (30), (+), (5), (*), (2), (/), (2) �� ���ص�.

splited[ ] �迭�� ���صǾ� ��� ������ ���� �迭��, ArrayList<String> �� ������� ��´�.

���� ������ �켱������ ����Ͽ� *,/ �� ������ ���� ����� ��, +,- ���� ����ߴ�.

	 (0)   (1)  (2)  (3)  (4)  (5)  (6)
������ 30    +    5    *    2    /    2 
�� ���� ArrayList<>�� ��� �ִµ�, *�� �߰��ϸ� �� ������ i���� * �� ����Ų��.

�׷��ٸ� i-1 ���� i+1 �� ���ϰ� i-1 �ε����� set( )�� �ϰ� ������ ����� �κ�(i,i+1)�ε����� remove( )�� �����Ѵ�.

ArrayList�� �����迭�̹Ƿ� ������ �ε����� ���� �ε����� ������� ��������� ����� �Ʒ��� ���� ��Ÿ����.

(0)   (1)   (2)  (3)  (4)  (5)  (6)       (0)   (1)   (2)   (3)  (4)
30     +     5    *    2    /    2        30     +     10    /    2

```

����

1. set( ) �Լ��� ���� �ʰ� add( )�� ����Ͽ� ������ �߻��߾��� -> add( )�� �� ���, ���� �ش� ������ ���� �Էµǰ� �� ������ �ִ� ���� ���� �ڷ� �и�.

2. Integer.getInteger( ) �Լ��� Integer.parseInt( )�� �򰥸�.

```java
private void result(){
        String current = txtPreview.getText().toString();
        txtResult.setText(calculate(current));
    }
    private String calculate(String preview){

        // TODO ���ڿ��� �ɰ��� �켱������ ���� �����Ͻÿ�
        // 1. ���ڿ��� ���Խ����� * / + - �� �̿��ؼ� �迭�� �ڸ���                       (0) (1) (2) (3) (4) (5) (6)
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

**6. Preview ������ ������ �Լ��� C��ư Ŭ���� �ʱ�ȭ �Ǵ� �κ�.**

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