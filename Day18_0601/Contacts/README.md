# Runtime Permission

![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/Permission.PNG)

**1. Runtime Permission**

�ȵ���̵�� ���ø�ο�(6.0) Api Level 23 �̻� ���ʹ� ����ڰ� ���� ������ �ʿ��� ����� ������ ��, ����ڷ� �Ͽ��� �ش� ������ �ۿ� ��� �� ������ ����,
�����ڰ� �ƴ� ����ڰ� �ڽ��� ����̽��� ���� ������ �����ϴ� ���·� ���� ���� ������ �ٲ�.  ->  �޴��佺Ʈ���� ���� ��ġ���Ѱ� �ٸ��� �ҽ��ڵ�� �ۼ��ؾ� �Ѵ�.

**2. ���� ȹ�� ó��**

1) ���� ȹ���ϱ� �� ���� ��ȿ�� üũ
```
checkSelfPermission(String) != packageManager.PERMISSION_GRANTED
```
���� ���� Ư�� ������ ���� �ִ��� Ȯ�� ����

2) ������ �ʿ��� ��� ó��
```
shouldShowRequestPermissionRationale(String)
```
* �����൵ �Ǵµ� ����ڰ� �ʿ��� ���ѿ� ���� ������ ���ִ� ��.
* ���� ȹ���� �ʿ��� ������ �����ؾ� �Ѵٸ� ���� �ɼ��� �߰��Ͽ� ���� ó���� ����.
* ����ڰ� ������ ���� ��û�� �ź��� ��쿡 true ��ȯ.
* �� ���, ���� ��û�� ���� ��ȭâ���� '�ٽ� ���� �ʱ�' üũ�ڽ��� �Բ� ǥ�õ�.
* ����ڰ� �̸� �����ϸ� ���Ŀ� ���� requestPermissions �޼��带 ȣ���ص� ���� ��û ��ȭâ�� ǥ�õ��� �ʰ�, �ٷ� ����ڰ� �ش� ������ �ź��� ���� �����ϰ� �ݹ� �Լ��� ȣ��ȴ�.

3) ���� ȹ���� ���� API
```
Activity.requestPermissions(String[], int)
```
���� ���� �� Group�� permission 2������ ���������� ���� �� �ִ�.
�ѹ��� 1���� �ƴ� String[] �迭�� �Ѱ� �� ���� �ʿ��� permission�� �� ���� ȹ���� �� �ִ�.

4) ���ó��
```
onRequestPermissionResult(int, String[], int[])
```
* 3)���� ����ڿ��� ����ȹ�濡 ���� ��� ������ �� ����� ���� ����ڰ� ���� Ȥ�� ������ ���ؼ� ������ �ϸ� ���ó�� �޼ҵ�(onRequestPermissionResult)�� ������ �ȴ�.
* ���� ȹ�濡 ���� ����/���п� ���� ������ ���� callback.
* �Լ� ������ �迭�� ���޵ǹǷ� �ʿ��� �۹̼��� �� �޾������� Ȯ���Ͽ� ���� ó���� ����.
 
***
 
#ContentProvider & ContentResolver 

![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/ContentProvider_Resolver.PNG)

�ȵ���̵�� �ٸ� ���ø����̼ǿ��� Ư�� ���ø����̼��� �����ͺ��̽��� ���� �����ϴ� ���� �Ұ���.
�׷��� ����Ʈ ���ι��̴�(Content Provider)�� ����Ѵ�.

**ContentProvider**

���ø����̼� ���� �����ͺ��̽��� �ٸ� ���ø����̼��� ����� �� �ִ� ��θ� �����Ѵ�.

**ContentResolver**

����Ʈ ���ι��̴��� ����Ͽ� �ȵ���� �ý����� ���� �������̳� �̵�� � ���� ���������µ�, �� ����Ʈ ���ι��̴��� �����ϱ� ���ؼ� ����ϴ� ���� ����Ʈ �������̴�.

����Ʈ �������� ����Ʈ ���ι��̴��� �ּҸ� ���� �ش� ����Ʈ ���ι��̴��� �����Ͽ� ����Ʈ ���ι��̴��� �����Ϳ� ������ �� �ֵ��� ���ִ� ����. -> ������ ���� �ٸ�

URI �ּҸ� �ȴٸ� � ������ ������ �����ϴ�.
���⼭ URI �� �ȵ���̵� �ý��� �ڿ��� �ּ�ü�踦 ����.

```java
public List<Data> getContacts(){
        // �����ͺ��̽� Ȥ�� content resolver�� ���� ������ �����͸� ������
        // ������ ����Ҹ� ���� �����Ѵ�.

        List<Data> datas = new ArrayList<>();  // ArrayList -> List�� ���ٴ� ���� �������� �����ϰ� �ִ�?

        // ������ Database ���� ��
        // ��ȭ��ȣ�ο� �̹� ������� �ִ� Content Provider �� ����
        // �����͸� ������ �� �ִ�.

        ContentResolver resolver = getContentResolver();  // ī�忡�� �ּҷ� �������� �� ó��.

        // 1. ������ ������ URI(�ڿ��� �ּ�)�� ����
        //  phonUri -> ���̺��� �̸��̶�� ����.
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        // �̸� ���� �� Ŭ����

        // URI���� ���� �����Ͱ� ��� �ֱ� ������
        // 2. �����Ϳ��� ������ �÷����� ����
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 3. Content Resolver�� ������ ������ �����͸� �����´�.
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null); // -> �迭ó�� ���� ����ִ�?
        // 4. �ݺ����� ���� cursor�� ����ִ� �����͸� �ϳ��� �����Ѵ�.
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 ���� ������ ���������� �÷������� cursor �ִ� �ε��� ���� ��ȸ�ϰ�
                int idIndex = cursor.getColumnIndex(projections[0]);
                // 4.2 �ش� index�� ����ؼ� �������� �����´�.
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(nameIndex);

                int telIndex = cursor.getColumnIndex(projections[2]);
                String tel = cursor.getString(telIndex);

                // 5. ���� ������ ������ Ŭ������ ����ش�.
                Data data = new Data();
                data.setId(id);
                data.setName(name);
                data.setTel(tel);

                // 6. �������� ��ü�� ���� �� �ִ� ����ҿ� �����Ѵ�.
                datas.add(data);
            }
        }
        return datas;
    }
}
```
��ȭ��ȣ�ο��� �̹� ContentProvider �� ���� ������ ���� �� �� �ִ� ȯ���� ������ �ִ� ��.
���� ContentResolver �� ���� �� �����Ϳ� �����Ͽ� �����͸� �������� ���̴�.

���� ������ �� ���� �����͸� �����ϰ��� �Ѵٸ� ����Ʈ ���ι��̴��� ��������.

Q.1 �ڵ����� �⺻ �۵��� ��� ���ι��̴��� �������ֳ�?
A. �ƴ�. �����͸� �������� �ʴ� ���� ���� �ʿ� ����.

***

```java
Cursor cursor = resolver.query(phoneUri, projections, null, null, null);
```
![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/projec.PNG)

1��° ���� : �ּҷ��� �ּ� ��<br>
2��° ���� : �����͸� ������ ����<br>
3,4,5 ��° : ���ǽ� ( ���� ��)

***

```java
while(cursor.moveToNext()){
```
![](https://github.com/jjunji/Android/blob/master/Day18_0601/image/projec2.PNG)

Ŀ���� ���پ� �̵�

������ �� ������ data�� ID Name Number�� ���� ���̰� �� ���� �� �ؿ� �� �ؿ� �� �ؿ� �� ���� �̵�.
