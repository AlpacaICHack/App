package com.alpaca.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.alpaca.app.constants.Tags;

public class RequestFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.request_form, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Request song", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences prefs = getActivity().
                                getSharedPreferences(
                                        Tags.SHARED_PREFFERENCES,
                                        getActivity().MODE_MULTI_PROCESS);
                        int eventId = prefs.getInt(Tags.EVENT_ID, -1);
                        EditText artist = (EditText)
                                view.findViewById(R.id.artistName);
                        EditText song = (EditText)
                                view.findViewById(R.id.songName);
                        String artistName = artist.getText().toString();
                        String songName = song.getText().toString();
                        new APICall().requestSong(
                                eventId, artistName, songName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RequestFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
