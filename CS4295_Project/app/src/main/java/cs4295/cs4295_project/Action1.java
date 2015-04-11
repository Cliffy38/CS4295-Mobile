package cs4295.cs4295_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Action1 extends ActionBarActivity {

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private CountDownTimer countDownTimer; // built in android class

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    //Set timer -> Fixed
    private int time = 30 ;

    //For intent
    private LinearLayout layout ;
    private int timeLeft , actionId ;

    private Vibrator myVib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action1);

        textViewShowTime = (TextView)findViewById(R.id.tvTimeCount);
        textViewShowTime.setTextAppearance(getApplicationContext(),R.style.normalText);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        //Rotating the ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setRotation((90.0f));

        //set onClick event to the linear layout
        layout = (LinearLayout)findViewById(R.id.LinearLayout1);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myVib.vibrate(50);

                Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();

                String timeLeft = textViewShowTime.getText().toString().replace("\"","");
                int time = Integer.parseInt(timeLeft);

                ImageView x =  (ImageView)findViewById(R.id.imageView1);
                //x.getImage

                Intent i = new Intent(getApplicationContext() ,Pause.class);
                i.putExtra("TimeLeft",time);
                i.putExtra("currentAction", "SecondKeyValue");

                startActivity(i);
                finish();
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        timeLeft = myIntent.getIntExtra("TimeLeft",1);
        actionId = myIntent.getIntExtra("currentAction",1);

        if(myIntent.getExtras() == null) {
            Toast.makeText(getApplicationContext(), "first time", Toast.LENGTH_LONG).show();
            timeLeft = 30 ;

            setTimer(time,timeLeft); //30 second
            startTimer();

        } else {
            Toast.makeText(getApplicationContext(), "Intent data here", Toast.LENGTH_LONG).show();
            timeLeft = 25 ;

            setTimer(time,timeLeft); //30 second
            startTimer();
        }

    }

    private void setTimer(int time,int timeLeft) {
        mProgressBar.setMax(time);
        mProgressBar.setProgress(timeLeft);

        totalTimeCountInMilliseconds = timeLeft * 1000;
        timeBlinkInMilliseconds = (time/2) * 1000;
    }



    private void startTimer() {

        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
        @Override
        public void onTick(long millisUntilFinished) {
            long seconds = millisUntilFinished / 1000;
            //Setting the Progress Bar to decrease wih the timer
            mProgressBar.setProgress((int) (millisUntilFinished / 1000));

            textViewShowTime.setTextAppearance(getApplicationContext(),R.style.normalColor);

            if (millisUntilFinished < timeBlinkInMilliseconds) {
                textViewShowTime.setTextAppearance(getApplicationContext(),R.style.blinkText);
                // change the style of the textview .. giving a red alert style

                if (blink) {
                    textViewShowTime.setVisibility(View.VISIBLE);
                } else {
                    textViewShowTime.setVisibility(View.VISIBLE);
                }

                blink = !blink; // toggle the value of blink
            }

            textViewShowTime.setText(String.format("%02d", seconds % 60)+"\"");

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
