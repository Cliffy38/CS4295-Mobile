package cs4295.cs4295_project;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Timer extends ActionBarActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private Button buttonStartTime, buttonStopTime;
    private CountDownTimer countDownTimer; // built in android class

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer); // get the layout -> layout.activity_timer

        buttonStartTime = (Button) findViewById(R.id.btnStartTime);
        buttonStopTime = (Button) findViewById(R.id.btnStopTime);
        textViewShowTime = (TextView)findViewById(R.id.tvTimeCount);

        //Rotating the ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setRotation((90.0f));

        buttonStartTime.setOnClickListener(this);
        buttonStopTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStartTime)
        {
            textViewShowTime.setTextAppearance(getApplicationContext(),R.style.normalText);
            setTimer(30); //30 second
            buttonStopTime.setVisibility(View.VISIBLE);
            buttonStartTime.setVisibility(View.GONE);

            startTimer();

        } else if (v.getId() == R.id.btnStopTime) {
            countDownTimer.cancel();
            buttonStartTime.setVisibility(View.VISIBLE);
            buttonStopTime.setVisibility(View.GONE);
        }
    }

    private void setTimer(int time) {
        mProgressBar.setMax(time);

        totalTimeCountInMilliseconds = time * 1000;
        timeBlinkInMilliseconds = (time/2) * 1000;
    }

    private void resetTimer()
    {
        countDownTimer.cancel();
        textViewShowTime.setText("00:00");

        textViewShowTime.setVisibility(View.VISIBLE);
        buttonStartTime.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {

            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (millisUntilFinished / 1000));
                textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


                if (millisUntilFinished < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.VISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.GONE);
            }

        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

