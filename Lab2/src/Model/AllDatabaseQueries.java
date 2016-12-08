/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public interface AllDatabaseQueries<E> {
    
    /**
     * 
     * @param media Specifies if the search is for an album or a movie.
     * @param searchBy Specifies how to search: title, genre, rating or entertainer.
     * @param searchWord What the user is searching for. 
     * @return An ArrayList of albums or movies. 
     * @throws SQLException 
     */
    public ArrayList<E> getMedia(String media, String searchBy, String searchWord) throws SQLException;
    
    /**
     * 
     * @param entertainer Specifies if the search is for an artist or a director.
     * @param searchBy Specifies how to search: name, rating, nationality or media.
     * @param searchWord
     * @return An ArrayList of artists or directors. 
     * @throws SQLException 
     */
    public ArrayList<E> getEntertainer(String entertainer, String searchBy, String searchWord) throws SQLException;
    
    /**
     * 
     * @param determ Specifies if an album and artist is added or a movie and director. 
     * @param ratingAM The rating of the media.
     * @param ratingAD The rating of the entertainer.
     * @throws SQLException 
     */
    public void addNewItem(String determ, String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException;
    
    /**
     * 
     * @param determ Specifies if an album and artist is searched for or a movie and director. 
     * @return The primary key if the entertainer already exists, else -1.
     * @throws SQLException 
     */
    public int skipDuplicates(String determ, String name, String rating, String nationality) throws SQLException;
    
    /**
     * 
     * @param item The item to be updated: album, artist, movie or director.
     * @param primaryKey The primary key of the item to be updated. 
     * @param rating The new rating. 
     * @throws SQLException 
     */
    public void updateRating(String item, int primaryKey, String rating) throws SQLException;
}
