/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;


/**
 *
 * @author Niklas
 */
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private String rating;
    private Date releaseDate;
    private String director;
    
    public Movie(int movieId, String title, String genre, String rating, Date releaseDate, String director) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.director = director;
    }
    public Movie(String title, String genre, String rating, Date releaseDate) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public String getDirector() {
        return director;
    }
    


    @Override
    public String toString() {
        return "Movie{" + "title=" + title + ", genre=" + genre 
            + ", rating=" + rating + ", releaseDate=" + releaseDate + '}';
    }
    
}
