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
public class GetDirectors implements QueryGenerator {
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByMoviePrep;
    
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
        String byDirector = "SELECT * FROM T_Movie WHERE "
                + "movieId IN (SELECT movieId FROM T_MovieDirectory WHERE "
                + "directorId IN (SELECT directorId FROM T_Director WHERE "
                + "name LIKE ?))";
        searchByMoviePrep = con.prepareStatement(byDirector);
    }
    
    
    @Override
    public ArrayList search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("movie")) {
            try {
                createSearchByMoviePrep();
                searchByMoviePrep.setString(1, "%" + searchWord + "%");
                rs = searchByMoviePrep.executeQuery();
                ArrayList<Director> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
                            rs.getString("rating"), rs.getString("nationality")));
                }
                return tmp;
            } finally {
                if (rs != null)
                    rs.close();
                if (searchByMoviePrep != null)
                    searchByMoviePrep.close();
            }
        }
        else {
            try {
                createSearchPrep(searchBy);
                searchPrep.setString(1, "%" + searchWord + "%");
                rs = searchPrep.executeQuery();
                ArrayList<Director> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
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
}
