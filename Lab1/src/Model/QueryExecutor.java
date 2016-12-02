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
    private PreparedStatement getArtistByName;
    private PreparedStatement getArtistByRating;
    private PreparedStatement getArtistByGenre;
    private PreparedStatement getAlbumByTitle;
    private PreparedStatement getAlbumByGenre;
    private PreparedStatement getAlbumByRating;
    private PreparedStatement getAlbumByArtist;
    private PreparedStatement insertAlbum;
    private PreparedStatement insertArtist;
    private PreparedStatement insertArtistsAlbum;
    private String raing;
    
    public QueryExecutor(Connection con) throws SQLException {
        this.con = con;
        
        // prepared statement for getArtistByName
        String byArtistName = "SELECT * FROM T_Artist WHERE fName LIKE ? OR lName LIKE ?";
        getArtistByName = con.prepareStatement(byArtistName);
        
        // prepared statement for getArtistByRating
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
        //String newArtist = "NSERT INTO T_Artist (fName, lName, rating)" 
         //   + "VALUES(?, ?, ?) WHEN NOT EXISTS (SELECT fName FROM T_Artist" 
         //       + "WHERE fName = ?)";
       // String newArtist = "BEGIN IF NOT EXISTS (SELECT * FROM T_Artist"
       //         + "WHERE fName = ?)"
       //         + "BEGIN INSERT INTO T_Artist (fName, lName, rating)" 
       //         + "VALUES(?, ?, ?) END END";
        
                
        insertArtist = con.prepareStatement(newArtist, Statement.RETURN_GENERATED_KEYS);
        
        // preparted statement for insert new artistsAlbum
        String newArtistsAlbum = "INSERT INTO T_ArtistsAlbum (artistId, albumId)" 
            + "VALUES(?, ?)";
        insertArtistsAlbum = con.prepareStatement(newArtistsAlbum);
        
        // prepared statement for getAlbumByTitle
        String byAlbumTitle = "SELECT * FROM T_Album WHERE title LIKE ?";
        getAlbumByTitle = con.prepareStatement(byAlbumTitle);
        
        // prepared statement for getAlbumByGenre
        String byAlbumGenre = "SELECT * FROM T_Album WHERE genre LIKE ?";
        getAlbumByGenre = con.prepareStatement(byAlbumGenre);
        
        // prepared statement for getAlbumByRating
        String byAlbumRating = "SELECT * FROM T_Album WHERE rating LIKE ?";
        getAlbumByRating = con.prepareStatement(byAlbumRating);
        
        // prepared statement for getAlbumByArtist
        String byAlbumArtist = "SELECT * FROM T_Album WHERE "
                + "albumId = (SELECT albumId FROM T_ArtistsAlbum WHERE "
                + "artistId = (SELECT artistId FROM T_Artist WHERE "
                + "fName LIKE ?))";
        getAlbumByArtist = con.prepareStatement(byAlbumArtist);
    }
    
    @Override
    public ArrayList<Artist> getArtistsByName(String name) {
        try {
            getArtistByName.setString(1, "%" + name + "%");
            getArtistByName.setString(2, "%" + name + "%");
            ResultSet rs = getArtistByName.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("fName"), rs.getString("lName"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("Didn't select anything");
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
            System.out.println("Didn't select anything");
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
            System.out.println("Didn't select anything");
        }
    }

    @Override
    public void addNewArtist(String fName, String lName, String rating) {
        try {
            //insertArtist.setString(1, fName);
            insertArtist.setString(1, fName);
            insertArtist.setString(2, lName);
            insertArtist.setString(3, rating);
            
            insertArtist.executeUpdate();
            ResultSet rs = insertArtist.getGeneratedKeys();
            while (rs.next())
                artistPKEY = rs.getInt(1);
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("This one Didn't select anything");
        }
    }

    @Override
    public void addNewArtistsAlbum() {
        try {
            insertArtistsAlbum.setInt(1, artistPKEY);
            insertArtistsAlbum.setInt(2, albumPKEY);
            insertArtistsAlbum.executeUpdate();
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("Didn't select anything");
        }
    }


    
        //ALBUM
    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) {
        try {
            getAlbumByTitle.setString(1, "%" + title + "%");
            ResultSet rs = getAlbumByTitle.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) {
        try {
            getAlbumByGenre.setString(1, "%" + genre + "%");
            ResultSet rs = getAlbumByGenre.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByRating(String rating) {
        try {
            getAlbumByRating.setString(1, "%" + rating + "%");
            ResultSet rs = getAlbumByRating.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByArtist(String artist) {
        try {
            getAlbumByArtist.setString(1, "%" + artist + "%");
            ResultSet rs = getAlbumByArtist.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("Didn't select anything");
            return null;
        }
    }

    /*@Override
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
            System.out.println("Didn't select anything");
            return null;
        }
    }*/
    
}
