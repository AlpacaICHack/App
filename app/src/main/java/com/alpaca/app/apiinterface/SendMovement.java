package com.alpaca.app.apiinterface;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class SendMovement {
    private int movementValue;
    private int eventID;

    public SendMovement(int movementValue, int eventID){
        this.movementValue = movementValue;
        this.eventID = eventID;
    }

    public void submit(){
        String baseString = "http://alpaca.stenbom.eu/api/movement?event=";
        String requestString = baseString + eventID;
        requestString += "&value=" + movementValue;
        new AsyncGetRequest().execute(requestString);
    }
}
