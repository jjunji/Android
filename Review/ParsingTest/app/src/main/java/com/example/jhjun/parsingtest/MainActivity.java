package com.example.jhjun.parsingtest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity {
    // 사용자 정의 함수로 블루투스 활성 상태의 변경 결과를 App으로 알려줄때 식별자로 사용됨 (0보다 커야함)
    static final int REQUEST_ENABLE_BT = 10;
    static final int MESSAGE_WRITE = 1 ;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트.
    BluetoothAdapter mBluetoothAdapter;
    /**
     * BluetoothDevice 로 기기의 장치정보를 알아낼 수 있는 자세한 메소드 및 상태값을 알아낼 수 있다.
     * 연결하고자 하는 다른 블루투스 기기의 이름, 주소, 연결 상태 등의 정보를 조회할 수 있는 클래스.
     * 현재 기기가 아닌 다른 블루투스 기기와의 연결 및 정보를 알아낼 때 사용.
     */
    BluetoothDevice mRemoteDevie;
    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    char mCharDelimiter = '\n';


    Thread Thread_in = null;
    Thread Thread_out = null ;
    byte[] readBuffer;
    byte[] writeBuffer;
    int readBufferPosition;


    private static final String TAG = "MainActivity";

    // 날씨 rss 주소

    String urlAddr = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108";


    Context context = MainActivity.this;

    int seletion;


    String[] wf;
    String[] city;


    Button weatherBtn;
    Button alarm;


    TextView weatherView;
    TextView weatherView2;


    ImageView claer;
    ImageView partly_cloudy;
    ImageView mostly_cloudy;
    ImageView cloudy;
    ImageView rain;
    ImageView snow_rain;
    ImageView snow;


    TextView mEditReceive;

    ImageView image1;
    ImageView image2;
    ImageView image3;

    TextView Situation;


    Button ledonBtn;
    Button ledoffBtn;

    boolean led_checkOn = false;
    boolean led_checkOff = false;


    Vibrator vb ;
    long[] pattern = {500,500,500,500,500,500} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // 블루투스 송/수신 값
        mEditReceive = (TextView) findViewById(R.id.hum);
        image1 = (ImageView) findViewById(R.id.img01);
        image2 = (ImageView) findViewById(R.id.img02);
        image3 = (ImageView) findViewById(R.id.img03);
        Situation = (TextView) findViewById(R.id.situ);


        //led on/off
        ledonBtn = (Button) findViewById(R.id.led_on);
        ledonBtn.setOnClickListener(listener_ledon);

        ledoffBtn = (Button) findViewById(R.id.led_off);
        ledoffBtn.setOnClickListener(listener_ledoff);

        // 날씨 값
        weatherBtn = (Button) findViewById(R.id.weatherBtn);
        weatherBtn.setOnClickListener(listener);

        weatherView = (TextView) findViewById(R.id.weather);
        weatherView2 = (TextView) findViewById(R.id.weather_wf);

        claer = (ImageView) findViewById(R.id.icon_clear);
        partly_cloudy = (ImageView) findViewById(R.id.icon_partly_cloudy);
        mostly_cloudy = (ImageView) findViewById(R.id.icon_mostly_cloudy);
        cloudy = (ImageView) findViewById(R.id.icon_cloudy);
        rain = (ImageView) findViewById(R.id.icon_rain);
        snow_rain = (ImageView) findViewById(R.id.icon_snow_rain);
        snow = (ImageView) findViewById(R.id.icon_snow);


        // 알람

        alarm = (Button) findViewById(R.id.alarm);
        alarm.setOnClickListener(listener_alarm);
        vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);


        // 블루투스 활성화 시키는 메소드
        checkBluetooth();
    }


    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for (BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if (name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }



    //  connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄.
    //        실제 데이터 송수신을 위해서는 소켓으로부터 입출력 스트림을 얻고 입출력 스트림을 이용하여 이루어 진다.
    void connectToSelectedDevice(String selectedDeviceName) {
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        mRemoteDevie = getDeviceFromBondedList(selectedDeviceName);
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와 통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함.
            mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            // 데이터 송수신을 위한 스트림 얻기.
            // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            // 1. 데이터를 보내기 위한 OutputStrem
            // 2. 데이터를 받기 위한 InputStream
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();


            // 데이터 수신 준비.
            beginReadForData();

            // 데이터 송신 준비.
            beginWriteForData();


        } catch (Exception e) { // 블루투스 연결 중 오류 발생
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            // finish();  // App 종료
        }
    }


    // 끄는 버튼

    View.OnClickListener listener_ledoff = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            led_checkOff = true ;

            ledonBtn.setVisibility(View.VISIBLE);
            ledoffBtn.setVisibility(View.GONE);

        }

    };

    // 켜는 버튼
    View.OnClickListener listener_ledon = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            led_checkOn = true ;

            ledonBtn.setVisibility(View.GONE);
            ledoffBtn.setVisibility(View.VISIBLE);


        }

    };


    // 데이터 수신(쓰레드 사용 수신된 메시지를 계속 검사함)
    void beginReadForData() {
        final Handler handler = new Handler();

        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.

        // 문자열 수신 쓰레드.
        Thread_in = new Thread(new Runnable() {
            @Override

            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = mInputStream.available();   // 수신 데이터 확인
                        if (byteAvailable > 0) {                        // 데이터가 수신된 경우.
                            byte[] packetBytes = new byte[byteAvailable];
                            // read(buf[]) : 입력스트림에서 buf[] 크기만큼 읽어서 저장 없을 경우에 -1 리턴.
                            mInputStream.read(packetBytes);
                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == mCharDelimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];

                                    //final byte[] bytes = {encodedBytes[0],encodedBytes[1],encodedBytes[2],encodedBytes[3]};


                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    // final int soil = new BigInteger(encodedBytes).intValue() ;
                                    final String data = new String(encodedBytes, "US-ASCII");


                                    readBufferPosition = 0;


                                    handler.post(new Runnable() {

                                        // 수신된 문자열 데이터에 대한 처리.
                                        @Override
                                        public void run() {
                                            mEditReceive.setText(data);

                                            String aa = mEditReceive.getText().toString();
                                            double soil = Double.parseDouble(aa);

                                            // soil = Integer.parseInt(mEditReceive.getText().toString()) ;
                                            //int soil = new Integer(mEditReceive.getText().toString()) ;


                                            if (soil <= 400) {
                                                Situation.setText("이미 충분해요 물 그만!");
                                                image1.setVisibility(View.VISIBLE);
                                                image2.setVisibility(View.INVISIBLE);
                                                image3.setVisibility(View.INVISIBLE);

                                                vb.cancel();
                                            }

                                            else if (400 < soil && soil <= 900) {
                                                Situation.setText("파릇파릇 행복해요♥");
                                                image1.setVisibility(View.INVISIBLE);
                                                image2.setVisibility(View.VISIBLE);
                                                image3.setVisibility(View.INVISIBLE);

                                                vb.cancel();
                                            }

                                            else if (900 < soil) {

                                                Situation.setText("건조해! 물이 필요해요!!");
                                                image1.setVisibility(View.INVISIBLE);
                                                image2.setVisibility(View.INVISIBLE);
                                                image3.setVisibility(View.VISIBLE);

                                                // 진동알림
                                                vb.vibrate(pattern,0);
                                            }


                                        }

                                    });

                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Toast.makeText(getApplicationContext(), "데이터 수신 중 오류가 발생 했습니다.", Toast.LENGTH_LONG).show();
                        finish();            // App 종료.
                    }
                }

            }

        });
        Thread_in.start();

    }



    // 데이터 송신
    void beginWriteForData() {
        final Handler handler = new Handler();

        // 문자열 송신 쓰레드.
        Thread_out = new Thread(new Runnable() {
            @Override

            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.

                while (!Thread.currentThread().isInterrupted()) {
                    // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.

                    handler.post(new Runnable() {

                        // 수신된 문자열 데이터에 대한 처리.
                        @Override
                        public void run() {

                            if(led_checkOn) {
                                try {
                                    mOutputStream.write(0);
                                    led_checkOn = false;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            if(led_checkOff){
                                try {
                                    mOutputStream.write(1);
                                    led_checkOff =false;

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                        }

                    });

                }
            }
        });
        Thread_out.start();

    }


    // 블루투스 지원하며 활성 상태인 경우.
    void selectDevice() {
        // 블루투스 디바이스는 연결해서 사용하기 전에 먼저 페어링 되어야만 한다
        // getBondedDevices() : 페어링된 장치 목록 얻어오는 함수.
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if (mPariedDeviceCount == 0) { // 페어링된 장치가 없는 경우.
            Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            finish(); // App 종료.
        }
        // 페어링된 장치가 있는 경우.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");

        // 각 디바이스는 이름과(서로 다른) 주소를 가진다. 페어링 된 디바이스들을 표시한다.
        List<String> listItems = new ArrayList<>();
        for (BluetoothDevice device : mDevices) {
            // device.getName() : 단말기의 Bluetooth Adapter 이름을 반환.
            listItems.add(device.getName());
        }
        listItems.add("취소");  // 취소 항목 추가.


        // CharSequence : 변경 가능한 문자열.
        // toArray : List형태로 넘어온것 배열로 바꿔서 처리하기 위한 toArray() 함수.
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        // toArray 함수를 이용해서 size만큼 배열이 생성 되었다.
        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getApplicationContext(), "연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
                }
            }

        });

        builder.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();
        alert.show();
    }


    void checkBluetooth() {
        /**
         * getDefaultAdapter() : 만일 폰에 블루투스 모듈이 없으면 null 을 리턴한다.
         이경우 Toast를 사용해 에러메시지를 표시하고 앱을 종료한다.
         */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {  // 블루투스 미지원
            Toast.makeText(getApplicationContext(), "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
            finish();  // 앱종료
        } else { // 블루투스 지원
            /** isEnable() : 블루투스 모듈이 활성화 되었는지 확인.
             *               true : 지원 ,  false : 미지원
             */
            if (!mBluetoothAdapter.isEnabled()) { // 블루투스 지원하며 비활성 상태인 경우.
                Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // REQUEST_ENABLE_BT : 블루투스 활성 상태의 변경 결과를 App 으로 알려줄 때 식별자로 사용(0이상)
                /**
                 startActivityForResult 함수 호출후 다이얼로그가 나타남
                 "예" 를 선택하면 시스템의 블루투스 장치를 활성화 시키고
                 "아니오" 를 선택하면 비활성화 상태를 유지 한다.
                 선택 결과는 onActivityResult 콜백 함수에서 확인할 수 있다.
                 */
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else // 블루투스 지원하며 활성 상태인 경우.
                selectDevice();
        }
    }


    // onDestroy() : 어플이 종료될때 호출 되는 함수.
    //               블루투스 연결이 필요하지 않는 경우 입출력 스트림 소켓을 닫아줌.
    @Override
    protected void onDestroy() {
        try {
            Thread_in.interrupt(); // 데이터 수신 쓰레드 종료
            mInputStream.close();
            mSocket.close();
        } catch (Exception e) {
        }
        super.onDestroy();
    }


    // onActivityResult : 사용자의 선택결과 확인 (아니오, 예)
    // RESULT_OK: 블루투스가 활성화 상태로 변경된 경우. "예"
    // RESULT_CANCELED : 오류나 사용자의 "아니오" 선택으로 비활성 상태로 남아 있는 경우  RESULT_CANCELED

    /**
     * 사용자가 request를 허가(또는 거부)하면 안드로이드 앱의 onActivityResult 메소도를 호출해서 request의 허가/거부를 확인할수 있다.
     * 첫번째 requestCode : startActivityForResult 에서 사용했던 요청 코드. REQUEST_ENABLE_BT 값
     * 두번째 resultCode  : 종료된 액티비티가 setReuslt로 지정한 결과 코드. RESULT_OK, RESULT_CANCELED 값중 하나가 들어감.
     * 세번째 data        : 종료된 액티비티가 인테트를 첨부했을 경우, 그 인텐트가 들어있고 첨부하지 않으면 null
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // startActivityForResult 를 여러번 사용할 땐 이런 식으로 switch 문을 사용하여 어떤 요청인지 구분하여 사용함.
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) { // 블루투스 활성화 상태
                    selectDevice();
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 비활성화 상태 (종료)
                    Toast.makeText(getApplicationContext(), "블루투스를 사용할 수 없어 프로그램을 종료합니다", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }







    // 지역선택 버튼 클릭 시,

    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            parser();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final LinearLayout linear = (LinearLayout) View.inflate(context, R.layout.activity_menu, null);
            builder.setView(linear);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int whichButton) {

                    Spinner spinner = (Spinner) linear.findViewById(R.id.spinner);

                    seletion = spinner.getSelectedItemPosition();

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        // 선택 했을 때
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            seletion = position;


                        }

                        // 선택 하지 않았을 때
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });


                    // 지역 선택 후

                    // '서울' 선택 시
                    if (seletion == 0) {
                        parser_choice(0);
                    }

                    // '인천' 선택 시
                    else if (seletion == 1) {
                        parser_choice(1);
                    }

                    // '수원' 선택 시
                    else if (seletion == 2) {
                        parser_choice(2);
                    }

                    // '파주' 선택 시
                    else if (seletion == 3) {
                        parser_choice(3);
                    }

                    // '춘천' 선택 시
                    else if (seletion == 4) {
                        parser_choice(4);
                    }

                    // '원주' 선택 시
                    else if (seletion == 5) {
                        parser_choice(5);
                    }

                    // '강릉' 선택 시
                    else if (seletion == 6) {
                        parser_choice(6);
                    }

                    // '청주' 선택 시
                    else if (seletion == 7) {
                        parser_choice(7);
                    }

                    // '대전' 선택 시
                    else if (seletion == 8) {
                        parser_choice(8);
                    }

                    // '서산' 선택 시
                    else if (seletion == 9) {
                        parser_choice(9);
                    }

                    // '세종' 선택 시
                    else if (seletion == 10) {
                        parser_choice(10);
                    }

                    // '전주' 선택 시
                    else if (seletion == 11) {
                        parser_choice(11);
                    }

                    // '군산' 선택 시
                    else if (seletion == 12) {
                        parser_choice(12);
                    }

                    // '광주' 선택 시
                    else if (seletion == 13) {
                        parser_choice(13);
                    }


                    // '목포' 선택 시
                    else if (seletion == 14) {
                        parser_choice(14);
                    }

                    // '여수' 선택 시
                    else if (seletion == 15) {
                        parser_choice(15);
                    }

                    // '대구' 선택 시
                    else if (seletion == 16) {
                        parser_choice(16);
                    }

                    // '안동' 선택 시
                    else if (seletion == 17) {
                        parser_choice(17);
                    }

                    // '포항' 선택 시
                    else if (seletion == 18) {
                        parser_choice(18);
                    }

                    // '부산' 선택 시
                    else if (seletion == 19) {
                        parser_choice(19);
                    }

                    // '울산' 선택 시
                    else if (seletion == 20) {
                        parser_choice(20);
                    }

                    // '창원' 선택 시
                    else if (seletion == 21) {
                        parser_choice(21);
                    }

                    // '제주' 선택 시
                    else if (seletion == 22) {
                        parser_choice(22);
                    }

                    // '서귀포' 선택 시
                    else if (seletion == 23) {
                        parser_choice(23);
                    }

                }
            });

            builder.show();

        }
    };


    private void parser() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {

            URL url = new URL(urlAddr);
            InputStream input = url.openStream();

            InputStreamReader inputre = new InputStreamReader(input);
            BufferedReader buffre = new BufferedReader(inputre);
            StringBuffer strbuff = new StringBuffer();
            String line = null;

            XmlPullParserFactory factory = null;
            XmlPullParser xmlPullParser = null;


            try {
                factory = XmlPullParserFactory.newInstance();
                xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(buffre);

                int eventType = xmlPullParser.getEventType();

                boolean check_data = false;
                boolean check_city = false;
                boolean check_wf = false;
                boolean check_location = false;

                String city_all = ""; // 지역
                String wf_all = ""; // 날씨


                while (eventType != XmlPullParser.END_DOCUMENT) {


                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            Log.i(TAG, "Start Document");
                            break;

                        case XmlPullParser.START_TAG:
                            Log.i(TAG, "Start TAG : " + xmlPullParser.getName());

                            String aa = xmlPullParser.getName();


                            if (aa.compareTo("location") == 0) {
                                check_location = true;
                            }

                            if (aa.compareTo("city") == 0) {
                                check_city = true;

                                city_all += xmlPullParser.nextText() + "|";
                            }

                            if (aa.compareTo("data") == 0) {
                                check_data = true;
                            }

                            if (aa.compareTo("wf") == 0) {
                                check_wf = true;
                                if (check_location && check_data && check_wf) {
                                    wf_all += xmlPullParser.nextText() + "|";
                                }
                            }


                            break;

                        case XmlPullParser.TEXT:
                            Log.i(TAG, "TEXT : " + xmlPullParser.getText());


                            aa = xmlPullParser.getName();

                            if (check_location && check_data && check_wf) {
                                check_location = false;
                            }


                            break;


                        case XmlPullParser.END_TAG:

                            Log.i(TAG, "End TAG : " + xmlPullParser.getName());

                            aa = xmlPullParser.getName();


                            if (aa.compareTo("location") == 0) {
                                check_location = true;
                            }

                            if (aa.compareTo("city") == 0) {
                                check_city = false;

                            }

                            if (aa.compareTo("data") == 0) {
                                check_data = false;
                            }

                            if (aa.compareTo("wf") == 0) {
                                check_wf = false;
                            }


                            break;


                    }

                    eventType = xmlPullParser.next();


                }

                // 정보 받아오기 완료!
                city = city_all.toString().split("\\|");
                // weatherView2.setText(city[22]);

                wf = wf_all.toString().split("\\|");
                //weatherView.setText(wf[3]);// 한 지역 당 15개 wf


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                {
                    try {
                        if (buffre != null)
                            buffre.close();

                        if (inputre != null)
                            inputre.close();

                        if (input != null)
                            input.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        return;
    }


    void parser_choice(int i) {
        weatherView.setText(wf[i]);
        weatherView2.setText(city[i]);

        if (wf[i].equals("맑음")) {
            claer.setVisibility(View.VISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 맑아요!" + "\n" + "화분을 창가에 놓아도 좋아요♥", Toast.LENGTH_LONG).show();

        }

        else if (wf[i].equals("구름조금")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.VISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 구름이 조금 있어요!" + "\n" +
                    "화분을 창가에 놓아도 괜찮을 거에요 :D", Toast.LENGTH_LONG).show();
        } else if (wf[i].equals("구름많음")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.VISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 구름이 많아요!" + "\n" +
                    "광합성은 다음번에 할까요? :3", Toast.LENGTH_LONG).show();
        } else if (wf[i].equals("흐림")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.VISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 흐려요!" + "\n" +
                    "광합성은 다음번에 할까요? :3", Toast.LENGTH_LONG).show();

        } else if (wf[i].equals("비") || wf[i].equals("흐리고 비")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.VISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 비가와요!!" + "\n" +
                    "화분을 창가에 두어선 안돼요!! :(", Toast.LENGTH_LONG).show();

        } else if (wf[i].equals("비/눈")||wf[i].equals("흐리고 비/눈")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.VISIBLE);
            snow.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 비와 눈이 내려요!!" + "\n" +
                    "화분을 다른곳에 둘까요? :O", Toast.LENGTH_LONG).show();

        } else if (wf[i].equals("눈")) {
            claer.setVisibility(View.INVISIBLE);
            partly_cloudy.setVisibility(View.INVISIBLE);
            mostly_cloudy.setVisibility(View.INVISIBLE);
            cloudy.setVisibility(View.INVISIBLE);
            rain.setVisibility(View.INVISIBLE);
            snow_rain.setVisibility(View.INVISIBLE);
            snow.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "이틀 뒤에는 눈이 내려요!!" + "\n" +
                    "화분을 창가에 두면 꽃이 추워요! :U", Toast.LENGTH_LONG).show();

        }
    }


    // 알람끄기 클릭 시


    View.OnClickListener listener_alarm = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            vb.cancel();
        }
    };




}
