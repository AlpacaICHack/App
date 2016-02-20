package com.alpaca.app.apiinterface;

import android.content.Context;
import android.util.JsonReader;
import android.util.Pair;

import com.alpaca.app.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GetEvents implements AsyncTaskListener{

    private ServerListener callback;

    public GetEvents(ServerListener callback){
        this.callback = callback;
        new DownloadString(this).execute("http://alpaca.stenbom.eu/api/events");
    }

    private void parseJSON(String json){
        List<Event> events = new ArrayList<Event>();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String date = obj.getString("date");
                String picture = obj.getString("picture");
                String description = obj.getString("description");
                String name = obj.getString("name");
                int id = obj.getInt("id");
                Event event = new Event(id, name, description, date, picture);
                events.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callback.gotEvents(events);
    }

    @Override
    public void onStringDownloaded(String str){
        parseJSON(str);
    }
}
