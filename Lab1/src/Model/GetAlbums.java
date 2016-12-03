/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class GetAlbums implements QueryGenerator{
    private Connection con;
    private int albumPKEY;
    private PreparedStatement getAlbumByTitle;
    private PreparedStatement getAlbumByGenre;
    private PreparedStatement getAlbumByRating;
    private PreparedStatement getAlbumByArtist;

    public GetAlbums(Connection con) {
        this.con = con;
        initPreparedStatements();
    }
    private void initPreparedStatements() {
        try {
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
                    + "albumId IN (SELECT albumId FROM T_AlbumDirectory WHERE "
                    + "artistId IN (SELECT artistId FROM T_Artist WHERE "
                    + "name LIKE ?))";
            getAlbumByArtist = con.prepareStatement(byAlbumArtist);
        } catch (SQLException ex) {
            System.out.println("what to do here? Prepared "
                    + "statements of getAlbum crashed");
        }
    }
    
    @Override
    public ArrayList<Album> getByTitle(String title) {
        try {
            getAlbumByTitle.setString(1, "%" + title + "%");
            ResultSet rs = getAlbumByTitle.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByTitle Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList<Album> getByRating(String rating) {
        try {
            getAlbumByRating.setString(1, "%" + rating + "%");
            ResultSet rs = getAlbumByRating.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByRating Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList<Album> getByGenre(String genre) {
        try {
            getAlbumByGenre.setString(1, "%" + genre + "%");
            ResultSet rs = getAlbumByGenre.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByGenre Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList<Album> getByNationality(String genre) {
        return null;
    }
    
    @Override
    public ArrayList<Album> getByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Album> getByArtist(String artist) {
        try {
            getAlbumByArtist.setString(1, "%" + artist + "%");
            ResultSet rs = getAlbumByArtist.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByArtist Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getByAlbum(String album) {
        return null;
    }

    @Override
    public ArrayList<Album> getByDirector(String director) {
        return null;
    }

    @Override
    public ArrayList<Album> getByMovie(String movie) {
        return null;
    }
    
}
