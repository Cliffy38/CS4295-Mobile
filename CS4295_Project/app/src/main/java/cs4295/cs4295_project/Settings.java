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


public class Settings extends ActionBarActivity {
    Button repeatView;
    Button workView;
    Button breakView;
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

        repeatView = (Button) findViewById(R.id.Repetition);
        workView = (Button) findViewById(R.id.Workout);
        breakView = (Button) findViewById(R.id.Break);

        repeatView.setText(repeat);
        workView.setText(exerciseTime);
        breakView.setText(breakTime);

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
                        repeatView.setText(repeat);
                    }
                });
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
                        workView.setText(exerciseTime);
                    }
                });
                dialog.show();

            }
        });

        breakView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RestDialog dialog = new RestDialog(Settings.this, new RestDialog.DialogListener() {
                    public void cancelled() {
                        // do your code here
                    }

                    public void ready(String n) {
                        SharedPreferences.Editor editor = settingsPrefs.edit();
                        editor.putString(getString(R.string.breakTime), n);
                        editor.commit();
                        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");
                        breakView.setText(breakTime);
                    }
                });
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
