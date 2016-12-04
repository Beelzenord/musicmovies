/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author Niklas
 */
public interface InsertGenerator {
    public void addNew(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality)throws SQLException ;
    //public void addNewAlbMov(String title, String genre, String rating, Date rDate);
    //public void addNewArtDir(String name, String rating, String nationality);
    //public void addNewADMD();
    
    //public void close() throws SQLException;
    
    /*public void addNewMovie(String title, String genre, String rating, Date rDate);
    public void addNewDirector(String name, String rating);
    public void addNewMovieDirectory();*/
}
