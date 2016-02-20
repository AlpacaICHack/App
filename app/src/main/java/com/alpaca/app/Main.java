package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.alpaca.app.apiinterface.ServerListener;
import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.security.InvalidParameterException;
import java.util.List;

public class Main extends Activity implements ServerListener {
    private CardContainer cardContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        new APICall(this).getEvent(eventID);
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

    private CardModel createCard(SongInformation song) {
        String title = song.getSongName();
        String description = song.getArtistName();
        Bitmap image = ImageLoader.getInstance().loadImageSync(song.getAlbumArtURL());
        Drawable drawable = new BitmapDrawable(image);

        CardModel card = new CardModel(title, description, drawable);

        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.d("Swipeable CardModel", "I did not liked it");
            }

            @Override
            public void onDislike() {
                Log.d("Swipeable CardModel", "I liked it");
            }
        });

        card.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards", "I am pressing the card");
            }
        });

        return card;
    }
}
