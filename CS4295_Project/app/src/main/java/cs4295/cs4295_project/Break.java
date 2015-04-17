package cs4295.cs4295_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Handler;


public class Break extends ActionBarActivity {

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private TextView textViewActionName;
    private CountDownTimer countDownTimer; // built in android class

    //for switching images
    private int[] imgNum = {
            R.drawable.action1,R.drawable.action2,R.drawable.action3,R.drawable.action4,
            R.drawable.action5,R.drawable.action6,R.drawable.action7,R.drawable.action8,
            R.drawable.action9,R.drawable.action10,R.drawable.action11,R.drawable.action12
    };

    private String[] actionName ={
            "Jumping Jacks", "Wall Sit","Push Up","Abdominal Crunch","Step up Onto Chair",
            "Squat","Triceps Dip On Chair", "Plank", "High Knees/Running", "Lunge",
            "Push-Up and Rotation", "Right Side Plank", "Left Side Plank"
    } ;

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    //Set Break Time
    private int time = 10 ;

    //For intent
    private LinearLayout layout ;
    private int timeLeft , actionId ;
    private boolean countDownEnd = false; // if countDownEnd = true -> Times up

    private Vibrator myVib;

    //Share preference
    SharedPreferences settingsPrefs;
    String repeat;
    String exerciseTime;
    String breakTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent myIntent = getIntent(); // gets the previously created intent
        actionId = myIntent.getIntExtra("currentAction",0);

        //
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setImageResource(imgNum[actionId]);
        //TextView roundNum = (TextView)findViewById(R.id.tvRound);
        //roundNum.setText("Round "+ (actionId));
        textViewActionName = (TextView)findViewById(R.id.tvActionName);
        textViewActionName.setText(actionName[actionId]);

        //Get Share Preference
        /*
        settingsPrefs = getSharedPreferences("FitBo", MODE_PRIVATE);
        repeat = settingsPrefs.getString(getString(R.string.repeat), "1");
        exerciseTime = settingsPrefs.getString(getString(R.string.exerciseTime), "30");
        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");
        */

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        textViewShowTime = (TextView)findViewById(R.id.tvTimeCount);
        textViewShowTime.setTextAppearance(getApplicationContext(),R.style.normalText);

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

                if(! countDownEnd) {
                    pauseHandle();
                }
                else {
                    resetTimer();
                }
            }
        });

        if(myIntent.getExtras() == null) {
            String testing= "first time repeat:"+repeat +" ex.time: "+exerciseTime+" breaktime: "+breakTime;
            Toast.makeText(getApplicationContext(), testing , Toast.LENGTH_LONG).show();
            timeLeft = 10 ;
            setTimer(time,timeLeft); //10 second
            startTimer();

        }
        else {
            String testing= "Intent Data Here:"+ repeat +" ex.time: "+exerciseTime+" breaktime: "+breakTime;
            Toast.makeText(getApplicationContext(), testing, Toast.LENGTH_LONG).show();

            if(myIntent.getBooleanExtra("ChangeAction",false)) {
                //Set to the pause time
                timeLeft = myIntent.getIntExtra("TimeLeft", 1);
            }
            else{
                //Change Action -> Reset Timer
                timeLeft = 10;
            }

            textViewShowTime.setText(timeLeft+"\"");
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

    private void resetTimer() {
        countDownEnd = false;
        setTimer(30,30);
        startTimer();
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
                    // change the style of the textview -> giving a red alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                    } else {
                        textViewShowTime.setVisibility(View.VISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(seconds+"\"");

            }

            @Override
            public void onFinish() {

                //Create an Intent -> pass actionId + 1 and time= 0 -> Start Activity
                Intent i = new Intent(getApplicationContext() ,Action1.class);
                i.putExtra("TimeLeft",30);
                i.putExtra("currentAction",actionId);
                i.putExtra("ChangeAction",false);
                startActivity(i);
                finish();

            }

        };

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1s = 1000ms
                countDownTimer.start();
            }
        }, 1000);

    }

    private void pauseHandle(){
        String timeLeft = textViewShowTime.getText().toString().replace("\"","");
        int time = Integer.parseInt(timeLeft);

        ImageView x =  (ImageView)findViewById(R.id.imageView1);
        countDownTimer.cancel();

        Intent i = new Intent(getApplicationContext() ,Pause.class);
        i.putExtra("TimeLeft",time);
        i.putExtra("currentAction", actionId);
        i.putExtra("currentPage","Break");

        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_break, menu);
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
