package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.security.InvalidParameterException;

public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent extras = getIntent();
        int eventID;
        String eventName = null;

        if (extras != null) {
            eventID = extras.getIntExtra("id", -1);
            eventName = extras.getStringExtra("eventName");
        } else {
            throw new InvalidParameterException("Missing event data.");
        }

        if (eventName != null) {
            getActionBar().setTitle(eventName);
        } else {
            throw new InvalidParameterException("Event name missing.");
        }
    }
}
