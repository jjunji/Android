# SharedPreferences

**1. SharedPreferences란**

* 간단한 값 저장에 Local DB를 사용하기에는 복잡하기 때문에 SharedPreferences를 사용.
* 자동 로그인을 위한 토큰, ID, PWD 저장 등 간단한 값을 저장하기 위해 사용한다.
* Map 과 같은 형태의 자료구조
* 어플리케이션에 파일(xml) 형태로 데이터를 저장한다.
*  => data/data/패키지명/shared_prefs/SharedPreference이름.xml 위치에 저장
* 어플리케이션이 삭제되기 전까지 보존됨.
	
***
 
**2.SharedPreferences 객체**

*  키-값 쌍을 포함하는 파일을 가리키며, 키-값 쌍을 읽고 쓸 수 있는 간단한 메서드를 제공.
* 키-값 쌍을 읽고 쓰는 용도로만 사용됨.
* getSharedPreferences( ) : 여러 공유 기본 설정 파일이 필요한 경우 이 메서드를 사용합니다. 이러한 파일은 첫 번째 매개변수로 지정하는 이름으로 식별된다. 앱 내부의 모든 Context에서 이 메서드를 호출할 수 있음.
* getPreferences( ) : 액티비티에 하나의 공유 기본 설정 파일만 사용해야 하는 경우 Activity에서 이 메서드를 사용한다. 이 메서드는 액티비티에 속한 기본 공유 기본 설정 파일을 가져오기 때문에 이름을 제공할 필요없음.

***
  
  
**3.사용하기**


```java
	SharedPreferences setting;
    SharedPreferences.Editor editor;
```
SharedPreferences 객체를 참조할 변수를 선언.
값을 쓰기 위하여 edit( )을 호출하여 SharedPreferences.Editor를 가져온다.
```java
		setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("chk_auto", false)){
            et_id.setText(setting.getString("ID", ""));
            et_pw.setText(setting.getString("PW", ""));
            chk_auto.setChecked(true);
        }
```
저장 폴더에 setting.xml 로 저장됨. (다른 이름도 가능)
putBoolean( ) 및 putString( )과 같은 메서드를 사용하여 값을 추가한다.
get하는 데이터형에 맞게 디폴트값을 설정.
작업 완료는 commit( )을 해야 이루어짐. 

```java
public void onClick(View v) {
        if(chk_auto.isChecked()){
            Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show();
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
SharedPreferences 인스턴스의 getInt( ), getString( ) 메소드를 사용하여 데이터를 불러올 수 있다.

데이터를 불러오는 메서드에는 총 두 개의 인자를 넘겨주며, 첫 번째에는 불러올 데이터의 키(Key) 값, 두 번째에는 해당 키에 해당하는 값이 없을 경우 반환할 값을 넣어준다.

저장할 수 있는 데이터 타입
[Boolean, Integer, Float, Long, String]

***


**4. 삭제**
 
```java
SharedPreferences.Editor editor = mPref.edit();
editor.remove("key");
editor.clear();
editor.commit();
```
특정 키에 해당하는 값을 삭제 할 수 있고,
전부를 지울 수 도 있다.