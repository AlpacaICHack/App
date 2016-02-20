package com.alpaca.app.apiinterface;

public class Event {
    private int eventId;
    private String eventName;

    public Event(int eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
