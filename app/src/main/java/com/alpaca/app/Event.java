package com.alpaca.app;

import java.util.Date;

public class Event {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private String pictureURL;

    public Event(int eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public Event(int eventId, String eventName, String eventDescription,
                 Date eventDate, String pictureURL) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.pictureURL = pictureURL;
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
