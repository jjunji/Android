package jihoon.java.com.sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s : sensors){
            StringBuilder sb = new StringBuilder();
            sb.append(s.getName()).append("\n"); // 센서명
            sb.append(s.getType()).append("\n"); //
            sb.append(s.getVendor()).append("\n"); // 센서 칩을 만든 회사 출력
            sb.append(s.getMinDelay()).append("\n");
            sb.append(s.getResolution()).append("\n");
            Log.e("SensorInfo", sb.toString());
        }
    }
}
