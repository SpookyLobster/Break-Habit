package spookylobster.break_habit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import java.util.concurrent.TimeUnit;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")

public class MainActivity extends AppCompatActivity {

    private Button startbutton;
    private TextView timeView;
    private String[] action = {"Shake", "Running"};
    private ListView actionList;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbutton = (Button)findViewById(R.id.startb);
        timeView = (TextView)findViewById(R.id.timeTV);
        actionList = (ListView)findViewById(R.id.actionList);

        actionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startShakeActivity();
                }
                if (i==1) {
                    StepActivity();
                }
            }
        });

        ListAdapter actionAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, action);
        actionList.setAdapter(actionAdapt);

        timeView.setText("00:00:00");
        final CounterClass timer = new CounterClass(3000, 1000);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startShakeActivity();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer{

        public CounterClass(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @SuppressLint("NewApi")
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            timeView.setText(hms);
        }
        @Override
        public void onFinish(){
            timeView.setText("Time Up");
            actionList.setVisibility(View.VISIBLE);
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startShakeActivity(){
        Intent intent = new Intent(this, ShakeActivity.class);
        startActivityForResult(intent, 0);
    }
    public void SettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void StepActivity(){
        Intent step_intent = new Intent(this,StepActivity.class );
        startActivity(step_intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            System.out.print("Stopped");
            r.stop();
            actionList.setVisibility(View.INVISIBLE);
        }


    }
}
