package com.alpaca.app;

import android.util.Pair;

import com.alpaca.app.apiinterface.Event;

import java.util.List;

public interface ServerInterface {
    List<Pair<Integer, String>> getEvents();
    Event getEvent(int eventId);
    List<SongInformation> getSongPool();
    void voteTrackUp(int songId);
    void voteTrackDown(int songId);
    void voteRequestUp(int requestId);
    void voteRequestDown(int requestId);
    void postMovement(int movement);
}
