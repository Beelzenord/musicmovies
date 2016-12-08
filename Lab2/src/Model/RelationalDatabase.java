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
public class RelationalDatabase implements AllDatabaseQueries{
    private Connection con;
    private PreparedStatement searchMediaPrep;
    private PreparedStatement searchEntertainerPrep;
    private PreparedStatement insertMediaPrep;
    private PreparedStatement insertEntertainerPrep;
    private PreparedStatement insertNewDirectoryPrep;
    private PreparedStatement skipDuplicatesPrep;
    private PreparedStatement updateRatingPrep;
    
    private int mediaPKEY;
    private int entertainerPKEY;
    
    public RelationalDatabase(Connection con) {
        this.con = con;
    }
    
    private void createSearchMediaPrep(String media, String searchBy) throws SQLException {
        String prepState;
        if (media.equals("album")) {
            if (searchBy.equals("artist")) {
                prepState = "SELECT T_Album.*, T_Artist.name " +
                            "FROM T_Album, T_Artist, T_Albumdirectory " +
                            "WHERE T_Album.albumId = T_Albumdirectory.albumId AND " +
                            "T_artist.artistId = T_Albumdirectory.artistId AND " +
                            "T_Artist.name LIKE ?;";
            }
            else {
                prepState = "SELECT T_Album.*, T_Artist.name " +
                            "FROM T_Album, T_Artist, T_Albumdirectory " +
                            "WHERE T_Album.albumId = T_Albumdirectory.albumId AND " +
                            "T_artist.artistId = T_Albumdirectory.artistId AND " +
                            "T_Album."+searchBy+" LIKE ?;";
            }
        }
        else {
            if (searchBy.equals("director")) {
                prepState = "SELECT T_Movie.*, T_Director.name " +
                            "FROM T_Movie, T_Director, T_Moviedirectory " +
                            "WHERE T_Movie.movieId = T_Moviedirectory.movieId AND " +
                            "T_director.directorId = T_Moviedirectory.directorId AND " +
                            "T_Director.name LIKE ?;";
            }
            else {
                prepState = "SELECT T_Movie.*, T_Director.name " +
                            "FROM T_Movie, T_Director, T_Moviedirectory " +
                            "WHERE T_Movie.movieId = T_Moviedirectory.movieId AND " +
                            "T_director.directorId = T_Moviedirectory.directorId AND " +
                            "T_Movie."+searchBy+" LIKE ?;";
            }
        }
        searchMediaPrep = con.prepareStatement(prepState);
    }
    
    private void createSearchEntertainerPrep(String entertainer, String searchBy) throws SQLException {
        String prepState;
        if (entertainer.equals("artist")) {
            if (searchBy.equals("album")) {
                prepState = "SELECT T_Artist.*, T_Album.title " +
                            "FROM T_Artist, T_Album, T_Albumdirectory " +
                            "WHERE T_Album.albumId = T_Albumdirectory.albumId AND " +
                            "T_artist.artistId = T_Albumdirectory.artistId AND " +
                            "T_Album.title LIKE ?;";
            }
            else {
                prepState = "SELECT T_Artist.*, T_Album.title " +
                            "FROM T_Artist, T_Album, T_Albumdirectory " +
                            "WHERE T_Album.albumId = T_Albumdirectory.albumId AND " +
                            "T_artist.artistId = T_Albumdirectory.artistId AND " +
                            "T_Artist."+searchBy+" LIKE ?;";
            }
        }
        else {
            if (searchBy.equals("movie")) {
                prepState = "SELECT T_Director.*, T_Movie.title " +
                            "FROM T_Director, T_Movie, T_Moviedirectory " +
                            "WHERE T_Movie.movieId = T_Moviedirectory.movieId AND " +
                            "T_director.directorId = T_Moviedirectory.directorId AND " +
                            "T_Movie.title LIKE ?;";
            }
            else {
                prepState = "SELECT T_Director.*, T_Movie.title " +
                            "FROM T_Director, T_Movie, T_Moviedirectory " +
                            "WHERE T_Movie.movieId = T_Moviedirectory.movieId AND " +
                            "T_director.directorId = T_Moviedirectory.directorId AND " +
                            "T_Director."+searchBy+" LIKE ?;";
            }
        }
        searchEntertainerPrep = con.prepareStatement(prepState);
    }
    
    @Override
    public ArrayList getMedia(String media, String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchMediaPrep(media, searchBy);
            searchMediaPrep.setString(1, "%" + searchWord + "%");
            rs = searchMediaPrep.executeQuery();
            ArrayList tmp = new ArrayList();
            if (media.equals("album")) {
                while (rs.next()) {
                    tmp.add(new Album(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getDate(5), rs.getString(6)));
                }
            }
            else if (media.equals("movie")) {
                while (rs.next()) {
                    tmp.add(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getDate(5), rs.getString(6)));
                }
            }
            return tmp;
        } finally {
            if (rs != null)
                rs.close();
            if (searchMediaPrep != null)
                searchMediaPrep.close();
        }
    }

    @Override
    public ArrayList getEntertainer(String entertainer, String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchEntertainerPrep(entertainer, searchBy);
            searchEntertainerPrep.setString(1, "%" + searchWord + "%");
            rs = searchEntertainerPrep.executeQuery();
            ArrayList tmp = new ArrayList();
            if (entertainer.equals("artist")) {
                while (rs.next()) {
                    tmp.add(new Artist(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5)));
                }
            }
            else if (entertainer.equals("director")) {
                while (rs.next()) {
                    tmp.add(new Director(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5)));
                }
            }
            return tmp;
        } finally {
            if (rs != null)
                rs.close();
            if (searchMediaPrep != null)
                searchMediaPrep.close();
        }
    }

    
    
    /**
     * Creates a Prepared statement to insert a new album into the database
     * @throws SQLException 
     */
    private void createInsertMediaPrep(String determ) throws SQLException {
        // prepared statement for insert new Media
        String newMedia = "";
        if (determ.equals("album")) {
            newMedia = "INSERT INTO T_Album (title, genre, rating, "
                    + "releaseDate) VALUES(?, ?, ?, ?)";
        }
        else {
            newMedia = "INSERT INTO T_Movie (title, genre, rating, "
                    + "releaseDate) VALUES(?, ?, ?, ?)";
        }
        insertMediaPrep = con.prepareStatement(newMedia, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert a new artist into the database
     * @throws SQLException 
     */
    private void createInsertEntertainerPrep(String determ) throws SQLException {
        // prepared statement for insert new Entertainer
        String newEntertainer = "";
        if (determ.equals("album")) {
            newEntertainer = "INSERT INTO T_Artist (name, rating, nationality)"
                    + "VALUES(?, ?, ?)"; 
        }
        else {
            newEntertainer = "INSERT INTO T_Director (name, rating, nationality)"
                    + "VALUES(?, ?, ?)"; 
        }
        insertEntertainerPrep = con.prepareStatement(newEntertainer, Statement.RETURN_GENERATED_KEYS);
    }
    
    /**
     * Creates a Prepared statement to insert the new album and the 
     * new artist into the connection table 
     * @throws SQLException 
     */
    private void createInsertNewDirectoryPrep(String determ) throws SQLException {
        // preparted statement for insert new artistsAlbum
        String newDirectory = "";
        if (determ.equals("album")) {
            newDirectory = "INSERT INTO T_AlbumDirectory (artistId, albumId)"
                    + "VALUES(?, ?)";
        }
        else {
            newDirectory = "INSERT INTO T_MovieDirectory (directorId, movieId)"
                    + "VALUES(?, ?)";
        }
        insertNewDirectoryPrep = con.prepareStatement(newDirectory);
    }
    
    
    @Override
    public void addNewItem(String determ, String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        try {
            con.setAutoCommit(false);
                ResultSet rs = null;
                try {
                    createInsertMediaPrep(determ);
                    
                    insertMediaPrep.setString(1, title);
                    insertMediaPrep.setString(2, genre);
                    insertMediaPrep.setString(3, ratingAM);
                    insertMediaPrep.setDate(4, rDate);
                    insertMediaPrep.executeUpdate();
                    rs = insertMediaPrep.getGeneratedKeys();
                    while (rs.next())
                        mediaPKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertMediaPrep != null)
                        insertMediaPrep.close();
                }
         
                entertainerPKEY = skipDuplicates(determ, name, ratingAD, nationality);
            if (entertainerPKEY == -1) {
                try {
                    createInsertEntertainerPrep(determ);
                    insertEntertainerPrep.setString(1, name);
                    insertEntertainerPrep.setString(2, ratingAD);
                    insertEntertainerPrep.setString(3, nationality);
                    
                    insertEntertainerPrep.executeUpdate();
                    rs = insertEntertainerPrep.getGeneratedKeys();
                    while (rs.next())
                        entertainerPKEY = rs.getInt(1);
                } finally {
                    if (rs != null)
                        rs.close();
                    if (insertEntertainerPrep != null)
                        insertEntertainerPrep.close();
                }
            }

            // sambandstabell
            try {
                createInsertNewDirectoryPrep(determ);
                insertNewDirectoryPrep.setInt(1, entertainerPKEY);
                insertNewDirectoryPrep.setInt(2, mediaPKEY);
                insertNewDirectoryPrep.executeUpdate();
            } finally {
                if (insertNewDirectoryPrep != null)
                    insertNewDirectoryPrep.close();
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
    
    private void createSkipDuplicatesPrep(String determ) throws SQLException {
        String skipDuplicates = "";
        if (determ.equals("album")) {
            skipDuplicates = "SELECT artistId FROM T_Artist WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        }
        else {
            skipDuplicates = "SELECT directorId FROM T_Director WHERE name = ? "
                + "AND rating = ? AND nationality = ?";
        }
        skipDuplicatesPrep = con.prepareStatement(skipDuplicates);
    }
    
    @Override
    public int skipDuplicates(String determ, String name, String rating, String nationality) throws SQLException {
        ResultSet rs = null;
        int pKey = -1;
        try {
            createSkipDuplicatesPrep(determ);
            skipDuplicatesPrep.setString(1, name);
            skipDuplicatesPrep.setString(2, rating);
            skipDuplicatesPrep.setString(3, nationality);
            rs = skipDuplicatesPrep.executeQuery();
            while (rs.next()) {
                pKey = rs.getInt(1);
            }
            return pKey;
        } finally {
            if (rs != null)
                rs.close();
            if (skipDuplicatesPrep != null)
                skipDuplicatesPrep.close();
        }
    }
    
    private void createUpdateRatingPrep(String item) throws SQLException {
        String updateRating = "UPDATE T_" + item + " SET rating = ? "
                + "WHERE " + item + "Id = ?";
        updateRatingPrep = con.prepareStatement(updateRating);
    }
    
    @Override
    public void updateRating(String item, int primaryKey, String rating) throws SQLException {
        try {
            createUpdateRatingPrep(item);
            updateRatingPrep.setString(1, rating);
            updateRatingPrep.setInt(2, primaryKey);
            updateRatingPrep.executeUpdate();
        } finally {
            if (updateRatingPrep != null)
                updateRatingPrep.close();
        }
    }
    

    
}
