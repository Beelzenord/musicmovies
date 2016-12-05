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
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private String rating;
    private Date releaseDate;
    private ArrayList<String> directors;
    
    public Movie(int movieId, String title, String genre, String rating, Date releaseDate, ArrayList<String> directors) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.directors = new ArrayList();
        for (String s : directors)
            this.directors.add(s);
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
    
    public String getDirectors() {
        String tmp = new String("");
        for (String s : directors) {
            tmp += s + ", ";
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "Movie{" + "title=" + title + ", genre=" + genre 
            + ", rating=" + rating + ", releaseDate=" + releaseDate + '}';
    }
    
    
}
