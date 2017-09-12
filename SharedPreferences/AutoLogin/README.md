# SharedPreferences

**1. SharedPreferences��**

* ������ �� ���忡 Local DB�� ����ϱ⿡�� �����ϱ� ������ SharedPreferences�� ���.
* �ڵ� �α����� ���� ��ū, ID, PWD ���� �� ������ ���� �����ϱ� ���� ����Ѵ�.
* Map �� ���� ������ �ڷᱸ��
* ���ø����̼ǿ� ����(xml) ���·� �����͸� �����Ѵ�.
*  => data/data/��Ű����/shared_prefs/SharedPreference�̸�.xml ��ġ�� ����
* ���ø����̼��� �����Ǳ� ������ ������.
	
***
 
**2.SharedPreferences ��ü**

*  Ű-�� ���� �����ϴ� ������ ����Ű��, Ű-�� ���� �а� �� �� �ִ� ������ �޼��带 ����.
* Ű-�� ���� �а� ���� �뵵�θ� ����.
* getSharedPreferences( ) : ���� ���� �⺻ ���� ������ �ʿ��� ��� �� �޼��带 ����մϴ�. �̷��� ������ ù ��° �Ű������� �����ϴ� �̸����� �ĺ��ȴ�. �� ������ ��� Context���� �� �޼��带 ȣ���� �� ����.
* getPreferences( ) : ��Ƽ��Ƽ�� �ϳ��� ���� �⺻ ���� ���ϸ� ����ؾ� �ϴ� ��� Activity���� �� �޼��带 ����Ѵ�. �� �޼���� ��Ƽ��Ƽ�� ���� �⺻ ���� �⺻ ���� ������ �������� ������ �̸��� ������ �ʿ����.

***
  
  
**3.����ϱ�**


```java
	SharedPreferences setting;
    SharedPreferences.Editor editor;
```
SharedPreferences ��ü�� ������ ������ ����.
���� ���� ���Ͽ� edit( )�� ȣ���Ͽ� SharedPreferences.Editor�� �����´�.
```java
		setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("chk_auto", false)){
            et_id.setText(setting.getString("ID", ""));
            et_pw.setText(setting.getString("PW", ""));
            chk_auto.setChecked(true);
        }
```
���� ������ setting.xml �� �����. (�ٸ� �̸��� ����)
putBoolean( ) �� putString( )�� ���� �޼��带 ����Ͽ� ���� �߰��Ѵ�.
get�ϴ� ���������� �°� ����Ʈ���� ����.
�۾� �Ϸ�� commit( )�� �ؾ� �̷����. 

```java
public void onClick(View v) {
        if(chk_auto.isChecked()){
            Toast.makeText(this, "�α���", Toast.LENGTH_SHORT).show();
            String ID = et_id.getText().toString();
            String PW = et_pw.getText().toString();

            editor.putString("ID", ID);
            editor.putString("PW", PW);
            editor.putBoolean("chk_auto", true);
            editor.commit();
        }else{
            editor.clear();
            editor.commit();
        }
```
SharedPreferences �ν��Ͻ��� getInt( ), getString( ) �޼ҵ带 ����Ͽ� �����͸� �ҷ��� �� �ִ�.

�����͸� �ҷ����� �޼��忡�� �� �� ���� ���ڸ� �Ѱ��ָ�, ù ��°���� �ҷ��� �������� Ű(Key) ��, �� ��°���� �ش� Ű�� �ش��ϴ� ���� ���� ��� ��ȯ�� ���� �־��ش�.

������ �� �ִ� ������ Ÿ��
[Boolean, Integer, Float, Long, String]

***


**4. ����**
 
```java
SharedPreferences.Editor editor = mPref.edit();
editor.remove("key");
editor.clear();
editor.commit();
```
Ư�� Ű�� �ش��ϴ� ���� ���� �� �� �ְ�,
���θ� ���� �� �� �ִ�.