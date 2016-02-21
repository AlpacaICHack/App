package com.alpaca.app.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;

import com.alpaca.app.Util;
import com.alpaca.app.apiinterface.SendMovement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Accelerometer extends Service implements SensorEventListener {
    public static final String TAG = Accelerometer.class.getName();
    public static final int SCREEN_OFF_RECEIVER_DELAY = 500;

    private float previousMax = Float.MIN_VALUE;
    private int eventID = -1;
    private double lastUpdateTime = 0;
    private List<Float> localMaximums;
    private SensorManager sensorManager = null;
    private PowerManager.WakeLock wakeLock = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        registerListener();
        wakeLock.acquire();
        lastUpdateTime = Calendar.getInstance().getTimeInMillis() + 1000;
        localMaximums = new ArrayList<>();

        //SharedPreferences prefs = getSharedPreferences(Tags.SHARED_PREFFERENCES, MODE_MULTI_PROCESS);
        //eventID = prefs.getInt(Tags.EVENT_ID, -1);

        return START_STICKY;
    }

    private void registerListener() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        unregisterListener();
        wakeLock.release();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float movement = sumAbs(sensorEvent.values);

            if (movement > previousMax) {
                previousMax = movement;
            } else if (previousMax > 15) {
                localMaximums.add(previousMax - 15);
                previousMax = Float.MIN_VALUE;
            }
        }

        if (timeFromLastUpdate() > 1000 ) {
            float valueToSubmit;

            if (!localMaximums.isEmpty()) {
                valueToSubmit = Collections.max(localMaximums);
                lastUpdateTime = Calendar.getInstance().getTimeInMillis();
                localMaximums = new ArrayList<Float>();
            } else {
                valueToSubmit = 0;
                lastUpdateTime = Calendar.getInstance().getTimeInMillis();
            }

            if (eventID != -1) {
                SendMovement movement = new SendMovement(Math.round(valueToSubmit), eventID);
                movement.submit();
            }
        }
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        unregisterListener();
                        registerListener();
                    }
                };

                new Handler().postDelayed(runnable, SCREEN_OFF_RECEIVER_DELAY);
            }
        }
    };

    private float sumAbs(float[] values) {
        float sum = 0;

        for (float value : values) {
            sum += Math.abs(value);
        }

        return sum;
    }

    private double timeFromLastUpdate() {
        return Util.getTimeInMillis() - lastUpdateTime;
    }
}