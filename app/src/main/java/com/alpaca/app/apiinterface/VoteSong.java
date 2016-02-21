package com.alpaca.app.apiinterface;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.Secure;

import com.alpaca.app.SongInformation;

public class VoteSong {
    private boolean votingUp;
    private SongInformation song;
    private Context context;

    public VoteSong(SongInformation song, boolean votingUp, Context context) {
        this.votingUp = votingUp;
        this.song = song;
        this.context = context;
    }

    public void submitVote() {
        String baseUrl = "http://alpaca.stenbom.eu/api/vote_track?track=";
        String requestUrl = baseUrl + song.getId();
        requestUrl += "&user=" + getUniqueId();
        requestUrl += "&up=" + votingUp;
        new AsyncGetRequest().execute(requestUrl);
    }

    private String getUniqueId(){
        ContentResolver contentResolver = context.getContentResolver();
        return Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
    }
}
