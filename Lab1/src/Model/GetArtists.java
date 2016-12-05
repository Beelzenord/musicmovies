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
import java.util.ArrayList;

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
    
    /** 
     * Creates a PreparedStatement to search for an artist by 
     * either name, rating or nationality.
     * @param searchBy How to search the database.
     * @throws SQLException 
     */
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
    
    /** 
     * Creates a PreparedStatement to search for an artist by album.
     * @throws SQLException 
     */
    private void createSearchByAlbumPrep() throws SQLException {
        String byArtist = "SELECT * FROM T_Artist WHERE "
                + "artistId IN (SELECT artistId FROM T_AlbumDirectory WHERE "
                + "albumId IN (SELECT albumId FROM T_Album WHERE "
                + "title LIKE ?))";
        searchByAlbumPrep = con.prepareStatement(byArtist);
    }
    
    /** 
     * Creates a PreparedStatement to search for an artist by 
     * name, rating and nationality.
     * @throws SQLException 
     */
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Artist WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        searchByAllPrep = con.prepareStatement(byAll);
    }
    
    /** Creates a PreparedStatement to search for an album by 
     * selected artists primary key.
     * @throws SQLException 
     */
    private void createSearchByPkeyPrep() throws SQLException {
        String byKey = "SELECT title FROM T_Album WHERE albumId IN "
                + "(SELECT albumId FROM T_AlbumDirectory WHERE "
                + "artistId = ?)";
        searchByPkeyPrep = con.prepareStatement(byKey);
    }
    
    /** 
     * Creates a PreparedStatement to rate an artist 
     * @throws SQLException 
     */
    private void createUpdateRatingPrep() throws SQLException {
        String updateRating = "UPDATE T_Artist SET rating = ? "
                + "WHERE artistId = ?";
        updateRatingPrep = con.prepareStatement(updateRating);
    }
    
    /** 
     * This will search the database and return a list of Artists
     * @param searchBy How to search the database, by name, rating, nationality, album
     * @param searchWord The word the user is search for 
     * @return An ArrayList<Artist> 
     * @throws SQLException 
     */
    @Override
    public ArrayList search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("album")) {
            try {
                con.setAutoCommit(false);
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
                    con.commit();
                    return tmp;
                } finally {
                    if (rs != null)
                        rs.close();
                    if (searchByAlbumPrep != null)
                        searchByAlbumPrep.close();
                }
            } catch (SQLException ex) {
                if (con != null)
                    con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
        else {
            try {
                con.setAutoCommit(false);
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
                    con.commit();
                    return tmp;
                } finally {
                    if (rs != null)
                        rs.close();
                    if (searchPrep != null)
                        searchPrep.close();
                }
            } catch (SQLException ex) {
                if (con != null)
                    con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    /** 
     * This will search the database and return a list of Artists
     * @param search1 The artists name
     * @param search2 The artists rating
     * @param search3 The artists nationality
     * @param search4 The albums date
     * @return An ArrayList<Artist> 
     * @throws SQLException 
     */
    @Override
    public ArrayList searchByAll(String search1, String search2, String search3, Date search4) throws SQLException {
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
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
                con.commit();
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
        } catch (SQLException ex) {
            if (con != null)
                con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
    
    /** 
     * This will return an arrayList<String> containing all the title
     * of all the albums who has an album with that name.
     * @param pKey The primary key of the artist.
     * @return An ArrayList<String> 
     * @throws SQLException 
     */
    private ArrayList<String> searchByPkey(int pKey) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchByPkeyPrep();
            searchByPkeyPrep.clearParameters();
            searchByPkeyPrep.setInt(1, pKey);
            rs = searchByPkeyPrep.executeQuery();
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

    /** 
     * This will update the rating of selected artist.
     * @param primaryKey The primary key of selected artist. 
     * @param rating The new rating for the artist.
     * @throws SQLException 
     */
    @Override
    public void updateRating(int primaryKey, String rating) throws SQLException {
        try {
            createUpdateRatingPrep();
            updateRatingPrep.setString(1, rating);
            updateRatingPrep.setInt(2, primaryKey);
            updateRatingPrep.executeUpdate();
        } finally {
            if (updateRatingPrep != null)
                updateRatingPrep.close();
        }
    }
}
