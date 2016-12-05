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
    /** 
     * This can be used to search a database and return a generic ArrayList
     * @param searchBy How to search the database, by name, genre, rating, artist
     * @param searchWord The word the user is searching for 
     * @return An ArrayList<E> 
     * @throws SQLException 
     */
    public ArrayList<E> search(String searchBy, String searchWord) throws SQLException;
    
    /** 
     * This can be used to search a database and return a generic ArrayList
     * @param search1 The first word the user is searching for 
     * @param search2 The first word the user is searching for 
     * @param search3 The first word the user is searching for 
     * @param search4 The first word the user is searching for 
     * @return An ArrayList<E> 
     * @throws SQLException 
     */
    public ArrayList<E> searchByAll(String search1, String search2, String search3, Date search4) throws SQLException;
    
    /**
     * This can be used to update the rating of a tuple in a database
     * @param primaryKey The primary key of the tuple to be changed
     * @param rating The new rating 
     * @throws SQLException 
     */
    public void updateRating(int primaryKey, String rating) throws SQLException;
}
