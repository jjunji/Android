# HttpUrlConnection
***

��Ʈ��ũ ����� Main Thread���� �۾� �� �� ������ ���� �Ǿ� �ִ�.
-> Sub Thread �̿� or AsyncTask �̿�
***

getData( ) �޼ҵ� : �����͸� �������� �Լ�
```java
// �����͸� ������ ������ ������ String�̴�. -> return���� String
    // ���ڷ� ���� url�� ��Ʈ��ũ�� ���� �����͸� �������� �Լ�
    public String getData(String url) throws Exception{  // ��û�� ������ Exceptionó���� ����.
        String result = "";

        // ��Ʈ�� ó��
        // 1. ������ ����(���� url�� ���� ����) , ��ûó��(Request)
        // 1.1 URL ��ü �����
        URL serverUrl = new URL(url);
        // 1.2 ���� ��ü ���� -> DBHelper�� ����� ����.
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();  // url ��ü���� ������ ������.
        // 1.3 http method ����
        con.setRequestMethod("GET");

        // 2. ����ó�� Response
        // 2.1 �����ڵ� �м�
        int responseCode = con.getResponseCode();
        // 2.2 �������� ����ó��
        if(responseCode == HttpURLConnection.HTTP_OK){  // �������� �ڵ� ó��
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;

            while( (temp = br.readLine()) != null){
                result += temp;
            }
        // 2.3 ������ ���� ����ó��
        } else{
            // ���� ȣ�������� Exception�� ���� �Ѱ��� ��
            Log.e("Network", "error_code"+ responseCode);
        }

        return result;
    }
```
��Ʈ��ũ ����� ���� �����͸� �������� �� ������ ������ String Ÿ���̴�. -> getData( ) �޼ҵ��� ��ȯ���� String
json / xml �� �������. -> json, xml ��ü�� �����ϴ� ���� Parsing�� �߱� ������ ������ ��.

***
```java
throws Exception
try - catch
```
**try - catch** �� : try~catch �� ���ΰ� �ִ� �κп��� ��� ó���� �ϰڴٴ� ��.
**throw Exception** : ���� ��ü�� ȣ���� ������ �Ѱ� �޴´�.
-> ȣ�� ������ UI�� ��Ʈ�� �ϹǷν� ���� �޽����� ���� ��� ó���� ���������� ǥ��ȭ �� �� ����. (Toast,  �˾� ��� )
-> ȣ�� ������ try - catch ó��

***
newTask(String url) : �����带 ó���ϴ� �Լ�
```java
public void newTask(String url){
        new AsyncTask<String, Void, String>(){
            // ��׶��� ó�� �Լ�
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try{
                    // getData�Լ��� �����´�.
                    result = getData(params[0]);
                    Log.i("Network",result);
                    // result�� ȭ�鿡 ����Ϸ���
                    // ���� �����忡�� ���� ������� ���� �Ѱ������ -> Asynctask
                }catch (Exception e){
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }

        }.execute(url);
    }
```
execute( ) �޼ҵ尡 doInBackground( ) �Լ��� ���� ȣ���Ѵ�. ���ڷ� url(String) �� �ѱ�Ƿ� AsyncTask�� ù��° ���׸��� String Ÿ���� �ȴ�.
onPostExecute( ) �Լ��� ���޵Ǵ� result �� String Ÿ�� �̹Ƿ� ����° ���׸��� String Ÿ���� �ȴ�.

ȭ�鿡 ����� ����Ϸ���..
doInBackground( ) �� ���� SubThread���� ó���� ����� ���� ������� �Ѱܾ��Ѵ�.
-> onPostExecute(result)