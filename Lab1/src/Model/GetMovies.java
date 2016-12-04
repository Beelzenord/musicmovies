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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class GetMovies implements QueryGenerator {
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByDirectorPrep;

    public GetMovies(Connection con) {
        this.con = con;
    }
    
    private void createSearchPrep(String searchBy) throws SQLException {
        String prepState;
        if (searchBy.equals("title"))
            prepState = "SELECT * FROM T_Movie WHERE title LIKE ?;";
        else if (searchBy.equals("genre"))
            prepState = "SELECT * FROM T_Movie WHERE genre LIKE ?;";
        else
            prepState = "SELECT * FROM T_Movie WHERE rating LIKE ?;";
        
        String as = "SELECT * FROM T_Movie WHERE ? LIKE ?";
        searchPrep = con.prepareStatement(prepState);
    }
    
    private void createSearchByDirectorPrep() throws SQLException {
        String byDirector = "SELECT * FROM T_Movie WHERE "
                + "movieId IN (SELECT movieId FROM T_MovieDirectory WHERE "
                + "directorId IN (SELECT directorId FROM T_Director WHERE "
                + "name LIKE ?))";
        searchByDirectorPrep = con.prepareStatement(byDirector);
    }
    
    @Override
    public ArrayList<Movie> search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        System.out.println("a");
        if (searchBy.equals("director")) {
            try {
                createSearchByDirectorPrep();
                searchByDirectorPrep.setString(1, "%" + searchWord + "%");
                rs = searchByDirectorPrep.executeQuery();
                ArrayList<Movie> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                        rs.getString("rating"), rs.getDate("releaseDate")));
                }
                return tmp;
            } finally {
                if (rs != null)
                    rs.close();
                if (searchByDirectorPrep != null)
                    searchByDirectorPrep.close();
            }
        }
        else {
            try {
                createSearchPrep(searchBy);
                searchPrep.setString(1, "%" + searchWord + "%");
                
                rs = searchPrep.executeQuery();
                ArrayList<Movie> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                        rs.getString("rating"), rs.getDate("releaseDate")));
                }
                return tmp;
            }  finally {
                if (rs != null)
                    rs.close();
                if (searchPrep != null)
                    searchPrep.close();
            }
        }
    }
    
}