/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Niklas
 */
public class AddNewAlbArt implements InsertGenerator {
    private Connection con;
    private PreparedStatement insertAlbum;
    private PreparedStatement insertArtist;
    private PreparedStatement insertAlbumDirectory;
    private int albumPKEY;
    private int artistPKEY;
    
    public AddNewAlbArt(Connection con)  throws SQLException{
        this.con = con;
    }
    
    /**
     * Creates a Prepared statement to insert a new album into the database
     * @throws SQLException 
     */
    private void createInsertAlbumPrep() throws SQLException {
        // prepared statement for insert new album
        String newAlbum = "INSERT INTO T_Album (title, genre, rating, "
                + "releaseDate) VALUES(?, ?, ?, ?)";
        insertAlbum = con.prepareStatement(newAlbum, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert a new artist into the database
     * @throws SQLException 
     */
    private void createInsertArtistPrep() throws SQLException {
        // prepared statement for insert new artist
        String newArtist = "INSERT INTO T_Artist (name, rating, nationality)"
                + "VALUES(?, ?, ?)"; // get primary key and insert nationality
        insertArtist = con.prepareStatement(newArtist, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert the new album and the 
     * new artist into the connection table 
     * @throws SQLException 
     */
    private void createInsertAlbumDirectoryPrep() throws SQLException {
        // preparted statement for insert new artistsAlbum
        String newAlbumDirectory = "INSERT INTO T_AlbumDirectory (artistId, albumId)"
                + "VALUES(?, ?)";
        insertAlbumDirectory = con.prepareStatement(newAlbumDirectory);
    }
    
    /**
     * This will add a new album and a new artist to the database 
     * @param title The title of the album
     * @param genre The genre of the album
     * @param ratingAM The rating of the album
     * @param rDate The release date of the album
     * @param name The name of the artist
     * @param ratingAD The rating of the artist
     * @param nationality The nationality of the artist
     * @throws SQLException 
     */
    @Override
    public void addNew(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        try {
            // only add new album if it doesn't already exist
            ArrayList<Album> tmp1 = new GetAlbums(con).searchByAll(title, genre, ratingAM, rDate);
            
            con.setAutoCommit(false);
            if (tmp1 == null) {
                ResultSet rs = null;
                try {
                    createInsertAlbumPrep();
                    insertAlbum.setString(1, title);
                    insertAlbum.setString(2, genre);
                    insertAlbum.setString(3, ratingAM);
                    insertAlbum.setDate(4, rDate);
                    insertAlbum.executeUpdate();
                    rs = insertAlbum.getGeneratedKeys();
                    while (rs.next())
                        albumPKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertAlbum != null)
                        insertAlbum.close();
                }
            }
            else
                albumPKEY = tmp1.get(0).getAlbumId();
            
            // only add new artist if it doesn't already exist
            ArrayList<Artist> tmp2 = new GetArtists(con).searchByAll(name, ratingAD, nationality, rDate);
            
            con.setAutoCommit(false);
            if (tmp2 == null) {
                ResultSet rs = null;
                try {
                    createInsertArtistPrep();
                    insertArtist.setString(1, name);
                    insertArtist.setString(2, ratingAD);
                    insertArtist.setString(3, nationality);
                    
                    insertArtist.executeUpdate();
                    rs = insertArtist.getGeneratedKeys();
                    while (rs.next())
                        artistPKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertArtist != null)
                        insertArtist.close();
                }
            }
            else
                artistPKEY = tmp2.get(0).getArtistId();
            
            // sambandstabell
            try {
                createInsertAlbumDirectoryPrep();
                insertAlbumDirectory.setInt(1, artistPKEY);
                insertAlbumDirectory.setInt(2, albumPKEY);
                insertAlbumDirectory.executeUpdate();
            } finally {
                if (insertAlbumDirectory != null)
                    insertAlbumDirectory.close();
            }
            
            
            con.commit();
            
        }catch (SQLException ex) {
            if (con != null)
                con.rollback();
            throw ex;
        }
        finally {
            con.setAutoCommit(true);
        }
    }
    
}
