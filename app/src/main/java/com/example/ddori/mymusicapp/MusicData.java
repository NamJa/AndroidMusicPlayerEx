package com.example.ddori.mymusicapp;

import java.io.Serializable;

/**
 * Created by jongwoo on 2017. 12. 21..
 */

public class MusicData implements Serializable{
    private String id;
    private String albumId;
    private String title;
    private String artist;
    private String durTime;

    public MusicData() {

    }

    public MusicData(String id, String albumId, String title, String artist){
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.artist = artist;
    }
    public String getId() {return id;}
    public String getAlbumId() {return albumId;}
    public String getTitle() {return title;}
    public String getArtist() {return artist;}
    public String getDurTime() {return durTime;}

    public void setId(String id)
    {
        this.id = id;
    }
    public void setAlbumId(String albumId)
    {
        this.albumId = albumId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setArtist(String artist)
    {
        this.artist = artist;
    }
    public void setDurTime(String durTime) {this.durTime = durTime;}
}
