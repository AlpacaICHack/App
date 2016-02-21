package com.alpaca.app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alpaca.app.APICall;
import com.alpaca.app.Event;
import com.alpaca.app.R;
import com.alpaca.app.SongInformation;
import com.alpaca.app.apiinterface.ServerListener;
import com.alpaca.app.constants.Tags;

import java.util.List;

public class CurrentSong extends Fragment implements ServerListener{
    private static final String TAG = CurrentSong.class.getSimpleName();

    private TextView trackTitle;
    private TextView artistName;
    private ImageButton upVoteButton;
    private ImageButton downVoteButton;
    private int eventId;
    private ServerListener context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.current_song, container, false);
        trackTitle = (TextView) view.findViewById(R.id.trackTitle);
        artistName = (TextView) view.findViewById(R.id.trackArtist);
        trackTitle.setSelected(true);
        artistName.setSelected(true);
        upVoteButton = (ImageButton) view.findViewById(R.id.upVote);
        downVoteButton = (ImageButton) view.findViewById(R.id.downVote);
        context = this;

        Intent intent = getActivity().getIntent();
        eventId = intent.getIntExtra("id", -1);
        if (eventId == -1) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new APICall(this).getSong(eventId);


        return view;

    }

    public void currTrackUp() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Tags.SHARED_PREFFERENCES, Context.MODE_MULTI_PROCESS);
        int eventID = prefs.getInt(Tags.EVENT_ID, -1);
        new APICall().voteCurrentSong(eventID, true, getActivity());
        Log.i(TAG, "Liked: " + String.valueOf(eventID));
    }

    public void currTrackDown() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Tags.SHARED_PREFFERENCES, Context.MODE_MULTI_PROCESS);
        int eventID = prefs.getInt(Tags.EVENT_ID, -1);
        new APICall().voteCurrentSong(eventID, false, getActivity());
        Log.i(TAG, "Disliked: " + String.valueOf(eventID));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void gotEvents(List<Event> events) {

    }

    @Override
    public void gotEvent(Event event) {

    }

    @Override
    public void gotPool(List<SongInformation> songs) {

    }

    @Override
    public void gotSong(SongInformation song) {

        if (song != null) {
            trackTitle.setText(song.getSongName());
            artistName.setText(song.getArtistName());

            upVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currTrackUp();
                    v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.click_image));
                }
            });
            downVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currTrackDown();
                    v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.click_image));
                }
            });
        } else {
            trackTitle.setText("No song");
            artistName.setVisibility(View.INVISIBLE);
            upVoteButton.setVisibility(View.INVISIBLE);
            downVoteButton.setVisibility(View.INVISIBLE);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new APICall(context).getSong(eventId);
            }
        }, 5000);
    }
}
