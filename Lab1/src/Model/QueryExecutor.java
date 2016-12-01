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
public class QueryExecutor implements DBQueries {
    private Connection con;
    private int albumPKEY;
    private int artistPKEY;
    private PreparedStatement getByName;
    private PreparedStatement getArtistByRating;
    private PreparedStatement getByTitle;
    private PreparedStatement getByGenre;
    private PreparedStatement insertAlbum;
    private PreparedStatement insertArtist;
    private PreparedStatement insertArtistsAlbum;
    private String raing;
    
    public QueryExecutor(Connection con) throws SQLException {
        this.con = con;
        
        // prepared statement for getByArtistName
        String byName = "SELECT * FROM T_Artist WHERE fName = ? OR lName = ?";
        getByName = con.prepareStatement(byName);
        
        // prepared statement for getByArtistRating
        String byArtistRating = "SELECT * FROM T_Artist WHERE rating = ?";
        getArtistByRating = con.prepareStatement(byArtistRating);
        
        /*// prepared statement for getByTitle
        String byTitle = "SELECT * FROM T_Artist WHERE title = ?";
        getByTitle = con.prepareStatement(byTitle);
        
        // prepared statement for getByGenre
        String byGenre = "SELECT * FROM T_Artist WHERE genre = ?";
        getByGenre = con.prepareStatement(byGenre);*/
        
        // prepared statement for insert new album
        String newAlbum = "INSERT INTO T_Album (title, genre, rating, " 
            + "releaseDate) VALUES(?, ?, ?, ?)";
        insertAlbum = con.prepareStatement(newAlbum, Statement.RETURN_GENERATED_KEYS);
   
        // prepared statement for insert new artist
        String newArtist = "INSERT INTO T_Artist (fName, lName, rating)" 
            + "VALUES(?, ?, ?)";
        insertArtist = con.prepareStatement(newArtist, Statement.RETURN_GENERATED_KEYS);
        
        // preparted statement for insert new artistsAlbum
        String newArtistsAlbum = "INSERT INTO T_ArtistsAlbum (artistId, albumId)" 
            + "VALUES(?, ?)";
        insertArtistsAlbum = con.prepareStatement(newArtistsAlbum);
    }
    
    @Override
    public ArrayList<Artist> getArtistsByName(String name) {
        try {
            getByName.setString(1, name);
            getByName.setString(2, name);
            ResultSet rs = getByName.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("fName"), rs.getString("lName"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    @Override
    public ArrayList<Artist> getArtistsByRating(String rating) {
        try {
            getArtistByRating.setString(1, rating);
            ResultSet rs = getArtistByRating.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("fName"), rs.getString("lName"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            return null;
        }
    }


    @Override
    public void addNewAlbum(String title, String genre, String rating, Date rDate) {
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
        }
    }

    @Override
    public void addNewArtist(String fName, String lName, String rating) {
        try {
            insertArtist.setString(1, fName);
            insertArtist.setString(2, lName);
            insertArtist.setString(3, rating);
            insertArtist.executeUpdate();
            ResultSet rs = insertArtist.getGeneratedKeys();
            while (rs.next())
                artistPKEY = rs.getInt(1);
        } catch (SQLException ex) {
            // show alert message and redo
        }
    }

    @Override
    public void addNewArtistsAlbum() {
        try {
            insertArtistsAlbum.setInt(1, artistPKEY);
            System.out.println("1");
            insertArtistsAlbum.setInt(2, albumPKEY);
            System.out.println("2");
            insertArtistsAlbum.executeUpdate();
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("ADDNEWARTISTSALBUM REKT");
        }
    }


    
        //ALBUM
    /*@Override
    public ArrayList<Artist> getArtistsByTitle(String title) {
        try {
            getByTitle.setString(1, title);
            ResultSet rs = getByTitle.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("fName"), rs.getString("lName"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            return null;
        }

    @Override
    public ArrayList<Artist> getArtistsByGenre(String genre) {
        try {
            getByGenre.setString(1, genre);
            ResultSet rs = getByGenre.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("fName"), rs.getString("lName"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            return null;
        }
    }*/
    
}
