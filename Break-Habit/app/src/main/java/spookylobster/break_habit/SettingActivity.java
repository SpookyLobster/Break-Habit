package spookylobster.break_habit;

import android.content.Context;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.input;

public class SettingActivity extends AppCompatActivity {
    private SQLhandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        handler = new SQLhandler(this,null,1);
        final Button back= (Button) findViewById(R.id.backbutton);
        final Button save= (Button) findViewById(R.id.savebutton);
        final EditText numberShake, numberPush;
        TextView setPushup, setShake;
        numberShake = (EditText)findViewById(R.id.numberShake);
        numberPush = (EditText)findViewById(R.id.numberPush);
        setPushup = (TextView)findViewById(R.id.SetPushUp);
        setShake = (TextView)findViewById(R.id.SetPhoneShake);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int Shake = Integer.parseInt(numberShake.getText().toString());
                handler.updateSetting(Shake,"SetShake");
                int Push = Integer.parseInt(numberPush.getText().toString());
                handler.updateSetting(Push,"SetPush");
                Context context = getApplicationContext();
                CharSequence text = "Setting Saved " + handler.getData("SetShake") + " " + handler.getData("SetPush");
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                }
        });



    }



}
