package com.alpaca.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;

public class MediaButton extends BroadcastReceiver {
  private static final String TAG = MediaButton.class.getSimpleName();
  private static boolean programmaticChange = false;

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent == null) {
      Log.e(TAG, "Null intent.");
      return;
    }

    if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
      if (programmaticChange) {
        programmaticChange = false;
        return;
      }

      int prevVolume = intent.getExtras().getInt("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
      int currentVolume = intent.getExtras().getInt("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);

      AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
      boolean bounded = false;

      if (currentVolume == 0) {
        changeVolume(am, 1);
        bounded = true;
      } else if (currentVolume == maxVolume) {
        changeVolume(am, maxVolume - 1);
        bounded = true;
      } else if (prevVolume == 0 || prevVolume == maxVolume) {
        bounded = true;
      }

      Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
      long[] pattern = {0, 200, 100, 200};

      if (prevVolume != currentVolume && !bounded) {
        if (prevVolume > currentVolume) {
          vibrator.vibrate(300);
        } else if (prevVolume < currentVolume) {
          vibrator.vibrate(pattern, -1);
        }
      }
    }
  }

  private void changeVolume(AudioManager am, int volume) {
    programmaticChange = true;
    am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
  }
}