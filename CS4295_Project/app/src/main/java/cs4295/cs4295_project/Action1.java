package cs4295.cs4295_project;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Action1 extends ActionBarActivity {

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private CountDownTimer countDownTimer; // built in android class

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action1);

        textViewShowTime = (TextView)findViewById(R.id.tvTimeCount);

        //Rotating the ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setRotation((90.0f));

        textViewShowTime.setTextAppearance(getApplicationContext(),R.style.normalText);
        setTimer(30); //30 second
        startTimer();

    }

    private void setTimer(int time) {
        mProgressBar.setMax(time);

        totalTimeCountInMilliseconds = time * 1000;
        timeBlinkInMilliseconds = (time/2) * 1000;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (millisUntilFinished / 1000));
                Context context = getApplicationContext();

                textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


                if (millisUntilFinished < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.VISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(String.format("%02d", seconds % 60)+"\"");
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
            }

        }.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
