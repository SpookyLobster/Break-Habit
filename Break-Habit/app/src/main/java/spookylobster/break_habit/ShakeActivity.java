package spookylobster.break_habit;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.widget.TextView;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SensorManager;
    private Sensor Accelerometer;
    private float previousZ = 0;
    private Integer shakeCounter = 50;
    private TextView counter;
    private long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        SensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Accelerometer = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorManager.registerListener(this, Accelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        counter = (TextView) findViewById(R.id.shake_counter);
        updateCounterText();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        long curTime = System.currentTimeMillis();
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float z = sensorEvent.values[1];
            System.out.print("Shake is " + z);
            if ((curTime - lastUpdate) > 100) {

                float speed = Math.abs(z - previousZ)/ (curTime - lastUpdate) * 10000;
                lastUpdate = curTime;

                if ( 500 < speed) {
                    shake();
                }
                previousZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void shake() {
        if (shakeCounter == 0){
            stopShakeActivity();
        }
        shakeCounter -= 1;
        updateCounterText();
    }

    public void updateCounterText() {
        counter.setText(shakeCounter.toString());
    }

    public void stopShakeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}