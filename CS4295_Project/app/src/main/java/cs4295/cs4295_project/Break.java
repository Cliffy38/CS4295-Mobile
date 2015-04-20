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


public class Break extends ActionBarActivity implements TimerActivity{

    private ProgressBar mProgressBar;
    private TextView textViewShowTime;
    private TextView textViewActionName;
    private CountDownTimer countDownTimer; // built in android class
    private AccSensor sensor = new AccSensor(this);//Sensor

    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    //Set timer From share preference
    private int time;

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
    private MediaPlayer mpGo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent myIntent = getIntent(); // gets the previously created intent
        actionId = myIntent.getIntExtra("currentAction",0);

        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setImageResource(imgNum[actionId]);
        textViewActionName = (TextView)findViewById(R.id.tvActionName);
        textViewActionName.setText(actionName[actionId]);

        //Get Sound
        mp1 = MediaPlayer.create(this, R.raw.one2);
        mp2 = MediaPlayer.create(this, R.raw.two2);
        mp3 = MediaPlayer.create(this, R.raw.three2);
        mpGo =MediaPlayer.create(this, R.raw.go2);

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

                Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
                pauseHandle();
            }
        });

        if(myIntent.getExtras() == null) {
            String testing= "first time repeat:"+repeat +" ex.time: "+exerciseTime+" breaktime: "+breakTime;
            Toast.makeText(getApplicationContext(), testing , Toast.LENGTH_LONG).show();
            time = Integer.parseInt(breakTime) ;
            setTimer(time,time); // The exercise time and the timeLeft should be the same
            startTimer();

        }
        else {
            String testing= "Intent Data Here:"+ repeat +" ex.time: "+exerciseTime+" breaktime: "+breakTime;
            Toast.makeText(getApplicationContext(), testing, Toast.LENGTH_LONG).show();

            time = Integer.parseInt(breakTime) ;

            if(myIntent.getBooleanExtra("ChangeAction",true)) {
                //Change Action -> Reset Timer
                timeLeft = Integer.parseInt(breakTime) ;
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

    private void setTimer(int time,int timeLeft) {
        mProgressBar.setMax(time);
        mProgressBar.setProgress(timeLeft);

        totalTimeCountInMilliseconds = timeLeft * 1000;
        timeBlinkInMilliseconds = (time/2) * 1000;
    }

    private void resetTimer() {
        setTimer(30,30);
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
                        mpGo.start();
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
                skipBreak();
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

    public void skipBreak(){
        //Create an Intent -> pass actionId + 1 and time= 0 -> Start Activity
        Intent i = new Intent(getApplicationContext() ,Action1.class);
        i.putExtra("TimeLeft",Integer.parseInt(exerciseTime));
        i.putExtra("currentAction",actionId);
        i.putExtra("ChangeAction",false);
        finish();
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        sensor.stopSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor.startSensor();
    }

    private void pauseHandle(){
        String timeLeft = textViewShowTime.getText().toString().replace("\"","");
        int time = Integer.parseInt(timeLeft);

        ImageView x =  (ImageView)findViewById(R.id.imageView1);
        countDownTimer.cancel();
        sensor.stopSensor();

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
