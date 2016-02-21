package com.alpaca.app;

import android.graphics.Bitmap;

public class SongInformation {
    private int id;
    private String songName;
    private String artistName;
    private String albumArtURL;
    private boolean isRequest;
    private Bitmap image;

    public SongInformation(int id, String songName, String artistName,
                           String albumArtURL, boolean isRequest) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
        this.albumArtURL = albumArtURL;
        this.isRequest = isRequest;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumArtURL() {
        return albumArtURL;
    }

    public void setAlbumArtURL(String albumArtURL) {
        this.albumArtURL = albumArtURL;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setIsRequest(boolean isRequest) {
        this.isRequest = isRequest;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
