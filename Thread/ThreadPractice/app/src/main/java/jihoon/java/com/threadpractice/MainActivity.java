package jihoon.java.com.threadpractice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editTxtUrl, editTxtRegex;
    Button btnCrawl;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxtUrl = (EditText) findViewById(R.id.url);
        editTxtRegex = (EditText) findViewById(R.id.regex);
        btnCrawl = (Button) findViewById(R.id.btnCrawl);
        txtResult = (TextView) findViewById(R.id.result);
    }

    public void onClick(View view){
        String url = editTxtUrl.getText().toString();
        String regex = editTxtRegex.getText().toString();

    }

    //
    AsyncTask a = new AsyncTask<String, String, String>(){
        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {   // 넘어가는 인자 타입에 뭐가 있는지 확인해보면, String 타입과 프로토콜 타입이 있다. 그에 따른 분기문 작성한 것임.
                URL url = new URL(strings[0].contains("http")? strings[0] : "http://"+strings[0]); // url
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
/*                    PrintWriter pw = new PrintWriter(connection.getOutputStream());
                    String[] protocol = {"GET /books/book/3 HTTP/1.1",
                            "Host: localhost:9200",
                            "Cache-Control: no-cache",
                            "Postman-Token : asgasgasgasgsasagsda",
                            ""};
                    for(String s : protocol){
                        pw.println(s);
                    }*/

                if(isCancelled()){
                    return "Canceled";
                }
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder sb = new StringBuilder();
                while(scanner.hasNext()){
                    sb.append(scanner.next());
                }
                scanner.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {  // -> 메인스레드에서 실행 * 그러므로 ui변경이 가능하다.
            txtResult.setText(s);
        }

    }.execute(url, regex);     //strings[0] = url (.execute 에서 넘기는 인자 순으로 들어간다.) ,
}
