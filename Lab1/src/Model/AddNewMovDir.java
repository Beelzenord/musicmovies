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

/**
 *
 * @author Niklas
 */
public class AddNewMovDir implements InsertGenerator {
    private Connection con;
    private PreparedStatement insertMovie;
    private PreparedStatement insertDirector;
    private PreparedStatement insertMovieDirectory;
    private int moviePKEY;
    private int directorPKEY;
    
    public AddNewMovDir(Connection con)  throws SQLException{
        this.con = con;
    }
    
    /**
     * Creates a Prepared statement to insert a new movie into the database
     * @throws SQLException 
     */
    private void createInsertMoviePrep() throws SQLException {
        // prepared statement for insert new movie
        String newMovie = "INSERT INTO T_Movie (title, genre, rating, "
                + "releaseDate) VALUES(?, ?, ?, ?)";
        insertMovie = con.prepareStatement(newMovie, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert a new director into the database
     * @throws SQLException 
     */
    private void createInsertDirectorPrep() throws SQLException {
        // prepared statement for insert new director
        String newDirector = "INSERT INTO T_Director (name, rating, nationality)"
                + "VALUES(?, ?, ?)"; // get primary key and insert nationality
        insertDirector = con.prepareStatement(newDirector, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert the new movie and the 
     * new director into the connection table 
     * @throws SQLException 
     */
    private void createInsertMovieDirectoryPrep() throws SQLException {
        // preparted statement for insert new directorsMovie
        String newMovieDirectory = "INSERT INTO T_MovieDirectory (directorId, movieId)"
                + "VALUES(?, ?)";
        insertMovieDirectory = con.prepareStatement(newMovieDirectory);
    }
    
    /**
     * This will add a new movie and a new director to the database 
     * @param title The title of the movie
     * @param genre The genre of the movie
     * @param ratingAM The rating of the movie
     * @param rDate The release date of the movie
     * @param name The name of the director
     * @param ratingAD The rating of the director
     * @param nationality The nationality of the director
     * @throws SQLException 
     */
    @Override
    public void addNew(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        try {
            // only add new movie if it doesn't already exist
            ArrayList<Movie> tmp1 = new GetMovies(con).searchByAll(title, genre, ratingAM, rDate);
            
            con.setAutoCommit(false);
            if (tmp1 == null) {
                ResultSet rs = null;
                try {
                    createInsertMoviePrep();
                    insertMovie.setString(1, title);
                    insertMovie.setString(2, genre);
                    insertMovie.setString(3, ratingAM);
                    insertMovie.setDate(4, rDate);
                    insertMovie.executeUpdate();
                    rs = insertMovie.getGeneratedKeys();
                    while (rs.next())
                        moviePKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertMovie != null)
                        insertMovie.close();
                }
            }
            else
                moviePKEY = tmp1.get(0).getMovieId();
            
            // only add new director if it doesn't already exist
            ArrayList<Director> tmp2 = new GetDirectors(con).searchByAll(name, ratingAD, nationality, rDate);
            
            con.setAutoCommit(false);
            if (tmp2 == null) {
                ResultSet rs = null;
                try {
                    createInsertDirectorPrep();
                    insertDirector.setString(1, name);
                    insertDirector.setString(2, ratingAD);
                    insertDirector.setString(3, nationality);
                    
                    insertDirector.executeUpdate();
                    rs = insertDirector.getGeneratedKeys();
                    while (rs.next())
                        directorPKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertDirector != null)
                        insertDirector.close();
                }
            }
            else
                directorPKEY = tmp2.get(0).getDirectorId();
            
            // sambandstabell
            try {
                createInsertMovieDirectoryPrep();
                insertMovieDirectory.setInt(1, directorPKEY);
                insertMovieDirectory.setInt(2, moviePKEY);
                insertMovieDirectory.executeUpdate();
            } finally {
                if (insertMovieDirectory != null)
                    insertMovieDirectory.close();
            }
            
            
            con.commit();
            
        }catch (SQLException ex) {
            if (con != null)
                con.rollback();
            throw ex;
        }
        finally {
            con.setAutoCommit(true);
        }
    }
    
}
