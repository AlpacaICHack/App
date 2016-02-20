package com.alpaca.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by mauriceyap on 20/02/16.
 */
public class CurrentSong extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.current_song, container, false);
        TextView trackTitle = (TextView) view.findViewById(R.id.trackTitle);
        TextView artistName = (TextView) view.findViewById(R.id.trackArtist);
        trackTitle.setText("Shofukan");
        artistName.setText("Snarky Puppy");
        ImageButton upVoteButton = (ImageButton) view.findViewById(R.id.upVote);
        ImageButton downVoteButton = (ImageButton) view.findViewById(R.id.downVote);

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currTrackUp();
            }
        });

        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currTrackDown();
            }
        });

        return view;

    }

    public void currTrackUp() {

    }

    public void currTrackDown() {

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

    public void voteCurrTrack(View view) {

    }

}
