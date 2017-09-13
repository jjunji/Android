# Camera

�ȵ���̵� ������ & ī�޶� ����ϱ�

MainActivity�� ��ư�� 2�� (������ �̵�, ī�޶� �̵�) �����,
�� ��ư�� ���� ������ �̵�, ī�޶� �̵��� �ǵ��� ������.

***

```java
        // ��ư ���
        btnCamera.setEnabled(false);
        btnGallery.setEnabled(false);
```
ó�� ��Ƽ��Ƽ ����� ��ư ���. (���� ȹ�� �Ŀ� ��� Ǯ��.)

```java
 @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        //1 ����üũ - Ư�������� �ִ��� �ý��ۿ� �����
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            // 2. ������ ������ ����ڿ� ������ �޶�� ��û
            String permissions[] = { Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA };
            requestPermissions(permissions ,REQ_PERMISSION); // -> ������ �䱸�ϴ� �˾��� ����� ȭ�鿡 ����ȴ�
        }
    }
```
���� üũ ��

* ������ ������ init( ) �޼ҵ� ����
* ������ ������ ��û

***

```java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 ����ڰ� ������ ����
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                init();
                // 3.2 ����ڰ� ���� ����
            }else{
                Toast.makeText(this, "���ѿ�û�� �����ϼž� ���� ����� �� �ֽ��ϴ�.", Toast.LENGTH_SHORT).show();
            }
        }
    }
```
������ ���� ��� ��û�� �ؼ� �׿� ���� ������

* ���� -> init( ) �޼ҵ� ����
*  ���� -> �佺Ʈ �޽���

***

```java
    private void init(){
        btnCamera.setEnabled(true);
        btnGallery.setEnabled(true);
    }
```
������ ������ ��ư�� ����� �� �ְ� �ȴ�.

***
������ �̵� / ī�޶� ���� 
```java
@Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnGallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult( Intent.createChooser(intent, "���� �����ϼ���") , 100);
                break;
            case R.id.btnCamera:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
        }
    }
```
 **case R.id.btnGallery:**
startActivity(intent) �� �� ���, ���������� ������ ���� �� ���� ���� ����� ����.
-> startActivityForResult �� ����ؾ� �Ѵ�.

**startActivityForResult**
Intent 2��° ���� : �������� ��� ���� �����϶�� �˸�â�� title�� �����ִ� ��.

2��° ���� : ������ flag

***
������ ���� �� ���ƿ��� ȣ�� �Ǵ� �Լ�.
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
�̹��� �信 ���������� ������ �̹����� ����ش�.

***

ī�޶� ���� �κ�

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ��û�ڵ� ����
        if(requestCode == Const.Camera.REQ_CAMERA){
            // ���ó�� ���� ����
            if(resultCode == RESULT_OK){
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
```
�ȵ���̵� �Ѹ��� ���� ������ ���� ���� �ۼ��ϸ� �۵��Ѵ�.
�׷��� �̻� �������ʹ� �߰��� �ۼ��ؾ��� �ڵ尡 �ִ�.
-> �б⸦ ������ �������� ó���� �������.

***
��� ������ �۵��� �� �ֵ��� ó�� �غ���.
```java
        <!-- ������ �����ϱ� ���� ���Ͽ� ���� ������ ȹ���ϱ� ���� ���� -->
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <!-- resource ������ res/xml �����ȿ� ���� -->
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_path"/>
        </provider>
```
(�Ѹ��� �̻� ������ provider �� ����ؾ��Ѵ�. (contentProvider ���� �ٸ� ��)
���� manifest�� ������ �����ϱ� ���� ���Ͽ� ���� ������ ȹ���ϱ� ���� ������ �ؾ��Ѵ�.
xml ������ ������ ����.
(�ȵ���̵� �޴��佺Ʈ ���ι��̴� �˻�)

authorities = "��Ű����.provider" �� �ؾ������� �׷��� �Ӽ����� �ҷ��ͼ� ���� ���� ǥ���� �� �ִ�.

```java
<paths>
    <!-- path = /External Storage/CameraN �� �ȴ� -->
    <!-- name = content:// �� �����ϴ� uri �ּ�ü���� suffix�� �ȴ�. -->

    <external-path name = "Camera" path = "CameraN"/>
</paths>
```
���ҽ� ������ ������ ��� �����. res - xml - file_path �����

* Camera : Uri ��. (�ȵ���̵忡�� �ڿ��� access �ϱ� ���� �������� ��)
* CameraN : ���� ����� ���� ���丮 �̸�. 
* �Ȱ��� �����൵ �������.
* ���丮 ���� �ÿ��� path �̸����� ����.

***

takePhoto( ) �޼ҵ�
```java
Uri fileUri = null;
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            File photoFile = null;
            try {
                photoFile = createFile();
                if(photoFile != null){
                    // ���ø�� �̻� ������ ���� ���ι��̴��� ���� ������ ȹ��
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                        // �Ѹ��� ������ ���� ���� ȹ��
                    } else {
                        fileUri = Uri.fromFile(photoFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                }
            }catch(Exception e){
                Toast.makeText(getBaseContext(), "�������� ������ ���� �ӽ������� ������ �� �����ϴ�.", Toast.LENGTH_SHORT).show();
                return;
            }

        }else { // �Ѹ��� �̸� ���������� �ٷ� ����
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }
```
�Ѹ��� �̸� ���������� �ٷ� ���� ��Ű��,  


�Ѹ��� �̻� ���� ���ʹ� ī�޶� ������ ������ ������ �̸� ������ �ؾ��Ѵ�.
-> ���� ���� �޼ҵ带 ���� �����Ѵ� ( createFile( ) )

���� ȹ���� ���ø�� ������ �Ѹ��� ������ �ٸ��� ������ �б�ȿ� ���� ȹ�� �б⸦ �� ������ �Ѵ�.

* �Ѹ��� �̻� ��������
	* intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
   startActivityForResult(intent, Const.Camera.REQ_CAMERA);
* �Ѹ��� �̸� ��������
	* startActivityForResult(intent, Const.Camera.REQ_CAMERA);

���� �������� �ִµ�,
�Ѹ��� �̸� ���������� ������ Uri �� Intent�� ��Ƽ� onActivityResult( ) �� ���ڷ� �Ѱ��ְ�, 
�Ѹ��� �̻� ���������� ���� �Ѱ��� fileUri ���ٰ� ���� ������ ���ش�. 
fileUri -> ���� ���� ������ ��� �� Uri�� ���������.

***

createFile( ) �޼ҵ�

```java
 private File createFile(){
        String tempFileName = "TEMP_" + System.currentTimeMillis();
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        if(!tempDir.exists()){
            tempDir.mkdirs();

		--- ���� ������ ����� �κ� �߰� �ؾߵ� ---
        }
    }
```

mkdirs : �߰��� ���� ��� ���� �� �������ش�.

���ϸ��� ��������� ���� ������ ������ ���� �ƴ϶� �̸��� ���� ���̴�.

���� ������ ����� �κ��� ����

```java
// ���� �ӽ������� ����
        File tempFile = File.createTempFile(
                tempFilename,
                ".jpg",
                tempDir
        );
        return tempFile;
```
���� �ӽ� ������ �����ϴ� �ڵ带 �߰��Ͽ� createFile( ) �޼ҵ� �ϼ�.

***

```java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ��û�ڵ� ����
        if(requestCode == Const.Camera.REQ_CAMERA){
            // ���ó�� ���� ����
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;
                // �Ѹ��� �̸� ���������� data ����Ʈ�� ���� ������ uri �� ��ܿ´�.
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
