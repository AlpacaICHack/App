package com.alpaca.app.apiinterface;

import com.alpaca.app.Event;
import com.alpaca.app.SongInformation;

import java.util.List;

public interface ServerListener {
    void gotEvents(List<Event> events);
    void gotEvent(Event event);
    void gotPool(List<SongInformation> songs);
}
