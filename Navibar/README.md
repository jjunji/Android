### 네비게이션 바 사용하기

---

프로젝트 생성시 Navigation Drawer Activity 로 생성
Navigation Drawer Activity 로 프로젝트를 생성하면 네이게이션 바를 생성하는 코드가 생성된다. -> 이것을 활용하는 방법을 알아야한다.

네이게이션 메뉴 클릭시 호출되는 함수
```java
@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
```
* onNavigationItemSelected 함수 : 각 메뉴가 선택 되었을 때 발생하는 이벤트 정의
ex)  첫번째 메뉴를 클릭 했을 때 ListActivity.class 로 이동하게 하고 싶다면
      조건 문안에 Intent intent = new Intent(this, ListActivity.class);
						   startActivity(intent); 만 해주면 된다.

---
우측 상단 옵션 메뉴를 나타내는 코드
```java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
```
옵션 버튼을 나타내는 코드로 불필요 시 삭제해도 무방.

---
onCreate( ) 아래 선언된 NavigationView를 보면 네비 바를 클릭 했을 때 나오는 전체 뷰를 정의하고 있다는 것을 알 수 있다.
```java
NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
```
* R.id.nav_view : Navi 바를 클릭 했을 때 나오는 전체 뷰 저으이
*  각 메뉴에 대한 뷰는 xml에 따로 정의돼 있다.

```java
<android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />
```
* 각 메뉴에 대한 뷰가 정의된 곳은 -> @menu/activity_main_drawer

activity_main_drawer.xml
```java
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_camera"
            android:icon="@drawable/ic_menu_camera"
            android:title="Import" />
        <item
            android:id="@+id/nav_gallery"
            android:icon="@drawable/ic_menu_gallery"
            android:title="Gallery" />
        <item
            android:id="@+id/nav_slideshow"
            android:icon="@drawable/ic_menu_slideshow"
            android:title="Slideshow" />
        <item
            android:id="@+id/nav_manage"
            android:icon="@drawable/ic_menu_manage"
            android:title="Tools" />
    </group>

    <item android:title="Communicate">
        <menu>
            <item
                android:id="@+id/nav_share"
                android:icon="@drawable/ic_menu_share"
                android:title="Share" />
            <item
                android:id="@+id/nav_send"
                android:icon="@drawable/ic_menu_send"
                android:title="Send" />
        </menu>
    </item>

</menu>

```
* 여기서 각 메뉴의 대한 설정을 한다.
* item 태그를 추가하면 메뉴가 추가 된다. -> 메뉴를 여러개 추가해서 화면을 넘어가면 자동으로 스크롤 생성.