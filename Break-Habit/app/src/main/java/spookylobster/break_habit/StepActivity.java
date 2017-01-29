package spookylobster.break_habit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StepActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private TextView stepcounterTV;
    private boolean exercising;
    private Integer steps = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        exercising = true;
        stepcounterTV = (TextView)findViewById(R.id.stepcount);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }

        //updatecountertext();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepcounterTV.setText("Step Counter Detected : " + value);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //public void updatecountertext(){
      //  stepcounterTV.setText(steps.toString());
    //}
}
