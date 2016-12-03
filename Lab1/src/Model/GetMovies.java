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
public class GetMovies implements QueryGenerator {
    private Connection con;
    private PreparedStatement getMovieByTitle;
    private PreparedStatement getMovieByGenre;
    private PreparedStatement getMovieByRating;
    private PreparedStatement getMovieByDirector;

    public GetMovies(Connection con) {
        this.con = con;
        
        initPreparedStatements();
    }
    
    private void initPreparedStatements() {
        try {
        // prepared statement for getMovieByTitle
        String byMovieTitle = "SELECT * FROM T_Movie WHERE title LIKE ?";
        getMovieByTitle = con.prepareStatement(byMovieTitle);
        
        // prepared statement for getMovieByGenre
        String byMovieGenre = "SELECT * FROM T_Movie WHERE genre LIKE ?";
        getMovieByGenre = con.prepareStatement(byMovieGenre);
        
        // prepared statement for getMovieByRating
        String byMovieRating = "SELECT * FROM T_Movie WHERE rating LIKE ?";
        getMovieByRating = con.prepareStatement(byMovieRating);
        
        // prepared statement for getMovieByDirector
        String byMovieDirector = "SELECT * FROM T_Movie WHERE "
                + "movieId IN (SELECT movieId FROM T_MovieDirectory WHERE "
                + "directorId IN (SELECT directorId FROM T_Director WHERE "
                + "name LIKE ?))";
        getMovieByDirector = con.prepareStatement(byMovieDirector);
        
        } catch (SQLException ex) {
            System.out.println("what to do here? Prepared "
                    + "statements of getAlbum crashed");
        }
    }

    @Override
    public ArrayList getByTitle(String title) {
        try {
            getMovieByTitle.setString(1, "%" + title + "%");
            ResultSet rs = getMovieByTitle.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByTitle Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByRating(String rating) {
        try {
            getMovieByRating.setString(1, "%" + rating + "%");
            ResultSet rs = getMovieByRating.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByRating Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByGenre(String genre) {
        try {
            getMovieByGenre.setString(1, "%" + genre + "%");
            ResultSet rs = getMovieByGenre.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByGenre Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByNationality(String nationality) {
        return null;
    }

    @Override
    public ArrayList getByName(String name) {
        return null;
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
        try {
            getMovieByDirector.setString(1, "%" + director + "%");
            ResultSet rs = getMovieByDirector.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByDirector Didn't select anything");
            return null;
        }
    }

    @Override
    public ArrayList getByMovie(String movie) {
        return null;
    }
    
}