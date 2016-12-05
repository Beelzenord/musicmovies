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
public class GetDirectors implements QueryGenerator {
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByMoviePrep;
    private PreparedStatement searchByAllPrep;
    private PreparedStatement searchByPkeyPrep;
    private PreparedStatement updateRatingPrep;
    
    public GetDirectors(Connection con) {
        this.con = con;
    }
    
    private void createSearchPrep(String searchBy) throws SQLException {
        String prepState;
        if (searchBy.equals("name"))
            prepState = "SELECT * FROM T_Director WHERE name LIKE ?";
        else if (searchBy.equals("rating"))
            prepState = "SELECT * FROM T_Director WHERE rating LIKE ?";
        else
            prepState = "SELECT * FROM T_Director WHERE nationality LIKE ?";
        searchPrep = con.prepareStatement(prepState);
    }
    
    private void createSearchByMoviePrep() throws SQLException {
        String byDirector = "SELECT * FROM T_Director WHERE "
                + "directorId IN (SELECT directorId FROM T_MovieDirectory WHERE "
                + "movieId IN (SELECT movieId FROM T_Movie WHERE "
                + "title LIKE ?))";
        searchByMoviePrep = con.prepareStatement(byDirector);
    }
    
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Director WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        searchByAllPrep = con.prepareStatement(byAll);
    }
    
    private void createSearchByPkeyPrep() throws SQLException {
        String byKey = "SELECT title FROM T_Movie WHERE movieId IN "
                + "(SELECT movieId FROM T_MovieDirectory WHERE "
                + "directorId = ?)";
        searchByPkeyPrep = con.prepareStatement(byKey);
    }
    
    private void createUpdateRatingPrep() throws SQLException {
        String updateRating = "UPDATE T_Director SET rating = ? "
                + "WHERE directorId = ?";
        updateRatingPrep = con.prepareStatement(updateRating);
    }
    
    @Override
    public ArrayList search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("movie")) {
            try {
                con.setAutoCommit(false);
                try {
                    createSearchByMoviePrep();
                    searchByMoviePrep.setString(1, "%" + searchWord + "%");
                    rs = searchByMoviePrep.executeQuery();
                    ArrayList<Director> tmp = new ArrayList();
                    while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                        tmp.add(new Director(rs.getInt(1), rs.getString("name"),
                                rs.getString("rating"), rs.getString("nationality"), tmp3));
                    }
                    con.commit();
                    return tmp;
                } finally {
                    if (rs != null)
                        rs.close();
                    if (searchByMoviePrep != null)
                        searchByMoviePrep.close();
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
                    ArrayList<Director> tmp = new ArrayList();
                    while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                        tmp.add(new Director(rs.getInt(1), rs.getString("name"), 
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
                ArrayList<Director> tmp = new ArrayList();
                while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                        tmp.add(new Director(rs.getInt(1), rs.getString("name"), 
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
