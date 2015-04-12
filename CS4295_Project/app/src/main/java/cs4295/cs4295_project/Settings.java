package cs4295.cs4295_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.*;


public class Settings extends ActionBarActivity {



    Button repeatView;
    Button workView;
    Button breakView;
    int repeat;
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
        mcontext = getApplicationContext();

        settingsPrefs=getSharedPreferences("FitBo", MODE_PRIVATE);



        repeat = settingsPrefs.getInt(getString(R.string.repeat),1);
        exerciseTime = settingsPrefs.getString(getString(R.string.exerciseTime), "30");
        breakTime = settingsPrefs.getString(getString(R.string.breakTime), "10");

        repeatView = (Button) findViewById(R.id.Repetition);
        workView = (Button) findViewById(R.id.Workout);
        breakView = (Button) findViewById(R.id.Break);

        repeatView.setText(Integer.toString(repeat));
        workView.setText(exerciseTime);
        breakView.setText(breakTime);

        repeatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               Repeat dialog = new Repeat(Settings.this,new Repeat.DialogListener() {
                    public void cancelled() {
                        // do your code here
                    }
                    public void ready(int n) {
                        SharedPreferences.Editor editor = settingsPrefs.edit();
                        editor.putInt(getString(R.string.repeat), n);
                        editor.commit();
                        repeat = settingsPrefs.getInt(getString(R.string.repeat), 1);
                        repeatView.setText(Integer.toString(repeat));
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
