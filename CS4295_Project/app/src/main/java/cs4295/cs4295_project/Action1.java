package cs4295.cs4295_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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


public class Action1 extends ActionBarActivity implements TimerActivity{

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private TextView textViewActionName;
    private CountDownTimer countDownTimer; // built in android class
    private AccSensor sensor = new AccSensor(this);

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    //Set timer From share preference
    private int time ;

    //For intent
    private LinearLayout layout ;
    private int timeLeft , actionId ;

    private Vibrator myVib;

    //Share preference
    SharedPreferences settingsPrefs;
    String repeat;
    String exerciseTime;
    String breakTime;
    Boolean SoundOn ;

    //Sound
    private MediaPlayer mp1 ;
    private MediaPlayer mp2 ;
    private MediaPlayer mp3 ;
    private MediaPlayer mpStop ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent myIntent = getIntent(); // gets the previously created intent
        timeLeft = myIntent.getIntExtra("TimeLeft",30);// get share preference
        actionId = myIntent.getIntExtra("currentAction",0);

        //
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setImageResource(imgNum[actionId]);
        TextView roundNum = (TextView)findViewById(R.id.tvRound);
        roundNum.setText("Round "+ (actionId+1));
        textViewActionName = (TextView)findViewById(R.id.tvActionName);
        textViewActionName.setText(actionName[actionId]);

        //Get Sound
        mp1 = MediaPlayer.create(this, R.raw.one2);
        mp2 = MediaPlayer.create(this, R.raw.two2);
        mp3 = MediaPlayer.create(this, R.raw.three2);
        mpStop =MediaPlayer.create(this, R.raw.stop);

        //Get Share Preference
        settingsPrefs = getSharedPreferences("FitBo", MODE_PRIVATE);
        repeat = settingsPrefs.getString(getString(R.string.repeat), "1");
        exerciseTime = settingsPrefs.getString(getString(R.string.exerciseTime), "30");
        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");

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

                    pauseHandle();
            }
        });

        if(myIntent.getExtras() == null) {
            String testing= "first time repeat:"+repeat +" ex.time: "+exerciseTime+" breaktime: "+breakTime;
            time = Integer.parseInt(exerciseTime) ;
            setTimer(time,time); // The exercise time and the timeLeft should be the same
            startTimer();
        }
        else {
            time = Integer.parseInt(exerciseTime) ;

            if(myIntent.getBooleanExtra("ChangeAction",true)) {
                //Change Action -> Reset Timer
                timeLeft = Integer.parseInt(exerciseTime) ;
            }
            else{
                //Set to the pause time
                timeLeft = myIntent.getIntExtra("TimeLeft", 1);
            }

            textViewShowTime.setText(timeLeft+"\"");
            setTimer(time,timeLeft); //get from share preference
            startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        time = Integer.parseInt(breakTime) ;
        countDownTimer.cancel();
        sensor.stopSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor.startSensor();
    }

    private void setTimer(int time,int timeLeft) {
        mProgressBar.setMax(time);
        mProgressBar.setProgress(timeLeft);

        totalTimeCountInMilliseconds = timeLeft * 1000;
        timeBlinkInMilliseconds = (time/2) * 1000;
    }

    public void resetTimer() {
        sensor.stopSensor();
        countDownTimer.cancel();
        setTimer(Integer.parseInt(exerciseTime), Integer.parseInt(exerciseTime));
        startTimer();
    }

    private void startTimer() {
        sensor.startSensor();
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
                switch ((int)seconds){
                    case 0 :
                        mpStop.start();
                        break;
                    case 1:
                        mp1.start();
                        break;
                    case 2 :
                        mp2.start();
                        break;
                    case 3 :
                        mp3.start();
                        break;
                }
            }

            @Override
            public void onFinish() {
                //Create an Intent -> pass actionId + 1 and time= 0 -> Start Activity
                Intent i = new Intent(getApplicationContext() ,Break.class);
                i.putExtra("TimeLeft",10); //Set time to 10 sec
                i.putExtra("currentAction",actionId+1);
                startActivity(i);
                finish();
            }
        };

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countDownTimer.start();
            }
        }, 1000); // 1s = 1000ms

    }

    private void pauseHandle(){
        String timeLeft = textViewShowTime.getText().toString().replace("\"","");
        int time = Integer.parseInt(timeLeft);

        //Stop the timer when get to the pause page
        countDownTimer.cancel();
        sensor.stopSensor();

        Intent i = new Intent(getApplicationContext() ,Pause.class);
        i.putExtra("TimeLeft",time);
        i.putExtra("currentAction", actionId);
        i.putExtra("currentPage","Action1");
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Cancel all activities when back button pressed
        countDownTimer.cancel();
        sensor.stopSensor();
        finish();

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
