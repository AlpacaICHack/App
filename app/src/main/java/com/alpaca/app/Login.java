package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alpaca.app.services.Accelerometer;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {
    ListView listView;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        events = APICall.getEvents();
        List<String> values = new ArrayList<String>();

        for (Event event : events) {
            values.add(event.getEventName());
        }

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
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
}
