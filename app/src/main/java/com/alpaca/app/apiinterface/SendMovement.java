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

    // Returns true if the vote was made successfully
    // Returns false if the vote was unsuccessful
    public boolean submit(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String baseString = "http://alpaca.stenbom.eu/api/movement?event=";
        String requestString = baseString + eventID;
        requestString += "&value=" + movementValue;
        HttpGet httpGet = new HttpGet(requestString);
        try {
            httpClient.execute(httpGet, localContext);
            return true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
