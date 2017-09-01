package jihoon.java.com.sensor;

import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelrometer;
    public Graph mGraph, mGraphy, mGraphz;
    public TextView textX, textY, textZ;
    public Sensor gravity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGraph = (Graph) findViewById(R.id.graph);
        mGraphy = (Graph) findViewById(R.id.graphy);
        mGraphz = (Graph) findViewById(R.id.graphz);
/*        textX = (TextView) findViewById(R.id.textX);
        textY = (TextView) findViewById(R.id.textY);
        textZ = (TextView) findViewById(R.id.textZ);*/
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        accelrometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //gravity = manager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        //accelrometer = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        /*for(Sensor s : sensors){
            StringBuilder sb = new StringBuilder();
            sb.append(s.getName()).append("\n");
            sb.append(s.getType()).append("\n");
            sb.append(s.getVendor()).append("\n");
            sb.append(s.getMinDelay()).append("\n");
            sb.append(s.getResolution()).append("\n\n");
            Log.e("SensorInfo", sb.toString());*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accelrometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        Log.e("accelometer===", Arrays.toString((sensorEvent.values)));
        mGraph.setPoint((int) x * 10);
        mGraph.setBackgroundColor(Color.BLACK);
        mGraphy.setPoint((int) y * 10);
        mGraphy.setBackgroundColor(Color.GRAY);
        mGraphz.setPoint((int) z * 10);
        mGraphz.setBackgroundColor(Color.DKGRAY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
