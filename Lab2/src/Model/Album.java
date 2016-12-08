/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public class Album {
    private int albumId;
    private String title;
    private String genre;
    private String rating;
    private Date releaseDate;
    private String artist;
    
    public Album(int albumId, String title, String genre, String rating, Date releaseDate, String artist) {
        this.albumId = albumId;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.artist = artist;
    }
    public Album(String title, String genre, String rating, Date releaseDate) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtist() {
        return artist;
    }
    


    @Override
    public String toString() {
        return "Album{" + "title=" + title + ", genre=" + genre 
            + ", rating=" + rating + ", releaseDate=" + releaseDate + '}';
    }
    
}
