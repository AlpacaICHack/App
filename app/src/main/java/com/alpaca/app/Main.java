package com.alpaca.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alpaca.app.apiinterface.ServerListener;
import com.alpaca.app.constants.Tags;
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
    private AudioManager manager;

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

            SharedPreferences.Editor editor = getSharedPreferences(Tags.SHARED_PREFFERENCES, MODE_MULTI_PROCESS).edit();
            editor.putInt(Tags.EVENT_ID, eventID);
            editor.commit();
        } else {
            throw new InvalidParameterException("Missing event data.");
        }

        new APICall(this).getEvent(eventID);
        startService(new Intent(this, Accelerometer.class));

        screenLock = new ScreenLock();
        screenLock.manageService(this);

        manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                return true;

            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        SharedPreferences.Editor editor = getSharedPreferences(Tags.SHARED_PREFFERENCES, MODE_MULTI_PROCESS).edit();
        editor.putInt(Tags.EVENT_ID, -1);
        editor.commit();

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

    private CardModel createCard(final SongInformation song) {
        CardModel card = new CardModel(song);

        card.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike(SongInformation song) {
                new APICall().voteSong(song, true, getApplication());
            }

            @Override
            public void onDislike(SongInformation song) {
                new APICall().voteSong(song, false, getApplication());
            }
        });

        return card;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_request:
                new RequestFragment().show(getFragmentManager(), "");
                break;
        }
        return true;
    }
}
