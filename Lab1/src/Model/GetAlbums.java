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
    private PreparedStatement searchByAllPrep;
    private PreparedStatement searchByPkeyPrep;
    
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
    
    private void createSearchByAllPrep() throws SQLException {
        String byAll = "SELECT * FROM T_Album WHERE title = ? "
                + "AND genre = ? AND rating = ? AND releaseDate = ?";
        searchByAllPrep = con.prepareStatement(byAll);
    }
    
    private void createSearchByPkeyPrep() throws SQLException {
        String byKey = "SELECT name FROM T_Artist WHERE artistId = "
                + "(SELECT artistId FROM T_AlbumDirectory WHERE "
                + "albumId = ?)";
        searchByPkeyPrep = con.prepareStatement(byKey);
    }
    
    @Override
    public ArrayList<Album> search(String searchBy, String searchWord) throws SQLException {
        ResultSet rs = null;
        if (searchBy.equals("artist")) {
            try {
                createSearchByArtistPrep();
                searchByArtistPrep.setString(1, "%" + searchWord + "%");
                rs = searchByArtistPrep.executeQuery();
                ArrayList<Album> tmp = new ArrayList();
                while (rs.next()) {
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    
                    tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"),
                            rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
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
                    System.out.println("aaa");
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    System.out.println("ttt");
                    tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"),
                            rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
                    System.out.println("eee");
                    System.out.println(tmp.get(0).toString());
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
    
    @Override
    public ArrayList searchByAll(String search1, String search2, String search3, Date search4) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchByAllPrep();
            searchByAllPrep.setString(1, search1);
            searchByAllPrep.setString(2, search2);
            searchByAllPrep.setString(3, search3);
            searchByAllPrep.setDate(4, search4);
            rs = searchByAllPrep.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                    ArrayList<String> tmp3 = searchByPkey(rs.getInt(1));
                    tmp.add(new Album(rs.getInt("albumId"), rs.getString("title"), rs.getString("genre"),
                            rs.getString("rating"), rs.getDate("releaseDate"), tmp3));
            }
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
    }
    
    private ArrayList<String> searchByPkey(int pKey) throws SQLException {
        ResultSet rs = null;
        try {
            createSearchByPkeyPrep();
            //searchByPkeyPrep.clearParameters();
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
    
}
