# Image Crop

갤러리에서 이미지 호출 후 Crop 해보기

AndroidManifest
```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.CAMERA"></uses-permission>
    <uses-feature android:name="android.hardware.Camera"></uses-feature>
```
사진 접근 권한 & 찍은 사진을 저장할 외부 스토리지에 대한 접근 권한을 추가한다.

***
![](https://github.com/jjunji/Android/blob/master/imageCrop/ImageCrop/image/asset.PNG)

drawable - New - Image Asset
Configure Image Asset 창에서
Icon Type : Action Bar and Tab Icons 로 지정하고
Clip Art & 색상값을 변경한다.
-> 툴바에 삽입할 갤러리 이동, 카메라 이동 이미지를 정의하는 것임.
***

res - menu 디렉토리 생성
디렉토리 아래에 menu_main.xml 생성
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
툴바에 적용될 버튼 이미지 정의

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
기본 style을 NoActionBar 로 적용한뒤 toolBar가 필요한 곳만 직접 정의해서 사용할 수 있다.

***
MainActivity
```
int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            RequestRuntimePermission();
        }
```
Android 6.0(API 레벨 23)부터 사용자는 앱이 설치될 때가 아니라 앱이 실행되는 중에 앱에 권한을 부여한다.
 이 접근방식에서 사용자가 앱을 설치하거나 업데이트할 때 권한을 부여할 필요가 없으므로 앱 설치 과정이 간소화됨.

 **ContextCompat.checkSelfPermission( )**
 권한 보유 여부를 확인 메소드

권한 확인 후 없으면 RequestRuntimePermission(); 호출 ( 직접 정의한 요청 메소드)

```
private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA))
            Toast.makeText(this, "Camera Permission allows us to access Camera app", Toast.LENGTH_SHORT).show();
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissonCode);
        }
    }
```
사용자로에게 최초로 권한 요처을 하는 것인지, 아닌지에 대한 구분을 하고
그에 해당하는 요청을 보낸다.

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
카메라 이동, 갤러리 이동에 대한 정의

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
요청에 대한 요청 코드에 따라 CropImage( ) 메소드 호출.
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
aspect : 이미지를 잘라낼때 X축 Y축의 비율을 설정.
output : 출력할 이미지의 width와 height를 설정하는것으로 설정하지 않을 경우
	   기존이미지 크기로 설정됨.
	   
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