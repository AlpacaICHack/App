package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alpaca.app.apiinterface.ServerListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class Login extends Activity implements ServerListener{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageLoaderConfiguration configuration
                = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
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
    }

    @Override
    public void gotEvent(Event event) {}

    @Override
    public void gotPool(List<SongInformation> songs) {}

    @Override
    public void gotSong(SongInformation song) {

    }
}
