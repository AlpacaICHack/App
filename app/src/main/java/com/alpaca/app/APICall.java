package com.alpaca.app;

import android.content.Context;

import com.alpaca.app.apiinterface.GetEvents;
import com.alpaca.app.apiinterface.ServerListener;


public class APICall{
    private ServerListener context;

    public APICall(ServerListener context){
        this.context = context;
    }

    public void getEvents(){
        new GetEvents(context);
    }
}
