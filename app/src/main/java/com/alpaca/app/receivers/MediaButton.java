package com.alpaca.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;

import com.alpaca.app.constants.Intents;

public class MediaButton extends BroadcastReceiver {
    private static final String TAG = MediaButton.class.getSimpleName();
    private static int eventID = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Null intent.");
            return;
        }

        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            int prevVolume = intent.getExtras().getInt(
                    "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
            int currentVolume = intent.getExtras().getInt(
                    "android.media.EXTRA_VOLUME_STREAM_VALUE", 0);

            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            if (currentVolume == 0) {
                changeVolume(am, 1);
            } else if (currentVolume == maxVolume) {
                changeVolume(am, maxVolume - 1);
            }

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 200, 100, 200};

            if (prevVolume != currentVolume && prevVolume != 0 && prevVolume != maxVolume) {
                if (prevVolume > currentVolume) {
                    vibrator.vibrate(300);
                    dislike();
                } else if (prevVolume < currentVolume) {
                    vibrator.vibrate(pattern, -1);
                    like();
                }
            }
        } else if (intent.getAction().equals(Intents.EVENTID)) {
            eventID = intent.getIntExtra(Intents.EVENTID, -1);
        }
    }

    private void changeVolume(AudioManager am, int volume) {
        am.setStreamVolume(AudioManager.STREAM_MUSIC,
                volume,
                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    private void like() {
        if (eventID == -1) {
            return;
        }

        //ToDo
        Log.i(TAG, "Liked: " + String.valueOf(eventID));
    }

    private void dislike() {
        if (eventID == -1) {
            return;
        }

        //ToDo
        Log.i(TAG, "Disliked: " + String.valueOf(eventID));
    }
}