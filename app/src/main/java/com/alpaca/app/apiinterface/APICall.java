package com.alpaca.app.apiinterface;

import android.util.Pair;

import com.alpaca.app.ServerInterface;
import com.alpaca.app.SongInformation;

import java.util.ArrayList;
import java.util.List;

public class APICall implements ServerInterface{
    @Override
    public List<Pair<Integer, String>> getEvents() {
        List<Pair<Integer, String>> events
                = new ArrayList<Pair<Integer, String>>();
        events.add(new Pair<Integer, String>(1, "Room One"));
        events.add(new Pair<Integer, String>(2, "Room Two"));
        events.add(new Pair<Integer, String>(3, "Room Three"));
        return events;
    }

    @Override
    public Event getEvent(int eventId) {
        switch (eventId){
            case 1:
                return new Event(1, "Room One");
            case 2:
                return new Event(2, "Room Two");
            case 3:
                return new Event(3, "Room Three");
        }
        return null;
    }

    @Override
    public List<SongInformation> getSongPool() {
        List<SongInformation> songs = new ArrayList<SongInformation>();
        SongInformation song1 = new SongInformation(1, "Name 1", "Artist 1");
        SongInformation song2 = new SongInformation(2, "Name 2", "Artist 1");
        SongInformation song3 = new SongInformation(3, "Name 3", "Artist 2");
        return songs;
    }

    @Override
    public void voteTrackUp(int songId) {

    }

    @Override
    public void voteTrackDown(int songId) {

    }

    @Override
    public void voteRequestUp(int requestId) {

    }

    @Override
    public void voteRequestDown(int requestId) {

    }

    @Override
    public void postMovement(int movement) {

    }
}
