package spookylobster.break_habit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PushupActivity extends AppCompatActivity {
    private SQLhandler handler = new SQLhandler(this,null,1);;
    private Integer pushupCounter = 10;
    private TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);
        pushupCounter = handler.getData("SetPushup");
        counter = (TextView) findViewById(R.id.pushup_counter);
        updateCounterText();
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                press();
            }
        });
    }

    private void updateCounterText() {
        counter.setText(pushupCounter.toString());
    }

    public void press() {
        pushupCounter -= 1;
        if (pushupCounter == 0) {
            stopPushupActivity();
        }
        updateCounterText();
    }
    public void stopPushupActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
