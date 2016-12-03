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
public interface QueryGenerator <E> {
    /** ALBUM **/
    //title
    //genre
    //rating
    //artist
    
    /** ARTIST **/
    //name
    //rating
    //nationality
    //album
    
    /** MOVIE **/
    //title
    //genre
    //rating
    //director 
    
    /** DIRECTOR **/
    //name
    //rating
    //nationality
    //movie
    
    public ArrayList<E> getByTitle(String title); 
    public ArrayList<E> getByRating(String rating); 
    public ArrayList<E> getByGenre(String genre);
    public ArrayList<E> getByNationality(String nationality);
    public ArrayList<E> getByName(String name);
    
    public ArrayList<E> getByArtist(String artist);
    public ArrayList<E> getByAlbum(String album);
    public ArrayList<E> getByDirector(String director);
    public ArrayList<E> getByMovie(String movie);
}
