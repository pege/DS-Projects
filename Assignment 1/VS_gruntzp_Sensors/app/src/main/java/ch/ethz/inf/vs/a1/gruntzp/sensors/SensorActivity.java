package ch.ethz.inf.vs.a1.gruntzp.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

import static android.R.attr.value;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorM;
    private Sensor currentSensor;
    private TextView tv;
    private String sensorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("Position");
        sensorName = extras.getString("sensorName");


        tv = (TextView) findViewById(R.id.textView);
        tv.setText(sensorName + " (Button Nr.: " + Integer.toString(position) + ")");

        sensorM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        int[] const_sensors = new int[] {Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_GRAVITY,
                                        Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LIGHT, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_PRESSURE,
                                        Sensor.TYPE_PROXIMITY, Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_ROTATION_VECTOR};
        assert (position < const_sensors.length);
        currentSensor = sensorM.getDefaultSensor(const_sensors[position]);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float val = event.values[0];
        tv.setText(sensorName + ": " + Float.toString(val));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        tv.setText("Accuracy changed");

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorM.registerListener(this, currentSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorM.unregisterListener(this);
    }
}
