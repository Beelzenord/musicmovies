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
        String byArtist = "SELECT * FROM T_Album WHERE "
                + "albumId IN (SELECT albumId FROM T_AlbumDirectory WHERE "
                + "artistId IN (SELECT artistId FROM T_Artist WHERE "
                + "name LIKE ?))";
        searchByAlbumPrep = con.prepareStatement(byArtist);
    }
    
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Artist WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        searchByAllPrep = con.prepareStatement(byAll);
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
                    tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality")));
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
                    tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality")));
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
                tmp.add(new Artist(rs.getInt("artistId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
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

}
