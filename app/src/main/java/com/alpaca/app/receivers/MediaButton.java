package com.alpaca.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.Log;

import com.alpaca.app.APICall;
import com.alpaca.app.constants.Tags;

public class MediaButton extends BroadcastReceiver {
    private static final String TAG = MediaButton.class.getSimpleName();
    private static int eventID = -1;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent == null) {
            Log.e(TAG, "Null intent.");
            return;
        }

        if (eventID == -1) {
            SharedPreferences prefs = context.getSharedPreferences(Tags.SHARED_PREFFERENCES, Context.MODE_MULTI_PROCESS);
            eventID = prefs.getInt(Tags.EVENT_ID, -1);
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

        new APICall().voteCurrentSong(eventID, true, context);
        Log.i(TAG, "Liked: " + String.valueOf(eventID));
    }

    private void dislike() {
        if (eventID == -1) {
            return;
        }

        new APICall().voteCurrentSong(eventID, false, context);
        Log.i(TAG, "Disliked: " + String.valueOf(eventID));
    }
}