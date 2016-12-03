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
public class QueryExecutor implements DBQueries {
    private final Connection con;
    private int albumPKEY;
    private int artistPKEY;
    private int moviePKEY;
    private int directorPKEY;
    
    private PreparedStatement getArtistByName;
    private PreparedStatement getArtistByRating;
    private PreparedStatement getArtistByAll;
    private PreparedStatement getAlbumByTitle;
    private PreparedStatement getAlbumByGenre;
    private PreparedStatement getAlbumByRating;
    private PreparedStatement getAlbumByArtist;
    private PreparedStatement getAlbumByAll;
    
    private PreparedStatement insertAlbum;
    private PreparedStatement insertArtist;
    private PreparedStatement insertAlbumDirectory;
    
    private PreparedStatement insertMovie;
    private PreparedStatement insertDirector;
    private PreparedStatement insertMovieDirectory;
    private PreparedStatement getMovieByTitle;
    private PreparedStatement getMovieByGenre;
    private PreparedStatement getMovieByRating;
    private PreparedStatement getMovieByDirector;
    private PreparedStatement getDirectorByName;
    private PreparedStatement getDirectorByRating;    
    private String raing;
    
    public QueryExecutor(Connection con) throws SQLException {
        this.con = con;
        initPrimaryStatements();
    }
    
    private void initPrimaryStatements() throws SQLException {
        /*** GET ARTIST ***/
        // prepared statement for getArtistByName
        String byArtistName = "SELECT * FROM T_Artist WHERE name LIKE ?";
        getArtistByName = con.prepareStatement(byArtistName);
        
        // prepared statement for getArtistByRating
        String byArtistRating = "SELECT * FROM T_Artist WHERE rating = ?";
        getArtistByRating = con.prepareStatement(byArtistRating);
        
        String byArtistAll = "SELECT * FROM T_Artist WHERE name = ? "
                + "AND rating = ?";
        getArtistByAll = con.prepareStatement(byArtistAll, Statement.RETURN_GENERATED_KEYS);
        
        /*** INSERT NEW ALBUM AND ARTIST ***/
        // prepared statement for insert new album
        String newAlbum = "INSERT INTO T_Album (title, genre, rating, " 
            + "releaseDate) VALUES(?, ?, ?, ?)";
        insertAlbum = con.prepareStatement(newAlbum, Statement.RETURN_GENERATED_KEYS);
   
        // prepared statement for insert new artist
        String newArtist = "INSERT INTO T_Artist (name, rating)" 
            + "VALUES(?, ?)"; // get primary key and insert nationality
        
        //testa med IIN här och LIKE8not9
        //String newArtist = "NSERT INTO T_Artist (name, rating)" 
         //   + "VALUES(?, ?, ?) WHEN NOT EXISTS (SELECT fName FROM T_Artist" 
         //       + "WHERE fName = ?)";
        //String newArtist = "BEGIN IF NOT EXISTS (SELECT * FROM T_Artist "
        //        + "WHERE fName = ?) "
        //        + "BEGIN INSERT INTO T_Artist (name, rating) " 
        //        + "VALUES(?, ?, ?) END END";
        
                
        insertArtist = con.prepareStatement(newArtist, Statement.RETURN_GENERATED_KEYS);
        
        // preparted statement for insert new artistsAlbum
        String newAlbumDirectory = "INSERT INTO T_AlbumDirectory (artistId, albumId)" 
            + "VALUES(?, ?)";
        insertAlbumDirectory = con.prepareStatement(newAlbumDirectory);
        
        /*** GET ALBUM BY.. ***/
        // prepared statement for getAlbumByTitle
        String byAlbumTitle = "SELECT * FROM T_Album WHERE title LIKE ?";
        getAlbumByTitle = con.prepareStatement(byAlbumTitle);
        
        // prepared statement for getAlbumByGenre
        String byAlbumGenre = "SELECT * FROM T_Album WHERE genre LIKE ?";
        getAlbumByGenre = con.prepareStatement(byAlbumGenre);
        
        // prepared statement for getAlbumByRating
        String byAlbumRating = "SELECT * FROM T_Album WHERE rating LIKE ?";
        getAlbumByRating = con.prepareStatement(byAlbumRating);
        
        // prepared statement for getAlbumByArtist
        String byAlbumArtist = "SELECT * FROM T_Album WHERE "
                + "albumId IN (SELECT albumId FROM T_AlbumDirectory WHERE "
                + "artistId IN (SELECT artistId FROM T_Artist WHERE "
                + "name LIKE ?))";
        getAlbumByArtist = con.prepareStatement(byAlbumArtist);
        
        // prepared statement for getAlbumsByAll
        String byAlbumAll = "SELECT * FROM T_Album WHERE title = ? "
                + "AND genre = ? AND rating = ? AND releaseDate = ?";
        getAlbumByAll = con.prepareStatement(byAlbumAll, Statement.RETURN_GENERATED_KEYS);
        
        /*** GET MOVIE BY.. ***/
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
        
        /*** INSERT MOVIES AND DIRECTORS ***/
        // prepared statement for insert new movie
        String newMovie = "INSERT INTO T_Movie (title, genre, rating, " 
            + "releaseDate) VALUES(?, ?, ?, ?)";
        insertMovie = con.prepareStatement(newMovie, Statement.RETURN_GENERATED_KEYS);
   
        // prepared statement for insert new director
        String newDirector = "INSERT INTO T_Director (name, rating)" 
            + "VALUES(?, ?)"; // get primary key and insert nationality
        insertDirector = con.prepareStatement(newDirector, Statement.RETURN_GENERATED_KEYS);
        
        // preparted statement for insert new directorsMovie
        String newDirectorsMovie = "INSERT INTO T_MovieDirectory (directorId, movieId)" 
            + "VALUES(?, ?)";
        insertMovieDirectory = con.prepareStatement(newDirectorsMovie);
        
        /*** GER DIRECTOR ***/
        // prepared statement for getDirectorByName
        String byDirectorName = "SELECT * FROM T_Director WHERE name LIKE ?";
        getDirectorByName = con.prepareStatement(byDirectorName);
        
        // prepared statement for getDirectorByRating
        String byDirectorRating = "SELECT * FROM T_Director WHERE rating = ?";
        getDirectorByRating = con.prepareStatement(byDirectorRating);
    }
    
    @Override
    public ArrayList<Artist> getArtistsByName(String name) {
        try {
            getArtistByName.setString(1, "%" + name + "%");
            ResultSet rs = getArtistByName.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("name"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistsByName Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Artist> getArtistsByRating(String rating) {
        try {
            getArtistByRating.setString(1, rating);
            ResultSet rs = getArtistByRating.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("name"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getArtistsByRating Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Artist> getArtistsByAll(String name, String rating) {
        try {
            getArtistByAll.setString(1, name);
            getArtistByAll.setString(2, rating);
            ResultSet rs = getArtistByAll.executeQuery();
            ArrayList<Artist> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Artist(rs.getString("name"), rs.getString("rating")));
                artistPKEY = rs.getInt(1);
            }
            if (tmp.size() > 0)
                return tmp;
            else
                return null;
        } catch (SQLException ex) {
            System.out.println("getArtistsByAll Didn't select anything");
            return null;
        }
    }

    /*** ADD ALBUM AND ARTIST ***/
    @Override
    public void addNewAlbum(String title, String genre, String rating, Date rDate) {
        ArrayList<Album> tmp = getAlbumsByAll(title, genre, rating, rDate);
        if (tmp == null) {
            try {
                insertAlbum.setString(1, title);
                insertAlbum.setString(2, genre);
                insertAlbum.setString(3, rating);
                insertAlbum.setDate(4, rDate);
                insertAlbum.executeUpdate();
                ResultSet rs = insertAlbum.getGeneratedKeys();
                while (rs.next())
                    albumPKEY = rs.getInt(1);
            } catch (SQLException ex) {
                // show alert message and redo
                System.out.println("addNewAlbum Didn't select anything");
            }
        }
    }

    @Override
    public void addNewArtist(String name, String rating) {
        ArrayList<Artist> tmp = getArtistsByAll(name, rating);
        if (tmp == null) {
            try {
                insertArtist.setString(1, name);
                insertArtist.setString(2, rating);

                insertArtist.executeUpdate();
                ResultSet rs = insertArtist.getGeneratedKeys();
                while (rs.next())
                    artistPKEY = rs.getInt(1);
            } catch (SQLException ex) {
                // show alert message and redo
                System.out.println("addNewArtist Didn't select anything");
            }
        }
    }

    @Override
    public void addNewAlbumDirectory() {
        try {
            System.out.println("arPKEY" + artistPKEY);
            System.out.println("alPKEY" + albumPKEY);
            insertAlbumDirectory.setInt(1, artistPKEY);
            insertAlbumDirectory.setInt(2, albumPKEY);
            insertAlbumDirectory.executeUpdate();
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("addNewAlbumDirectory Didn't select anything");
        }
    }


    
    /*** GET ALBUM.. ***/
    @Override
    public ArrayList<Album> getAlbumsByTitle(String title) {
        try {
            getAlbumByTitle.setString(1, "%" + title + "%");
            ResultSet rs = getAlbumByTitle.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByTitle Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByGenre(String genre) {
        try {
            getAlbumByGenre.setString(1, "%" + genre + "%");
            ResultSet rs = getAlbumByGenre.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByGenre Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByRating(String rating) {
        try {
            getAlbumByRating.setString(1, "%" + rating + "%");
            ResultSet rs = getAlbumByRating.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByRating Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByArtist(String artist) {
        try {
            getAlbumByArtist.setString(1, "%" + artist + "%");
            ResultSet rs = getAlbumByArtist.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByArtist Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Album> getAlbumsByAll(String title, String genre, String rating, Date rDate) {
        try {
            getAlbumByAll.setString(1, title);
            getAlbumByAll.setString(2, genre);
            getAlbumByAll.setString(3, rating);
            getAlbumByAll.setDate(4, rDate);
            ResultSet rs = getAlbumByAll.executeQuery();
            ArrayList<Album> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Album(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
                albumPKEY = rs.getInt(1);
            }
            if (tmp.size() > 0)
                return tmp;
            else
                return null;
        } catch (SQLException ex) {
            System.out.println("getAlbumsByAll Didn't select anything");
            return null;
        }
    }
    
    /*** ADD MOVIES DIRECTOR ***/
    @Override
    public void addNewMovie(String title, String genre, String rating, Date rDate) {
        try {
            insertMovie.setString(1, title);
            insertMovie.setString(2, genre);
            insertMovie.setString(3, rating);
            insertMovie.setDate(4, rDate);
            insertMovie.executeUpdate();
            ResultSet rs = insertMovie.getGeneratedKeys();
            while (rs.next())
                moviePKEY = rs.getInt(1);
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("addNewMovie Didn't select anything");
        }
    }

    @Override
    public void addNewDirector(String name, String rating) {
        try {
            insertDirector.setString(1, name);
            insertDirector.setString(2, rating);
            
            insertDirector.executeUpdate();
            ResultSet rs = insertDirector.getGeneratedKeys();
            while (rs.next())
                directorPKEY = rs.getInt(1);
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("addNewDirector Didn't select anything");
        }
    }

    @Override
    public void addNewMovieDirectory() {
        try {
            insertMovieDirectory.setInt(1, directorPKEY);
            insertMovieDirectory.setInt(2, moviePKEY);
            insertMovieDirectory.executeUpdate();
        } catch (SQLException ex) {
            // show alert message and redo
            System.out.println("addNewMovieDirectory Didn't select anything");
        }
    }
    
    /*** GET MOVIE.. ***/
    @Override
    public ArrayList<Movie> getMoviesByTitle(String title) {
        try {
            getMovieByTitle.setString(1, "%" + title + "%");
            ResultSet rs = getMovieByTitle.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByTitle Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Movie> getMoviesByGenre(String genre) {
        try {
            getMovieByGenre.setString(1, "%" + genre + "%");
            ResultSet rs = getMovieByGenre.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByGenre Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Movie> getMoviesByRating(String rating) {
        try {
            getMovieByRating.setString(1, "%" + rating + "%");
            ResultSet rs = getMovieByRating.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByRating Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Movie> getMoviesByDirector(String director) {
        try {
            getMovieByDirector.setString(1, "%" + director + "%");
            ResultSet rs = getMovieByDirector.executeQuery();
            ArrayList<Movie> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Movie(rs.getString("title"), rs.getString("genre"), 
                    rs.getString("rating"), rs.getDate("releaseDate")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getMoviesByDirector Didn't select anything");
            return null;
        }
    }
    
    /*** GET DIRECTOR ***/
    @Override
    public ArrayList<Director> getDirectorsByName(String name) {
        try {
            getDirectorByName.setString(1, "%" + name + "%");
            ResultSet rs = getDirectorByName.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getString("name"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorsByName Didn't select anything");
            return null;
        }
    }
    
    @Override
    public ArrayList<Director> getDirectorsByRating(String rating) {
        try {
            getDirectorByRating.setString(1, rating);
            ResultSet rs = getDirectorByRating.executeQuery();
            ArrayList<Director> tmp = new ArrayList();
            while (rs.next()) {
                tmp.add(new Director(rs.getString("name"), rs.getString("rating")));
            }
            return tmp;
        } catch (SQLException ex) {
            System.out.println("getDirectorsByRating Didn't select anything");
            return null;
        }
    }
    
}
