package spookylobster.break_habit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.widget.TextView;

// Source: https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SensorManager;
    private Sensor Accelerometer;
    private float previousZ = 0;
    SQLhandler handler = new SQLhandler(this,null,1);
    private Integer shakeCounter = 10;
    private TextView counter;
    private long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        shakeCounter = handler.getData("SetShake");
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

                if (1000 < speed) {
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
        shakeCounter -= 1;
        if (shakeCounter <= 0){
            stopShakeActivity();
        } else {
            updateCounterText();
        }
    }

    public void updateCounterText() {
        counter.setText(shakeCounter.toString());
    }

    public void stopShakeActivity(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}