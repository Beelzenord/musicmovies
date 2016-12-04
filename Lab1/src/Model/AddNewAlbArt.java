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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        initPreparedStatements();
    }
    
    private void initPreparedStatements()  throws SQLException{
        // prepared statement for insert new album
        String newAlbum = "INSERT INTO T_Album (title, genre, rating, " 
            + "releaseDate) VALUES(?, ?, ?, ?)";
        insertAlbum = con.prepareStatement(newAlbum, Statement.RETURN_GENERATED_KEYS);
            
        // prepared statement for insert new artist
        String newArtist = "INSERT INTO T_Artist (name, rating, nationality)" 
            + "VALUES(?, ?, ?)"; // get primary key and insert nationality
        insertArtist = con.prepareStatement(newArtist, Statement.RETURN_GENERATED_KEYS);
        
        // preparted statement for insert new artistsAlbum
        String newAlbumDirectory = "INSERT INTO T_AlbumDirectory (artistId, albumId)" 
            + "VALUES(?, ?)";
        insertAlbumDirectory = con.prepareStatement(newAlbumDirectory);
    }
        
    @Override
    public void addNew(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        try {
            con.setAutoCommit(false);
            
            ArrayList<Album> tmp1 = new GetAlbums(con).getAlbMovByAll(title, genre, ratingAM, rDate);
            if (tmp1 == null) {
                ResultSet rs = null;
                try {
                    insertAlbum.setString(1, title);
                    insertAlbum.setString(2, genre);
                    insertAlbum.setString(3, ratingAM);
                    insertAlbum.setDate(4, rDate);
                    insertAlbum.executeUpdate();
                    rs = insertAlbum.getGeneratedKeys();
                    while (rs.next())
                        albumPKEY = rs.getInt(1);
                } finally {
                    rs.close();
                }
            }
            else
                albumPKEY = tmp1.get(0).getAlbumId();
            
            
            ArrayList<Artist> tmp2 = new GetArtists(con).getArtDirByAll(name, ratingAD, nationality);
            if (tmp2 == null) {
                ResultSet rs = null;
                try {
                    insertArtist.setString(1, name);
                    insertArtist.setString(2, ratingAD);
                    insertArtist.setString(3, nationality);

                    insertArtist.executeUpdate();
                    rs = insertArtist.getGeneratedKeys();
                    while (rs.next())
                        artistPKEY = rs.getInt(1);
                } finally {
                    rs.close();
                }
            }
            else
                artistPKEY = tmp2.get(0).getArtistId();
            
            System.out.println("arPKEY" + artistPKEY);
            System.out.println("alPKEY" + albumPKEY);
            insertAlbumDirectory.setInt(1, artistPKEY);
            insertAlbumDirectory.setInt(2, albumPKEY);
            insertAlbumDirectory.executeUpdate();

            con.commit();
            
        }catch (SQLException ex) {
                // show alert message and redo
                if (con!= null)
                    con.rollback();
                 throw ex;
            }
        finally {
            con.setAutoCommit(true);
        }
    }
    
    @Override
    public void addNewAlbMov(String title, String genre, String rating, Date rDate) {
        ArrayList<Album> tmp = new GetAlbums(con).getAlbMovByAll(title, genre, rating, rDate);
        if (tmp == null) {
            try {
                insertAlbum.setString(1, title);
                insertAlbum.setString(2, genre);
                insertAlbum.setString(3, rating);
                insertAlbum.setDate(4, rDate);
                insertAlbum.executeUpdate();
                ResultSet rs = insertAlbum.getGeneratedKeys();
                while (rs.next())
                    albumPKEY = rs.getInt(1);
            } catch (SQLException ex) {
                // show alert message and redo
                System.out.println("addNewAlbum Didn't select anything");
            }
        }
        else
            albumPKEY = tmp.get(0).getAlbumId();
    }

    @Override
    public void addNewArtDir(String name, String rating, String nationality) {
        ArrayList<Artist> tmp = new GetArtists(con).getArtDirByAll(name, rating, nationality);
        if (tmp == null) {
            try {
                insertArtist.setString(1, name);
                insertArtist.setString(2, rating);
                insertArtist.setString(3, nationality);

                insertArtist.executeUpdate();
                ResultSet rs = insertArtist.getGeneratedKeys();
                while (rs.next())
                    artistPKEY = rs.getInt(1);
            } catch (SQLException ex) {
                // show alert message and redo
                System.out.println("addNewArtist Didn't select anything");
            }
        }
        else
            artistPKEY = tmp.get(0).getArtistId();
    }

    @Override
    public void addNewADMD() {
        try {
            System.out.println("arPKEY" + artistPKEY);
            System.out.println("alPKEY" + albumPKEY);
            insertAlbumDirectory.setInt(1, artistPKEY);
            insertAlbumDirectory.setInt(2, albumPKEY);
            insertAlbumDirectory.executeUpdate();
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("addNewAlbumDirectory Didn't select anything");
        }
    }

    @Override
    public void close() throws SQLException {
        insertAlbum.close();
        insertArtist.close();
        insertAlbumDirectory.close();
    }

}
