package com.alpaca.app.apiinterface;

import com.alpaca.app.Event;

import java.util.List;

public interface ServerListener {
    void gotEvents(List<Event> events);
}
