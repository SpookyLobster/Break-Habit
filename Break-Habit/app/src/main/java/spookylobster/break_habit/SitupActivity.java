package spookylobster.break_habit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SitupActivity extends AppCompatActivity implements SensorEventListener {


    private TextView situp_tv;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mGravity;
    private float[] mGeomagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situp);

        // Keep the screen on
        // https://developer.android.com/training/scheduling/wakelock.html#screen
        // use the FLAG_KEEP_SCREEN_ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Grab the layout TextView
        situp_tv = (TextView) findViewById(R.id.tv_tilt);

        // Setup the sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        // Detect the window position
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                Toast.makeText(this, "Rotation 0", Toast.LENGTH_SHORT).show();
                break;
            case Surface.ROTATION_90:
                Toast.makeText(this, "Rotation 90", Toast.LENGTH_SHORT).show();
                break;
            case Surface.ROTATION_180:
                Toast.makeText(this, "Rotation 180", Toast.LENGTH_SHORT).show();
                break;
            case Surface.ROTATION_270:
                Toast.makeText(this, "Rotation 270", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Rotation unknown", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * Convert degrees to absolute tilt value between 0-100
     */
    private int degreesToPower(int degrees) {
        // Tilted back towards user more than -90 deg
        if (degrees < -90) {
            degrees = -90;
        }
        // Tilted forward past 0 deg
        else if (degrees > 0) {
            degrees = 0;
        }
        // Normalize into a positive value
        degrees *= -1;
        // Invert from 90-0 to 0-90
        degrees = 90 - degrees;
        // Convert to scale of 0-100
        float degFloat = degrees / 90f * 100f;
        return (int) degFloat;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d(TAG, "onSensorChanged()");
        if (event.values == null) {
            Toast.makeText(this, "event.values is null", Toast.LENGTH_SHORT).show();
            return;
        }
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
            default:
                Toast.makeText(this, "Unknown sensor type", Toast.LENGTH_SHORT).show();
                return;
        }
        if (mGravity == null) {
            Toast.makeText(this, "mGravity is null", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mGeomagnetic == null) {
            Toast.makeText(this, "mGeomagnetic is null", Toast.LENGTH_SHORT).show();
            return;
        }
        /*float R[] = new float[9];
        if (! SensorManager.getRotationMatrix(R, null, mGravity, mGeomagnetic)) {
            Toast.makeText(this, "getRotationMatrix() failed", Toast.LENGTH_LONG).show();
            return;
        }

        float orientation[] = new float[9];
        SensorManager.getOrientation(R, orientation);
        // Orientation contains: azimuth, pitch and roll - we'll use roll
        float roll = orientation[2];
        int rollDeg = (int) Math.round(Math.toDegrees(roll));
        int power = degreesToPower(rollDeg);
        //Log.d(TAG, "deg=" + rollDeg + " power=" + power);
        situp_tv.setText(String.valueOf(power));*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}