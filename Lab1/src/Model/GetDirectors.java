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
public class GetDirectors implements QueryGenerator {
    private Connection con;
    private PreparedStatement getDirectorByName;
    private PreparedStatement getDirectorByRating;    
    private PreparedStatement getDirectorByNationality;    
    private PreparedStatement getDirectorByMovie;    

    public GetDirectors(Connection con) {
        this.con = con;
        initPreparedStatements();
    }
    
    private void initPreparedStatements() {
        try {
        // prepared statement for getDirectorByName
        String byDirectorName = "SELECT * FROM T_Director WHERE name LIKE ?";
        getDirectorByName = con.prepareStatement(byDirectorName);
        
        // prepared statement for getDirectorByRating
        String byDirectorRating = "SELECT * FROM T_Director WHERE rating = ?";
        getDirectorByRating = con.prepareStatement(byDirectorRating);
        
        // prepared statement for getDirectorByNationality
        String byDirectorNationality = "SELECT * FROM T_Director WHERE nationality = ?";
        getDirectorByNationality = con.prepareStatement(byDirectorNationality);
        
        // prepared statement for getMovieByDirector
        String byDirectorMovie = "SELECT * FROM T_Director WHERE "
                + "directorId IN (SELECT directorId FROM T_MovieDirectory WHERE "
                + "movieId IN (SELECT movieId FROM T_Movie WHERE "
                + "title LIKE ?))";
        getDirectorByMovie = con.prepareStatement(byDirectorMovie);
        
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
            getDirectorByRating.setString(1, rating);
            ResultSet rs = getDirectorByRating.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorsByRating Didn't select anything");
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
            getDirectorByNationality.setString(1, nationality);
            ResultSet rs = getDirectorByNationality.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorsByNationality Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByName(String name) {
        try {
            getDirectorByName.setString(1, "%" + name + "%");
            ResultSet rs = getDirectorByName.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorsByName Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByArtist(String artist) {
        return null;
    }

    @Override
    public ArrayList getByAlbum(String album) {
        return null;
    }

    @Override
    public ArrayList getByDirector(String director) {
        return null;
    }

    @Override
    public ArrayList getByMovie(String movie) {
        try {
            getDirectorByMovie.setString(1, "%" + movie + "%");
            ResultSet rs = getDirectorByMovie.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getInt("directorId"), rs.getString("name"), 
                        rs.getString("rating"), rs.getString("nationality")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorByMovie Didn't select anything");
            return null;
        }
    }
}
