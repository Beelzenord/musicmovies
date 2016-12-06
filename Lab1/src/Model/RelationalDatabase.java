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
public class RelationalDatabase implements AllQueries{
    private Connection con;
    private PreparedStatement searchMediaPrep;
    private PreparedStatement searchEntertainerPrep;
    
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

    
    
    
    

    
}
