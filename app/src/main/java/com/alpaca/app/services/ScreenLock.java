package com.alpaca.app.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.alpaca.app.R;


public class ScreenLock extends Service {
  private static final String TAG = ScreenLock.class.getSimpleName();

  private static ScreenLock screenLockService;
  private MediaPlayer mediaPlayer;

  public ScreenLock() {
    screenLockService = this;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (mediaPlayer == null) {
      mediaPlayer = MediaPlayer.create(this, R.raw.sound);
      mediaPlayer.setVolume(0, 0);
      mediaPlayer.setLooping(true);
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    if (!mediaPlayer.isPlaying()) {
      mediaPlayer.start();
    }

    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public IBinder onBind(Intent intent) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void onDestroy() {
    stopMediaPlay();
    super.onDestroy();
  }

  private void stopMediaPlay() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
  }

  public static void manageService(Context context) {
      Intent intent = new Intent(context, ScreenLock.class);
      context.startService(intent);
      Log.i(TAG, "Service started");
  }

  public static void stopService() {
    if (screenLockService != null) {
      Log.i(TAG, "Service Destroyed");
      screenLockService.stopSelf();
    }
  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    Log.i(TAG, ServiceInfo.FLAG_STOP_WITH_TASK + "");
    Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
    restartServiceIntent.setPackage(getPackageName());

    PendingIntent restartServicePendingIntent = PendingIntent.getService(
            getApplicationContext(), 1, restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT);

    AlarmManager alarmService = (AlarmManager) getApplicationContext()
        .getSystemService(Context.ALARM_SERVICE);

    alarmService.set(AlarmManager.ELAPSED_REALTIME,
        SystemClock.elapsedRealtime() + 1000,
        restartServicePendingIntent);

    super.onTaskRemoved(rootIntent);
  }
}