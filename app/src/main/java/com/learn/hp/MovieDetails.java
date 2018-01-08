package com.learn.hp;

import java.io.Serializable;


public class MovieDetails  implements Serializable {

    private String original_title;

    private String overview;

    private String release_date;

    private double vote_average;
    private int id;

    public void setId(int fromID) {
        id = fromID;
    }

    public String getId()
    {String temp=""+id;
        return temp;}

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String poster_path;

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
    
}
