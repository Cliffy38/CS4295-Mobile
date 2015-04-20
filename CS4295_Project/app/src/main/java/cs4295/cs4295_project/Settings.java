package cs4295.cs4295_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Settings extends ActionBarActivity {
    LinearLayout repeatView;
    LinearLayout workView;
    LinearLayout breakView;
    TextView repeatSec;
    TextView workOutSec;
    TextView restSec;
    String repeat;
    String exerciseTime;
    String breakTime;
    SharedPreferences settingsPrefs;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterValues;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);

        mcontext = getApplicationContext();
        settingsPrefs = getSharedPreferences("FitBo", MODE_PRIVATE);

        repeat = settingsPrefs.getString(getString(R.string.repeat), "1");
        exerciseTime = settingsPrefs.getString(getString(R.string.exerciseTime), "30");
        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");

        repeatView = (LinearLayout) findViewById(R.id.first_of_setting);
        workView = (LinearLayout) findViewById(R.id.two_of_setting);
        breakView = (LinearLayout) findViewById(R.id.three_of_setting);

        repeatSec = (TextView) findViewById(R.id.Repetition);
        workOutSec = (TextView) findViewById(R.id.Workout);
        restSec = (TextView) findViewById(R.id.Break);

        repeatSec.setText(repeat+" times");
        workOutSec.setText(exerciseTime+" sec");
        restSec.setText(breakTime+" sec");

        repeatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RepeatDialog dialog = new RepeatDialog(Settings.this, new RepeatDialog.DialogListener() {
                    public void cancelled() {
                        // do your code here
                    }

                    public void ready(String n) {
                        SharedPreferences.Editor editor = settingsPrefs.edit();
                        editor.putString(getString(R.string.repeat), n);
                        editor.commit();
                        repeat = settingsPrefs.getString(getString(R.string.repeat), "1");
                        repeatSec.setText(repeat+" times");
                    }
                },repeat);
                dialog.show();

            }
        });

        workView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ExeciseTimeDialog dialog = new ExeciseTimeDialog(Settings.this, new ExeciseTimeDialog.DialogListener() {
                    public void cancelled() {
                        // do your code here
                    }

                    public void ready(String n) {
                        SharedPreferences.Editor editor = settingsPrefs.edit();
                        editor.putString(getString(R.string.exerciseTime), n);
                        editor.commit();
                        exerciseTime = settingsPrefs.getString(getString(R.string.exerciseTime), "30");
                        workOutSec.setText(exerciseTime+" sec");
                    }
                },exerciseTime);
                dialog.show();

            }
        });

        breakView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RestDialog dialog = new RestDialog(Settings.this, new RestDialog.DialogListener() {
                    public void cancelled() {}

                    public void ready(String n) {
                        SharedPreferences.Editor editor = settingsPrefs.edit();
                        editor.putString(getString(R.string.breakTime), n);
                        editor.commit();
                        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");
                        restSec.setText(breakTime+" sec");
                    }
                },breakTime);
                dialog.show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
