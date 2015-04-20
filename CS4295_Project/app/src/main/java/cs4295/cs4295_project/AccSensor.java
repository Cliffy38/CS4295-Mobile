package cs4295.cs4295_project;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccSensor implements SensorEventListener {
    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private Action1 action;
    private Break actionBreak;
    private int changeTimes = 0;

    public AccSensor(Action1 a){
        action = a;
    }
    public AccSensor(Break b) {
        actionBreak = b;
    }

    public void startSensor() {
        mInitialized = false;
        if (!(action == null)){
            mSensorManager = (SensorManager) action.getSystemService(Context.SENSOR_SERVICE);
        }
        else if (!(actionBreak == null)){
            mSensorManager = (SensorManager) actionBreak.getSystemService(Context.SENSOR_SERVICE);
        }
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void stopSensor() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
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
            if (deltaX > 15 || deltaY > 15 || deltaZ > 15){
                changeTimes++;
            }
            if (changeTimes > 2){
                callAction(action, actionBreak);
            }
        }
    }

    private void callAction(Action1 a, Break b){
        changeTimes = 0;
        if (!(a == null)){
            a.resetTimer();
        }
        else if (!(b == null)){
            b.skipBreak();
            b.onPause();
        }
    }

}