### �׺���̼� �� ����ϱ�

---

������Ʈ ������ Navigation Drawer Activity �� ����
Navigation Drawer Activity �� ������Ʈ�� �����ϸ� ���̰��̼� �ٸ� �����ϴ� �ڵ尡 �����ȴ�. -> �̰��� Ȱ���ϴ� ����� �˾ƾ��Ѵ�.

���̰��̼� �޴� Ŭ���� ȣ��Ǵ� �Լ�
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
* onNavigationItemSelected �Լ� : �� �޴��� ���� �Ǿ��� �� �߻��ϴ� �̺�Ʈ ����
ex)  ù��° �޴��� Ŭ�� ���� �� ListActivity.class �� �̵��ϰ� �ϰ� �ʹٸ�
      ���� ���ȿ� Intent intent = new Intent(this, ListActivity.class);
						   startActivity(intent); �� ���ָ� �ȴ�.

---
���� ��� �ɼ� �޴��� ��Ÿ���� �ڵ�
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
�ɼ� ��ư�� ��Ÿ���� �ڵ�� ���ʿ� �� �����ص� ����.

---
onCreate( ) �Ʒ� ����� NavigationView�� ���� �׺� �ٸ� Ŭ�� ���� �� ������ ��ü �並 �����ϰ� �ִٴ� ���� �� �� �ִ�.
```java
NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
```
* R.id.nav_view : Navi �ٸ� Ŭ�� ���� �� ������ ��ü �� ������
*  �� �޴��� ���� ��� xml�� ���� ���ǵ� �ִ�.

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
* �� �޴��� ���� �䰡 ���ǵ� ���� -> @menu/activity_main_drawer

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
* ���⼭ �� �޴��� ���� ������ �Ѵ�.
* item �±׸� �߰��ϸ� �޴��� �߰� �ȴ�. -> �޴��� ������ �߰��ؼ� ȭ���� �Ѿ�� �ڵ����� ��ũ�� ����.