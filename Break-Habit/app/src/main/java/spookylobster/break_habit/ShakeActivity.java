package spookylobster.break_habit;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.TextView;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SensorManager;
    private Sensor Accelerometer;
    private float previousZ = 0;
    private Integer shakeCounter = 10;
    private TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        SensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Accelerometer = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorManager.registerListener(this, Accelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        counter = (TextView) findViewById(R.id.shake_counter);
        counter.setText(shakeCounter.toString());


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float z = sensorEvent.values[2];

            if (z != previousZ) {
                previousZ = z;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}