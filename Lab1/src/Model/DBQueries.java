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
    
    public void addNewAlbum(String title, String genre, String rating, Date rDate);
    public void addNewArtist(String fName, String lName, String rating);
    public void addNewArtistsAlbum();
}
