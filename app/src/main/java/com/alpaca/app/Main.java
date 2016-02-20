package com.alpaca.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alpaca.app.apiinterface.ServerListener;
import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import java.security.InvalidParameterException;
import java.util.List;

public class Main extends Activity implements ServerListener {
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
        CardContainer mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Ordered);

        CardModel card = new CardModel("Title1", "Description goes here", getResources().getDrawable(R.drawable.picture1));

        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.d("Swipeable CardModel", "I liked it");
            }

            @Override
            public void onDislike() {
                Log.d("Swipeable CardModel", "I did not liked it");
            }
        });

        card.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards", "I am pressing the card");
            }
        });

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        adapter.add(card);
        mCardContainer.setAdapter(adapter);
    }
}
