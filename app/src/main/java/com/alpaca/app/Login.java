package com.alpaca.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alpaca.app.apiinterface.ServerListener;
import com.alpaca.app.services.Accelerometer;

import java.util.ArrayList;
import java.util.List;

public class Login extends ActionBarActivity implements ServerListener{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        new APICall(this).getEvents();

        
    }

    @Override
    public void gotEvents(final List<Event> events) {
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new EventsAdapter(events, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Login.this, Main.class);
                i.putExtra("id", events.get(position).getEventId());
                i.putExtra("eventName", events.get(position).getEventName());
                startActivity(i);
            }

        });

        startService(new Intent(this, Accelerometer.class));
    }

    @Override
    public void gotEvent(Event event) {

    }
}
