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
public class GetArtists implements QueryGenerator {
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByAlbumPrep;
    private PreparedStatement searchByAllPrep;
    private PreparedStatement searchByPkeyPrep;
    private PreparedStatement updateRatingPrep;
    
    public GetArtists(Connection con) {
        this.con = con;
    }
    
    private void createSearchPrep(String searchBy) throws SQLException {
        String prepState;
        if (searchBy.equals("name"))
            prepState = "SELECT * FROM T_Artist WHERE name LIKE ?";
        else if (searchBy.equals("rating"))
            prepState = "SELECT * FROM T_Artist WHERE rating LIKE ?";
        else
            prepState = "SELECT * FROM T_Artist WHERE nationality LIKE ?";
        searchPrep = con.prepareStatement(prepState);
    }
    
    private void createSearchByAlbumPrep() throws SQLException {
        String byArtist = "SELECT * FROM T_Artist WHERE "
                + "artistId IN (SELECT artistId FROM T_AlbumDirectory WHERE "
                + "albumId IN (SELECT albumId FROM T_Album WHERE "
                + "title LIKE ?))";
        searchByAlbumPrep = con.prepareStatement(byArtist);
    }
    
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Artist WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        searchByAllPrep = con.prepareStatement(byAll);
    }
    
    private void createSearchByPkeyPrep() throws SQLException {
        String byKey = "SELECT title FROM T_Album WHERE albumId IN "
                + "(SELECT albumId FROM T_AlbumDirectory WHERE "
                + "artistId = ?)";
        searchByPkeyPrep = con.prepareStatement(byKey);
    }
    
    private void createUpdateRatingPrep() throws SQLException {
        String updateRating = "UPDATE T_Artist SET rating = ? "
                + "WHERE artistId = ?";
        updateRatingPrep = con.prepareStatement(updateRating);
    }
    
    @Override
    public ArrayList search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("album")) {
            try {
                createSearchByAlbumPrep();
                searchByAlbumPrep.setString(1, "%" + searchWord + "%");
                rs = searchByAlbumPrep.executeQuery();
                ArrayList<Artist> tmp = new ArrayList();
                while (rs.next()) {
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    tmp.add(new Artist(rs.getInt(1), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality"), tmp3));
                }
                return tmp;
            } finally {
                if (rs != null)
                    rs.close();
                if (searchByAlbumPrep != null)
                    searchByAlbumPrep.close();
            }
        }
        else {
            try {
                createSearchPrep(searchBy);
                searchPrep.setString(1, "%" + searchWord + "%");
                rs = searchPrep.executeQuery();
                ArrayList<Artist> tmp = new ArrayList();
                while (rs.next()) {
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    tmp.add(new Artist(rs.getInt(1), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality"), tmp3));
                }
                return tmp;
            } finally {
                if (rs != null)
                    rs.close();
                if (searchPrep != null)
                    searchPrep.close();
            }
        }
    }

    @Override
    public ArrayList searchByAll(String search1, String search2, String search3, Date search4) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchByAllPrep();
            searchByAllPrep.setString(1, search1);
            searchByAllPrep.setString(2, search2);
            searchByAllPrep.setString(3, search3);
            rs = searchByAllPrep.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    tmp.add(new Artist(rs.getInt(1), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality"), tmp3));
            }
            if (tmp.size() > 0)
                return tmp;
            else
                return null;
        } finally {
            if (rs != null)
                rs.close();
            if (searchByAllPrep != null)
                searchByAllPrep.close();
        }
    }
    
    private ArrayList<String> searchByPkey(int pKey) throws SQLException {
        ResultSet rs = null;
        try {
            System.out.println("a");
            createSearchByPkeyPrep();
            System.out.println("b");
            searchByPkeyPrep.clearParameters();
            System.out.println("c");
            searchByPkeyPrep.setInt(1, pKey);
            System.out.println("d");
            rs = searchByPkeyPrep.executeQuery();
            System.out.println("e");
            ArrayList<String> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(rs.getString("title"));
            }
            return tmp;
        } finally {
                if (rs != null)
                    rs.close();
                if (searchByPkeyPrep != null)
                    searchByPkeyPrep.close();
            }
    }

    @Override
    public void updateRating(int primaryKey, String searchWord) throws SQLException {
        try {
            createUpdateRatingPrep();
            updateRatingPrep.setString(1, searchWord);
            updateRatingPrep.setInt(2, primaryKey);
            updateRatingPrep.executeUpdate();
        } finally {
            if (updateRatingPrep != null)
                updateRatingPrep.close();
        }
    }

}
