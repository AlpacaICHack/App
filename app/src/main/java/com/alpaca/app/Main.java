package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.alpaca.app.apiinterface.ServerListener;
import com.alpaca.app.constants.Intents;
import com.alpaca.app.services.Accelerometer;
import com.alpaca.app.services.ScreenLock;
import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import java.security.InvalidParameterException;
import java.util.List;

public class Main extends Activity implements ServerListener {
    private CardContainer cardContainer;
    private ScreenLock screenLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent extras = getIntent();
        int eventID;

        if (extras != null) {
            eventID = extras.getIntExtra("id", -1);

            if (eventID == -1) {
                throw new InvalidParameterException("Wrong event ID");
            }

            Intent intent = new Intent(Intents.EVENTID);
            intent.putExtra(Intents.EVENTID, eventID);
            sendBroadcast(intent);
        } else {
            throw new InvalidParameterException("Missing event data.");
        }

        new APICall(this).getEvent(eventID);

        screenLock = new ScreenLock();
        screenLock.manageService(this);

        Intent intent = new Intent(this, Accelerometer.class);
        intent.putExtra(Intents.EVENTID, eventID);
        startService(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        screenLock.stopService();
        stopService(new Intent(Main.this, Accelerometer.class));
    }

    @Override
    public void gotEvents(List<Event> events) {}

    @Override
    public void gotEvent(Event event) {
        new APICall(this).getPool(event.getEventId());
    }

    @Override
    public void gotPool(List<SongInformation> songs) {
        cardContainer = (CardContainer) findViewById(R.id.layoutview);
        cardContainer.setOrientation(Orientations.Orientation.Ordered);

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        for (SongInformation song : songs) {
            adapter.add(createCard(song));
        }

        cardContainer.setAdapter(adapter);
    }

    @Override
    public void gotSong(SongInformation song) {

    }

    private CardModel createCard(SongInformation song) {
        String title = song.getSongName();
        String description = song.getArtistName();
        Bitmap image = null;

        CardModel card = new CardModel(title, description, getResources().getDrawable(R.drawable.picture1));

        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                //Actually dislike
                Log.d("Swipeable CardModel", "I disliked it");
            }

            @Override
            public void onDislike() {
                //Actually like
                Log.d("Swipeable CardModel", "I liked it");
            }
        });

        return card;
    }
}
