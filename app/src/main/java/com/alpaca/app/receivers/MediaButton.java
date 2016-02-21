package com.alpaca.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.alpaca.app.constants.Tags;

public class MediaButton extends BroadcastReceiver {
    private static final String TAG = MediaButton.class.getSimpleName();
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Null intent.");
            return;
        }

        this.context = context;

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

            if (prevVolume != currentVolume && prevVolume != 0 && prevVolume != maxVolume) {
                if (prevVolume > currentVolume) {
                    unlike();
                } else if (prevVolume < currentVolume) {
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
        context.sendBroadcast(new Intent(Tags.LIKE));
    }

    private void unlike() {
        context.sendBroadcast(new Intent(Tags.UNLIKE));
    }
}