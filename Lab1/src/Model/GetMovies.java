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
public class GetMovies implements QueryGenerator {
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByDirectorPrep;
    private PreparedStatement searchByAllPrep;
    private PreparedStatement searchByPkeyPrep;
    private PreparedStatement updateRatingPrep;
    
    public GetMovies(Connection con) {
        this.con = con;
    }
    
    /** 
     * Creates a PreparedStatement to search for a movie by 
     * either title, genre or rating.
     * @param searchBy How to search the database.
     * @throws SQLException 
     */
    private void createSearchPrep(String searchBy) throws SQLException {
        String prepState;
        if (searchBy.equals("title"))
            prepState = "SELECT * FROM T_Movie WHERE title LIKE ?;";
        else if (searchBy.equals("genre"))
            prepState = "SELECT * FROM T_Movie WHERE genre LIKE ?;";
        else
            prepState = "SELECT * FROM T_Movie WHERE rating LIKE ?;";
        searchPrep = con.prepareStatement(prepState);
    }
    
    /** 
     * Creates a PreparedStatement to search for a movie by director.
     * @throws SQLException 
     */
    private void createSearchByDirectorPrep() throws SQLException {
        String byDirector = "SELECT * FROM T_Movie WHERE "
                + "movieId IN (SELECT movieId FROM T_MovieDirectory WHERE "
                + "directorId IN (SELECT directorId FROM T_Director WHERE "
                + "name LIKE ?))";
        searchByDirectorPrep = con.prepareStatement(byDirector);
    }
    
    /** 
     * Creates a PreparedStatement to search for a movie by 
     * title, genre, rating and release date.
     * @throws SQLException 
     */
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Movie WHERE title = ? "
                + "AND genre = ? AND rating = ? AND releaseDate = ?";
        searchByAllPrep = con.prepareStatement(byAll);
    }
    
    /** Creates a PreparedStatement to search for a director by 
     * selected movies primary key.
     * @throws SQLException 
     */
    private void createSearchByPkeyPrep() throws SQLException {
        String byKey = "SELECT name FROM T_Director WHERE directorId IN "
                + "(SELECT directorId FROM T_MovieDirectory WHERE "
                + "movieId = ?)";
        searchByPkeyPrep = con.prepareStatement(byKey);
    }
    
    /** 
     * Creates a PreparedStatement to rate a movie 
     * @throws SQLException 
     */
    private void createUpdateRatingPrep() throws SQLException {
        String updateRating = "UPDATE T_Movie SET rating = ? "
                + "WHERE movieId = ?";
        updateRatingPrep = con.prepareStatement(updateRating);
    }
    
    /** 
     * This will search the database and return a list of Movies
     * @param searchBy How to search the database, by name, genre, rating, director
     * @param searchWord The word the user is search for 
     * @return An ArrayList<Movie> 
     * @throws SQLException 
     */
    @Override
    public ArrayList<Movie> search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("director")) {
            try {
                con.setAutoCommit(false);
                try {
                    createSearchByDirectorPrep();
                    searchByDirectorPrep.setString(1, "%" + searchWord + "%");
                    rs = searchByDirectorPrep.executeQuery();
                    ArrayList<Movie> tmp = new ArrayList();
                    while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));

                        tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"),
                                rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
                    }
                    con.commit();
                    return tmp;
                } finally {
                    if (rs != null)
                        rs.close();
                    if (searchByDirectorPrep != null)
                        searchByDirectorPrep.close();
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
                    ArrayList<Movie> tmp = new ArrayList();
                    while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                        tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"),
                                rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
                    }
                    con.commit();
                    return tmp;
                }  finally {
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
     * This will search the database and return a list of Movies
     * @param search1 The movies title
     * @param search2 The movies genre
     * @param search3 The movies rating
     * @param search4 The movies date
     * @return An ArrayList<Movie> 
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
                searchByAllPrep.setDate(4, search4);
                rs = searchByAllPrep.executeQuery();
                ArrayList<Movie> tmp = new ArrayList();
                while (rs.next()) {
                        ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                        tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"),
                                rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
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
     * This will return an arrayList<String> containing all the names
     * of all the directors who has a movie with that name.
     * @param pKey The primary key of the movie.
     * @return An ArrayList<String> 
     * @throws SQLException 
     */
    private ArrayList<String> searchByPkey(int pKey) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchByPkeyPrep();
            searchByPkeyPrep.setInt(1, pKey);
            rs = searchByPkeyPrep.executeQuery();
            ArrayList<String> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(rs.getString("name"));
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
     * This will update the rating of selected movie.
     * @param primaryKey The primary key of selected movie. 
     * @param rating The new rating for the movie.
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