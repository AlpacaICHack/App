package com.alpaca.app.apiinterface;

import com.alpaca.app.Event;
import com.alpaca.app.SongInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetCurrentSong implements AsyncTaskListener{
    private SongInformation song;
    private ServerListener callback;
    private int eventId;

    public GetCurrentSong(int eventId, ServerListener callback){
        this.callback = callback;
        this.eventId = eventId;
        String baseURL = "http://alpaca.stenbom.eu/api/current_track?event=";
        String url = baseURL + eventId;
        new DownloadString(this).execute(url);
    }

    private void parseJSON(String json){
        try {
            JSONObject obj = new JSONObject(json);
            String artwork = obj.getString("artwork");
            boolean isRequest = obj.getBoolean("request");
            String name = obj.getString("name");
            String artist = obj.getString("artist");
            int id = obj.getInt("id");
            song = new SongInformation(
                    id, name, artist, artwork, isRequest
            );
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        callback.gotSong(song);
    }
    @Override
    public void onStringDownloaded(String str) {
        parseJSON(str);
    }
}
