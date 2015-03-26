package cs4295.cs4295_project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RunSetting extends ActionBarActivity {
    public final static String distance = "cs4295.cs4295_project.distance";
    public final static String times = "cs4295.cs4295_project.times";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_setting);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_setting, menu);
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
    public void confirmRunSetting(View view){
        Intent intent = new Intent (this,RunActivity.class);
        EditText edistance = (EditText)findViewById(R.id.setDistanceEdit);
        EditText etimes = (EditText)findViewById(R.id.setTimesEdit);
        String sdistance = edistance.getText().toString();
        String stimes = etimes.getText().toString();
        intent.putExtra(distance, sdistance);
        intent.putExtra(times, stimes);
        startActivity(intent);
    }
}
