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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class GetAlbums implements QueryGenerator{
    private Connection con;
    private PreparedStatement searchPrep;
    private PreparedStatement searchByArtistPrep;

    public GetAlbums(Connection con) {
        this.con = con;
    }
    
    private void createSearchPrep(String searchBy) throws SQLException {
        String prepState;
        if (searchBy.equals("title"))
            prepState = "SELECT * FROM T_Album WHERE title LIKE ?;";
        else if (searchBy.equals("genre"))
            prepState = "SELECT * FROM T_Album WHERE genre LIKE ?;";
        else
            prepState = "SELECT * FROM T_Album WHERE rating LIKE ?;";
        
        String as = "SELECT * FROM T_Album WHERE ? LIKE ?";
        searchPrep = con.prepareStatement(prepState);
    }
    
    private void createSearchByArtistPrep() throws SQLException {
        String byArtist = "SELECT * FROM T_Album WHERE "
                + "albumId IN (SELECT albumId FROM T_AlbumDirectory WHERE "
                + "artistId IN (SELECT artistId FROM T_Artist WHERE "
                + "name LIKE ?))";
        searchByArtistPrep = con.prepareStatement(byArtist);
    }
    
    @Override
    public ArrayList<Album> search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        System.out.println("a");
        if (searchBy.equals("artist")) {
            try {
                createSearchByArtistPrep();
                searchByArtistPrep.setString(1, "%" + searchWord + "%");
                rs = searchByArtistPrep.executeQuery();
                ArrayList<Album> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
                        rs.getString("rating"), rs.getDate("releaseDate")));
                }
                return tmp;
            } finally {
                if (rs != null)
                    rs.close();
                if (searchByArtistPrep != null)
                    searchByArtistPrep.close();
            }
        }
        else {
            try {
                createSearchPrep(searchBy);
                searchPrep.setString(1, "%" + searchWord + "%");
                
                rs = searchPrep.executeQuery();
                ArrayList<Album> tmp = new ArrayList();
                while (rs.next()) {
                    tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"), 
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
