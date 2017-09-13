# Camera

안드로이드 갤러리 & 카메라 사용하기

MainActivity에 버튼을 2개 (갤러리 이동, 카메라 이동) 만들고,
각 버튼에 따라 갤러리 이동, 카메라 이동이 되도록 만들어보자.

***

```java
        // 버튼 잠금
        btnCamera.setEnabled(false);
        btnGallery.setEnabled(false);
```
처음 액티비티 실행시 버튼 잠금. (권한 획득 후에 잠금 풀림.)

```java
 @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        //1 권한체크 - 특정권한이 있는지 시스템에 물어본다
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            // 2. 권한이 없으면 사용자에 권한을 달라고 요청
            String permissions[] = { Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA };
            requestPermissions(permissions ,REQ_PERMISSION); // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다
        }
    }
```
권한 체크 후

* 권한이 있으면 init( ) 메소드 실행
* 권한이 없으면 요청

***

```java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 사용자가 승인을 했음
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                init();
                // 3.2 사용자가 거절 했음
            }else{
                Toast.makeText(this, "권한요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
```
권한이 없는 경우 요청을 해서 그에 따른 응답이

* 승인 -> init( ) 메소드 실행
*  거절 -> 토스트 메시지

***

```java
    private void init(){
        btnCamera.setEnabled(true);
        btnGallery.setEnabled(true);
    }
```
권한을 얻으면 버튼을 사용할 수 있게 된다.

***
갤러리 이동 / 카메라 실행 
```java
@Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnGallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요") , 100);
                break;
            case R.id.btnCamera:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
        }
    }
```
 **case R.id.btnGallery:**
startActivity(intent) 로 할 경우, 갤러리에서 사진을 선택 후 돌려 받을 방법이 없다.
-> startActivityForResult 를 사용해야 한다.

**startActivityForResult**
Intent 2번째 인자 : 갤러리를 띄울 앱을 선택하라는 알림창의 title을 적어주는 것.

2번째 인자 : 갤러리 flag

***
갤러리 실행 후 돌아오면 호출 되는 함수.
```java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case 100:
                    Uri imageUri = data.getData();
                    Log.i("Gallery","imageUri========================="+imageUri.getPath());
                    imageView.setImageURI(imageUri);
                    break;
            }
        }
    }
```
이미지 뷰에 갤러리에서 선택한 이미지를 띄워준다.

***

카메라 실행 부분

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if(resultCode == RESULT_OK){
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
```
안드로이드 롤리팝 이하 버전은 위와 같이 작성하면 작동한다.
그러나 이상 버전부터는 추가로 작성해야할 코드가 있다.
-> 분기를 나눠서 버전별로 처리를 해줘야함.

***
모든 버전에 작동할 수 있도록 처리 해보자.
```java
        <!-- 사진을 저장하기 위한 파일에 대한 권한을 획득하기 위한 설정 -->
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <!-- resource 파일을 res/xml 폴더안에 생성 -->
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_path"/>
        </provider>
```
(롤리팝 이상 버전은 provider 를 사용해야한다. (contentProvider 랑은 다른 것)
먼저 manifest에 사진을 저장하기 위한 파일에 대한 권한을 획득하기 위한 설정을 해야한다.
xml 폴더가 없으면 생성.
(안드로이드 메니페스트 프로바이더 검색)

authorities = "패키지명.provider" 로 해야하지만 그래들 속성에서 불러와서 위와 같이 표기할 수 있다.

```java
<paths>
    <!-- path = /External Storage/CameraN 가 된다 -->
    <!-- name = content:// 로 시작하는 uri 주소체계의 suffix가 된다. -->

    <external-path name = "Camera" path = "CameraN"/>
</paths>
```
리소스 파일을 저장할 경로 만들기. res - xml - file_path 만들기

* Camera : Uri 명. (안드로이드에서 자원을 access 하기 위한 프로토콜 명)
* CameraN : 물리 경로의 실제 디렉토리 이름. 
* 똑같이 지어줘도 상관없다.
* 디렉토리 생성 시에는 path 이름으로 생성.

***

takePhoto( ) 메소드
```java
Uri fileUri = null;
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            File photoFile = null;
            try {
                photoFile = createFile();
                if(photoFile != null){
                    // 마시멜로 이상 버전은 파일 프로바이더를 통해 권한을 획득
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                        // 롤리팝 버전은 권한 없이 획득
                    } else {
                        fileUri = Uri.fromFile(photoFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                }
            }catch(Exception e){
                Toast.makeText(getBaseContext(), "사진파일 저장을 위한 임시파일을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

        }else { // 롤리팝 미만 버전에서만 바로 실행
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }
```
롤리팝 미만 버전에서만 바로 실행 시키고,  


롤리팝 이상 버전 부터는 카메라 파일을 저장할 파일을 미리 생성을 해야한다.
-> 파일 생성 메소드를 따로 정의한다 ( createFile( ) )

권한 획득은 마시멜로 버전과 롤리팝 버전이 다르기 때문에 분기안에 권한 획득 분기를 또 만들어야 한다.

* 롤리팝 이상 버전에서
	* intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
   startActivityForResult(intent, Const.Camera.REQ_CAMERA);
* 롤리팝 미만 버전에서
	* startActivityForResult(intent, Const.Camera.REQ_CAMERA);

둘은 차이점이 있는데,
롤리팝 미만 버전에서는 사진의 Uri 를 Intent에 담아서 onActivityResult( ) 의 인자로 넘겨주고, 
롤리팝 이상 버전에서는 내가 넘겨준 fileUri 에다가 직접 세팅을 해준다. 
fileUri -> 내가 찍은 사진의 결과 값 Uri가 저장되있음.

***

createFile( ) 메소드

```java
 private File createFile(){
        String tempFileName = "TEMP_" + System.currentTimeMillis();
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        if(!tempDir.exists()){
            tempDir.mkdirs();

		--- 실제 파일을 만드는 부분 추가 해야됨 ---
        }
    }
```

mkdirs : 중간에 없는 경로 까지 다 생성해준다.

파일명을 만들었으면 실제 파일이 생성된 것이 아니라 이름만 생긴 것이다.

실제 파일을 만드는 부분을 보자

```java
// 실제 임시파일을 생성
        File tempFile = File.createTempFile(
                tempFilename,
                ".jpg",
                tempDir
        );
        return tempFile;
```
실제 임시 파일을 생성하는 코드를 추가하여 createFile( ) 메소드 완성.

***

```java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;
                // 롤리팝 미만 버전에서는 data 인텐트에 찍은 사진의 uri 가 담겨온다.
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    imageUri = data.getData();
                }else{
                    imageUri = fileUri;
                }

                imageView.setImageURI(imageUri);
            }
        }
    }
```
