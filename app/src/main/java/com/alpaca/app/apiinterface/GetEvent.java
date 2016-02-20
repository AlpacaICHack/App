package com.alpaca.app.apiinterface;

import com.alpaca.app.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetEvent implements AsyncTaskListener{
    private Event event;
    private ServerListener callback;
    private int eventId;

    public GetEvent(int eventId, ServerListener callback){
        this.callback = callback;
        this.eventId = eventId;
        String url = "http://alpaca.stenbom.eu/api/events?id=" + eventId;
        new DownloadString(this).execute(url);
    }

    private void parseJSON(String json){
        Event event = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject obj = jsonArray.getJSONObject(0);
            String date = obj.getString("date");
            String picture = obj.getString("picture");
            String description = obj.getString("description");
            String name = obj.getString("name");
            int id = obj.getInt("id");
            event = new Event(id, name, description, date, picture);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        callback.gotEvent(event);
    }
    @Override
    public void onStringDownloaded(String str) {
        parseJSON(str);
    }
}
