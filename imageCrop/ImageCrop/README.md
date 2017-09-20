# Image Crop

���������� �̹��� ȣ�� �� Crop �غ���

AndroidManifest
```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.CAMERA"></uses-permission>
    <uses-feature android:name="android.hardware.Camera"></uses-feature>
```
���� ���� ���� & ���� ������ ������ �ܺ� ���丮���� ���� ���� ������ �߰��Ѵ�.

***
![](https://github.com/jjunji/Android/blob/master/imageCrop/ImageCrop/image/asset.PNG)

drawable - New - Image Asset
Configure Image Asset â����
Icon Type : Action Bar and Tab Icons �� �����ϰ�
Clip Art & ������ �����Ѵ�.
-> ���ٿ� ������ ������ �̵�, ī�޶� �̵� �̹����� �����ϴ� ����.
***

res - menu ���丮 ����
���丮 �Ʒ��� menu_main.xml ����
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto">

<item
    android:id="@+id/btn_camera"
    android:icon="@drawable/ic_camera"
    android:title="Camera"
    app:showAsAction="always"></item>

<item
    android:id="@+id/btn_gallery"
    android:icon="@drawable/ic_gallery"
    android:title="Camera"
    app:showAsAction="always"></item>

</menu>
```
���ٿ� ����� ��ư �̹��� ����

![](https://github.com/jjunji/Android/blob/master/imageCrop/ImageCrop/image/menu_main.PNG)

***

activity_main
```
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:titleTextColor="@android:color/white"
        android:background="?attr/colorPrimary"
        android:minHeight="?attr/actionBarSize"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:layout_editor_absoluteY="0dp"
        tools:layout_editor_absoluteX="8dp">
    </android.support.v7.widget.Toolbar>
    
    <ImageView
        android:id="@+id/imageView"
        android:layout_below="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
�⺻ style�� NoActionBar �� �����ѵ� toolBar�� �ʿ��� ���� ���� �����ؼ� ����� �� �ִ�.

***
MainActivity
```
int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            RequestRuntimePermission();
        }
```
Android 6.0(API ���� 23)���� ����ڴ� ���� ��ġ�� ���� �ƴ϶� ���� ����Ǵ� �߿� �ۿ� ������ �ο��Ѵ�.
 �� ���ٹ�Ŀ��� ����ڰ� ���� ��ġ�ϰų� ������Ʈ�� �� ������ �ο��� �ʿ䰡 �����Ƿ� �� ��ġ ������ ����ȭ��.

 **ContextCompat.checkSelfPermission( )**
 ���� ���� ���θ� Ȯ�� �޼ҵ�

���� Ȯ�� �� ������ RequestRuntimePermission(); ȣ�� ( ���� ������ ��û �޼ҵ�)

```
private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA))
            Toast.makeText(this, "Camera Permission allows us to access Camera app", Toast.LENGTH_SHORT).show();
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissonCode);
        }
    }
```
����ڷο��� ���ʷ� ���� ��ó�� �ϴ� ������, �ƴ����� ���� ������ �ϰ�
�׿� �ش��ϴ� ��û�� ������.

```java
private void CameraOpen() {
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", true);
        startActivityForResult(CamIntent, 0);
    }
    
private void GalleryOpen() {
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "Select Image from Gallery"),2);
    }
```
ī�޶� �̵�, ������ �̵��� ���� ����

```java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            CropImage();
        }
        else if(requestCode == 2){
            if(data != null){
                uri = data.getData();
                CropImage();
            }
        }
        else if(requestCode == 1){
            if(data != null){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }
```
��û�� ���� ��û �ڵ忡 ���� CropImage( ) �޼ҵ� ȣ��.
```java
   private void CropImage() {
        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("ScaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);
        }
        catch (ActivityNotFoundException ex){

        }
    }
```
aspect : �̹����� �߶󳾶� X�� Y���� ������ ����.
output : ����� �̹����� width�� height�� �����ϴ°����� �������� ���� ���
	   �����̹��� ũ��� ������.
	   
```java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestPermissonCode:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
```