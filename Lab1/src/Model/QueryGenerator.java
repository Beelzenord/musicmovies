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
public interface QueryGenerator <E> {

    /*public ArrayList<E> getByTitle(String title) throws SQLException ; 
    public ArrayList<E> getByRating(String rating)throws SQLException ; 
    public ArrayList<E> getByGenre(String genre)throws SQLException ;
    public ArrayList<E> getByNationality(String nationality)throws SQLException ;
    public ArrayList<E> getByName(String name)throws SQLException ;*/
    
    public ArrayList<E> search(String searchBy, String searchWord) throws SQLException;
    
    
    
    /*public ArrayList<E> getByArtist(String artist)throws SQLException ;
    public ArrayList<E> getByAlbum(String album)throws SQLException ;
    public ArrayList<E> getByDirector(String director)throws SQLException ;
    public ArrayList<E> getByMovie(String movie)throws SQLException ;
    
    public ArrayList<E> getAlbMovByAll(String title, String genre, String rating, Date rDate)throws SQLException ;
    public ArrayList<E> getArtDirByAll(String name, String rating, String nationality)throws SQLException ;
    
    public void close() throws SQLException;*/
}
