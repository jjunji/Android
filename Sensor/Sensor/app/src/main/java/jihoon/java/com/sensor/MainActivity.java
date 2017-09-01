package jihoon.java.com.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sm;
    private Sensor s;

    private Graph mGraph;
    private Graph mGraphY;
    private Graph mGraphZ;
    private TextView mTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE); // 센서 서비스 가져오기
        //s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        s = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mGraph = (Graph) findViewById(R.id.mGraph);
        mGraphY = (Graph) findViewById(R.id.mGraph2);
        mGraphZ = (Graph) findViewById(R.id.mGraph3);

        mTxtView = (TextView) findViewById(R.id.mTxtView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.e("Sensor value called ========",Arrays.toString(event.values));
        mGraph.setPoint((int)x *100);
        mGraphY.setPoint((int)y *100);
        mGraphZ.setPoint((int)z *100);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
