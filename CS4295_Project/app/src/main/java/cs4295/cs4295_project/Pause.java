package cs4295.cs4295_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Pause extends ActionBarActivity implements OnClickListener {

    private ImageButton next , play , previous;
    private int timeLeft , actionId ;

    //Testing
    private TextView time, action;
    private Vibrator myVib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        next = (ImageButton) findViewById(R.id.btnNext);
        play = (ImageButton) findViewById(R.id.btnPlay);
        previous = (ImageButton) findViewById(R.id.btnPrevious);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        next.setOnClickListener(this);
        play.setOnClickListener(this);
        previous.setOnClickListener(this);

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    play.setBackgroundResource(R.drawable.play_button2);
                }
                //Log.d("Pressed", "Button pressed");
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    play.setBackgroundResource(R.drawable.play_button);
                }
                //Log.d("Released", "Button released");
                // TODO Auto-generated method stub
                return false;
            }
        });

        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    next.setBackgroundResource(R.drawable.next_button2);
                }
                //Log.d("Pressed", "Button pressed");
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    next.setBackgroundResource(R.drawable.next_button);
                }
                //Log.d("Released", "Button released");
                // TODO Auto-generated method stub
                return false;
            }
        });

        previous.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    previous.setBackgroundResource(R.drawable.previous_button2);
                }
                //Log.d("Pressed", "Button pressed");
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    previous.setBackgroundResource(R.drawable.previous_button);
                }
                //Log.d("Released", "Button released");
                // TODO Auto-generated method stub
                return false;
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        timeLeft = myIntent.getIntExtra("TimeLeft",1);
        actionId = myIntent.getIntExtra("currentAction",0);

        //Testing
        time = (TextView)findViewById(R.id.tv_timeLeft);
        action = (TextView)findViewById(R.id.tv_ActionId);

        //time.setText(timeLeft);
        //action.setText(actionId);
    }

    @Override
    public void onClick(View v) {
        Intent i ;
        switch (v.getId()) {
            case R.id.btnNext:
                Toast.makeText(Pause.this, "Next is clicked!", Toast.LENGTH_SHORT).show();
                myVib.vibrate(50);

                //Create an Intent -> pass actionId + 1 and time= 0 -> Start Activity
                i = new Intent(getApplicationContext() ,Action1.class);
                i.putExtra("TimeLeft",30);
                i.putExtra("currentAction",actionId+1);

                startActivity(i);
                finish();

                break;
            case R.id.btnPlay:
                //Toast.makeText(Pause.this, "Play is clicked!", Toast.LENGTH_LONG).show();
                myVib.vibrate(50);

                i = new Intent(getApplicationContext() ,Action1.class);
                i.putExtra("TimeLeft",timeLeft);
                i.putExtra("currentAction", actionId);

                startActivity(i);
                finish();

                //Create an Intent -> pass actionId + time -> Start Activity
                break;
            case R.id.btnPrevious:
                Toast.makeText(Pause.this, "Previous is clicked!", Toast.LENGTH_SHORT).show();
                myVib.vibrate(50);

                i = new Intent(getApplicationContext() ,Action1.class);
                i.putExtra("TimeLeft",30);
                i.putExtra("currentAction",actionId);

                startActivity(i);
                finish();

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pause, menu);
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
