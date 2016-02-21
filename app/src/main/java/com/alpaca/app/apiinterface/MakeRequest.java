package com.alpaca.app.apiinterface;

public class MakeRequest {
    private int eventId;
    private String artist;
    private String songName;

    public MakeRequest(int eventId, String artist, String songName) {
        this.eventId = eventId;
        this.artist = artist;
        this.songName = songName;
    }

    public void submit(){
        String baseUrl = "http://alpaca.stenbom.eu/api/add_request?event=";
        String requestUrl = baseUrl += eventId;
        requestUrl += "&artist=" + artist;
        requestUrl += "&track=" + songName;
        requestUrl = requestUrl.replaceAll(" ", "%20");
        new AsyncGetRequest().execute(requestUrl);

    }
}
