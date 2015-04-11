package cs4295.cs4295_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

public class AccTestActivity extends ActionBarActivity implements SensorEventListener {
    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private TextClock textClock;
    ArrayList<String> record = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_test);
        textClock = (TextClock)findViewById(R.id.textClock);
        textClock.setFormat12Hour("EEEE, MMMM dd, yyyy h:mmaa");
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
       //list out record
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                record);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        Context context = getApplicationContext();
//        Intent intent = new Intent(context, );
//        switch (item.getItemId()) {
//            case R.id.action_home:
//                intent = click.goTo(context,0);
//                startActivity(intent);
//                return true;
//            case R.id.action_settings:
//                intent = click.goTo(context,1);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        String className = this.getClass().toString();
        switch (item.getItemId()) {
            case R.id.action_home:
                if (!className.equals("class cs4295.cs4295_project.Home")) {
                    intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
                return true;
            case R.id.action_settings:
                intent = new Intent(getApplicationContext(), Timer.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        String recordRow = "";
        TextView tvX = (TextView) findViewById(R.id.x_axis);
        TextView tvY = (TextView) findViewById(R.id.y_axis);
        TextView tvZ = (TextView) findViewById(R.id.z_axis);
        ImageView iv = (ImageView) findViewById(R.id.image);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            tvX.setText("0.0");
            tvY.setText("0.0");
            tvZ.setText("0.0");
            mInitialized = true;
        } else {
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);
            if (deltaX < NOISE) deltaX = (float) 0.0;
            if (deltaY < NOISE) deltaY = (float) 0.0;
            if (deltaZ < NOISE) deltaZ = (float) 0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            tvX.setText(Float.toString(deltaX));
            tvY.setText(Float.toString(deltaY));
            tvZ.setText(Float.toString(deltaZ));
            recordRow = "X: " + Float.toString(deltaX) + " Y: " + Float.toString(deltaY) + " Z: " + Float.toString(deltaZ);
            iv.setVisibility(View.VISIBLE);
            if (deltaX > deltaY) {
                iv.setImageResource(R.drawable.horizontal);
                textClock.setFormat24Hour("yyyy-MM-dd hh:mm, EEEE");
                addRow(recordRow);
            } else if (deltaY > deltaX) {
                iv.setImageResource(R.drawable.vertical);
                textClock.setFormat12Hour("EEEE, MMMM dd, yyyy h:mmaa");
                addRow(recordRow);
            } else {
                iv.setVisibility(View.INVISIBLE);
                textClock.setFormat12Hour("MM/dd/yy h:mmaa");
            }
        }
    }

        private void addRow(String row){
            record.add(row);
            if (record.size() > 5)
                record.remove(0);
            adapter.notifyDataSetChanged();

        }

}