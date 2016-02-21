package com.alpaca.app;

import android.content.Context;

import com.alpaca.app.apiinterface.GetCurrentSong;
import com.alpaca.app.apiinterface.GetEvent;
import com.alpaca.app.apiinterface.GetEvents;
import com.alpaca.app.apiinterface.GetPool;
import com.alpaca.app.apiinterface.ServerListener;
import com.alpaca.app.apiinterface.VoteCurrentSong;


public class APICall{
    private ServerListener serverListenercontext;

    public APICall(ServerListener context){
        this.serverListenercontext = context;
    }

    public APICall(){}

    public void getEvents(){
        new GetEvents(serverListenercontext);
    }

    public void getEvent(int id){
        new GetEvent(id, serverListenercontext);
    }

    public void getPool(int id){
        new GetPool(id, serverListenercontext);
    }

    public void getSong(int eventId){
        new GetCurrentSong(eventId, serverListenercontext);
    }

    public void voteCurrentSong(int eventId, boolean isUp, Context context){
        new VoteCurrentSong(eventId, isUp, context);
    }
}
