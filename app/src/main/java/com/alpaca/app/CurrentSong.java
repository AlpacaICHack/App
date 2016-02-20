package com.alpaca.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpaca.app.apiinterface.ServerListener;

import java.util.List;

/**
 * Created by mauriceyap on 20/02/16.
 */
public class CurrentSong extends Fragment implements ServerListener{

    private TextView trackTitle;
    private TextView artistName;
    private ImageView albumArtView;
    private ImageButton upVoteButton;
    private ImageButton downVoteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.current_song, container, false);
        trackTitle = (TextView) view.findViewById(R.id.trackTitle);
        artistName = (TextView) view.findViewById(R.id.trackArtist);
        albumArtView = (ImageView) view.findViewById(R.id.albumArt);
        upVoteButton = (ImageButton) view.findViewById(R.id.upVote);
        downVoteButton = (ImageButton) view.findViewById(R.id.downVote);

        //new APICall(this).getSong();


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

        trackTitle.setText(song.getSongName());
        artistName.setText(song.getArtistName());
        // TODO: albumArtView.setImage

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

    }
}
