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

/**
 *
 * @author Niklas
 */
public class GetArtists implements QueryGenerator {
    private Connection con;
    private PreparedStatement getArtistByName;
    private PreparedStatement getArtistByRating;
    private PreparedStatement getArtistByNationality;
    private PreparedStatement getArtistByAlbum;
    
    
    public GetArtists(Connection con) {
        this.con = con;
        initPreparedStatements();
    }
    
    private void initPreparedStatements() {
        try {
        // prepared statement for getArtistByName
        String byArtistName = "SELECT * FROM T_Artist WHERE name LIKE ?";
        getArtistByName = con.prepareStatement(byArtistName);
        
        // prepared statement for getArtistByRating
        String byArtistRating = "SELECT * FROM T_Artist WHERE rating = ?";
        getArtistByRating = con.prepareStatement(byArtistRating);
        
        // prepared statement for getArtistByNationality
        String byArtistNationality = "SELECT * FROM T_Artist WHERE nationality = ?";
        getArtistByNationality = con.prepareStatement(byArtistNationality);
        
        // prepared statement for getAlbumByArtist
        String byArtistAlbum = "SELECT * FROM T_Artist WHERE "
                + "artistId IN (SELECT artistId FROM T_AlbumDirectory WHERE "
                + "albumId IN (SELECT albumId FROM T_Album WHERE "
                + "title LIKE ?))";
        getArtistByAlbum = con.prepareStatement(byArtistAlbum);
        
        } catch (SQLException ex) {
            System.out.println("what to do here? Prepared "
                    + "statements of getAlbum crashed");
        }
    }
    
    @Override
    public ArrayList getByTitle(String title) {
        return null;
    }

    @Override
    public ArrayList getByRating(String rating) {
        try {
            getArtistByRating.setString(1, rating);
            ResultSet rs = getArtistByRating.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistsByRating Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByGenre(String genre) {
        return null;
    }

    @Override
    public ArrayList getByNationality(String nationality) {
        try {
            getArtistByNationality.setString(1, nationality);
            ResultSet rs = getArtistByNationality.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistsByNationality Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Artist> getByName(String name) {
        try {
            getArtistByName.setString(1, "%" + name + "%");
            ResultSet rs = getArtistByName.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistsByName Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByArtist(String artist) {
        return null;
    }

    @Override
    public ArrayList getByAlbum(String album) {
        try {
            getArtistByAlbum.setString(1, "%" + album + "%");
            ResultSet rs = getArtistByAlbum.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistByAlbum Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByDirector(String director) {
        return null;
    }

    @Override
    public ArrayList getByMovie(String movie) {
        return null;
    }
}
