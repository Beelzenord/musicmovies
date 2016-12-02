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
public interface DBQueries {
    public ArrayList<Artist> getArtistsByName(String name);
    public ArrayList<Artist> getArtistsByRating(String rating);
    
    public ArrayList<Album> getAlbumsByTitle(String title);
    public ArrayList<Album> getAlbumsByGenre(String genre);
    public ArrayList<Album> getAlbumsByRating(String rating);
    public ArrayList<Album> getAlbumsByArtist(String artist);
    
    public ArrayList<Movie> getMoviesByTitle(String title);
    public ArrayList<Movie> getMoviesByGenre(String genre);
    public ArrayList<Movie> getMoviesByRating(String rating);
    public ArrayList<Movie> getMoviesByDirector(String director);
    
    public void addNewAlbum(String title, String genre, String rating, Date rDate);
    public void addNewArtist(String fName, String lName, String rating);
    public void addNewArtistsAlbum();
}
