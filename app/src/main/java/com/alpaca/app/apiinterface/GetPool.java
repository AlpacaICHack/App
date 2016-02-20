package com.alpaca.app.apiinterface;

import android.util.Pair;

import com.alpaca.app.SongInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPool implements AsyncTaskListener {
    private List<SongInformation> songs = new ArrayList<SongInformation>();
    private ServerListener context;

    public GetPool(int eventId, ServerListener context){
        String baseURL = "http://alpaca.stenbom.eu/api/pool?event=";
        this.context = context;
        new DownloadString(this).execute(baseURL + eventId);
    }

    private void parseJSON(String json){
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String artwork = obj.getString("artwork");
                boolean isRequest = obj.getBoolean("request");
                String name = obj.getString("name");
                String artist = obj.getString("artist");
                int id = obj.getInt("id");
                SongInformation song = new SongInformation(
                    id, name, artist, artwork, isRequest
                );
                songs.add(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.gotPool(songs);
    }

    @Override
    public void onStringDownloaded(String str){
        parseJSON(str);
    }
}
