/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;

import Model.Album;
import Model.DBQueries;
import Model.QueryExecutor;
import Model.Artist;
import Model.Director;
import Model.GetAlbums;
import Model.Model;
import Model.Movie;
import Model.QueryGenerator;
import View.View;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;

/**
 *
 * @author Niklas
 */
public class Controller implements QueryGenerator {
    private ArrayList<DBQueries> artists;
    private ArrayList<QueryGenerator> theQueries;
    private Model model;
    private View view;
    private QueryExecutor exec;
    private int index;
    
    public Controller(Model model, View view) {
        artists = new ArrayList();
        theQueries = new ArrayList();
        index = 0;
        this.model = model;
        this.view = view;
        view.ControllerHook(this);
    }
    
    public void systemAccess() throws SQLException {
        exec = new QueryExecutor(model.getMyConn());
        theQueries.add(new GetAlbums(model.getMyConn()));
        artists.add(exec);
        insertAlbum();
    }
    
    public void handleButton(){
        //test();
        System.out.println("Conasdasdsafirm");
    }
    
    public void transfer(String userName, String passWord) throws SQLException{
        System.out.println("fasdfa<");
        if (model.validateUserIdentity(userName,passWord)) {
            systemAccess();
            // new view of everything
            view.hide();
            artistsByName();
        }
    }
    
    public void confirm(){
        System.out.println("confirm");
    }
    
    
    
    
    public void test() {
        ArrayList<Album> asd = getByTitle("da");
        System.out.println(asd.get(0).toString());
    }
    
    
    @Override
    public ArrayList getByTitle(String title) {
        return theQueries.get(index).getByTitle(title);
    }
    
    @Override
    public ArrayList getByRating(String rating) {
        return theQueries.get(index).getByRating(rating);
    }

    @Override
    public ArrayList getByGenre(String genre) {
        return theQueries.get(index).getByGenre(genre);
    }

    @Override
    public ArrayList getByNationality(String nationality) {
        return theQueries.get(index).getByNationality(nationality);
    }

    @Override
    public ArrayList getByName(String name) {
        return theQueries.get(index).getByName(name);
    }
    
    @Override
    public ArrayList getByArtist(String artist) {
        return theQueries.get(index).getByArtist(artist);
    }

    @Override
    public ArrayList getByAlbum(String album) {
        return theQueries.get(index).getByAlbum(album);
    }

    @Override
    public ArrayList getByDirector(String director) {
        return theQueries.get(index).getByDirector(director);
    }

    @Override
    public ArrayList getByMovie(String movie) {
        return theQueries.get(index).getByMovie(movie);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void exitProgram() {
        System.out.println("exitlul");
    }
    
    /*** GET ARTIST ***/
    public void artistsByName() {
        new Thread() {
            public void run() {
                System.out.println("\nartistsByName");
                String name = "na";
                ArrayList<Artist> tmp;
                tmp = artists.get(0).getArtistsByName(name);
                Platform.runLater(
                        new Runnable() {
                            public void run() {
                                for (Artist a : tmp) {
                                    System.out.println(a.toString());
                                }
                            }
                        });
            }
        }.start();
    }
    
    public void artistsByRating() {
        System.out.println("\nartistsByRating");
        String rating = "11";
        ArrayList<Artist> tmp;
        tmp = artists.get(0).getArtistsByRating(rating);
        for (Artist a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    /*** GET ALBUM ***/
    public void albumByTitle() {
        System.out.println("\nalbumByTitle");
        String title = "horse";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByTitle(title);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByGenre() {
        System.out.println("\nalbumByGenre");
        String genre = "punk";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByGenre(genre);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByRating() {
        System.out.println("\nalbumByRating");
        String rating = "15";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByRating(rating);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void albumByArtist() {
        System.out.println("\nalbumByArtist");
        String artist = "e";
        ArrayList<Album> tmp;
        tmp = artists.get(0).getAlbumsByArtist(artist);
        for (Album a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    /*** GET DIRECTOR ***/
    public void directorsByName() {
        System.out.println("\ndirectorsByName");
        String name = "Mel";
        ArrayList<Director> tmp;
        tmp = artists.get(0).getDirectorsByName(name);
        for (Director a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void directorsByRating() {
        System.out.println("\ndirectorsByRating");
        String rating = "11";
        ArrayList<Director> tmp;
        tmp = artists.get(0).getDirectorsByRating(rating);
        for (Director a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    /*** GET MOVIE.. ***/
    public void movieByTitle() {
        System.out.println("\nmovieByTitle");
        String title = "horse";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByTitle(title);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByGenre() {
        System.out.println("\nmovieByGenre");
        String genre = "punk";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByGenre(genre);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByRating() {
        System.out.println("\nmovieByRating");
        String rating = "15";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByRating(rating);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    public void movieByDirector() {
        System.out.println("\nmovieByDirector");
        String director = "e";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByDirector(director);
        for (Movie a : tmp) {
            System.out.println(a.toString());
        }
    }
    
    /*** ADD ALBUM AND ARTIST ***/
    public void insertAlbum() {
        System.out.println("\ninsertAlbum");
        String title = "secondalbumname";
        String genre = "firstrock";
        String rating = "9";
        Date rDate = new Date(97, 10, 23);
        
        artists.get(0).addNewAlbum(title, genre, rating, rDate);
    }
    
    public void insertArtist() {
        System.out.println("\ninsertArtist");
        String name = "firstartistname";
        String rating = "3";
        artists.get(0).addNewArtist(name, rating);
    }
    
    public void insertAlbumDirectory() {
        System.out.println("\ninsertAlbumDirectory");
        artists.get(0).addNewAlbumDirectory();
    }
    
    /*** ADD MOVIE AND DIRECTOR ***/
    public void insertMovie() {
        System.out.println("\ninsertMovie");
        String title = "yes";
        String genre = "works";
        String rating = "6";
        Date rDate = new Date(97, 10, 23);
        
        artists.get(0).addNewMovie(title, genre, rating, rDate);
    }
    
    public void insertDirector() {
        System.out.println("\ninsertDirector");
        String name = "Mel";
        String rating = "5";
        artists.get(0).addNewDirector(name, rating);
    }
    
    public void insertMovieDirectory() {
        System.out.println("\ninsertMovieDirectory");
        artists.get(0).addNewMovieDirectory();
    }
}

