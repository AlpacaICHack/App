package com.alpaca.app.apiinterface;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.Secure;

public class VoteCurrentSong {
    private boolean votingUp;
    private int eventId;
    private Context context;

    public VoteCurrentSong(int eventId, boolean votingUp, Context context) {
        this.votingUp = votingUp;
        this.eventId = eventId;
        this.context = context;

        String baseString = "http://alpaca.stenbom.eu/api/vote?event=";
        String requestString = baseString + eventId;
        requestString += "&user=" + getUniqueId();
        requestString += "&up=" + votingUp;
        new AsyncGetRequest().execute(requestString);
    }

    private String getUniqueId(){
        ContentResolver contentResolver = context.getContentResolver();
        return Secure.getString(contentResolver, Secure.ANDROID_ID);
    }


}
