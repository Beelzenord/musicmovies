/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;

import Model.AddNewAlbArt;
import Model.AddNewMovDir;
import Model.Album;
import Model.DBQueries;
import Model.Artist;
import Model.Director;
import Model.GetAlbums;
import Model.GetArtists;
import Model.GetDirectors;
import Model.GetMovies;
import Model.Model;
import Model.Movie;
import Model.QueryGenerator;
import Model.InsertGenerator;
import View.ArtistTableView;
import View.View;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Niklas
 */
public class Controller {
    private ArrayList<DBQueries> artists;
    private ArrayList<QueryGenerator> theQueries;
    private ArrayList<InsertGenerator> theInserts;
    private ArtistTableView artistTableView;
    private Model model;
    private View view;
    private int queryIndex;
    private int insertIndex;
    
    public Controller(Model model, View view) {
        artists = new ArrayList();
        theQueries = new ArrayList();
        theInserts = new ArrayList();
        
        queryIndex = 1;
        insertIndex = 0;
        this.model = model;
        this.view = view;
        view.ControllerHook(this);
    }
    
    public void systemAccess() throws SQLException {
        theQueries.add(new GetAlbums(model.getMyConn()));
        theQueries.add(new GetArtists(model.getMyConn()));
        //theQueries.add(new GetMovies(model.getMyConn()));
        //theQueries.add(new GetDirectors(model.getMyConn()));
        theInserts.add(new AddNewAlbArt(model.getMyConn()));
        /*theInserts.add(new AddNewMovDir(model.getMyConn()));*/
        //insertAlbum();
    }
    
    public void connectViews(ArtistTableView artistTableView) {
        System.out.println("CONNECTED VIEWS");
        this.artistTableView = artistTableView;
    }
    
    public void handleButton(){
        test();
        System.out.println("Conasdasdsafirm");
    }
    
    public void transfer(String userName, String passWord) throws SQLException{
        System.out.println("fasdfa<");
        if (model.validateUserIdentity(userName,passWord)) {
            systemAccess();
            //test();
            // new view of everything
            //view.hide();
            //artistsByName();
        }
    }
    
    public void initViews() {
        view.startMainView();
    }
    
    public void confirm(){
        System.out.println("confirm");
    }
    
    
    
    
    public void test() {
        try {
            //ArrayList<Album> asd = getByTitle("da");
            searcher3("name", "d");
            //Date d = new Date(85, 4, 14);
            //insert1("oai", "rock", "3", d, "asd", "1", "qwe");
        } catch (SQLException ex) {
            System.out.println("EVERYBODY GO SHIT FUCK");
        }
        //System.out.println(asd.get(0).toString());
    }
    
    public void searcher(String searchBy, String searchWord) throws SQLException {
        ArrayList<Album> asd = theQueries.get(0).search(searchBy, searchWord);
        System.out.println(asd.get(0).toString());
    }
    
    public void searcher2(String searchBy, String searchWord) throws SQLException {
        ArrayList<Artist> asd = theQueries.get(1).search(searchBy, searchWord);
        System.out.println(asd.get(0).toString());
        System.out.println(asd.get(1).toString());
    }
    
    public void searcher3(String searchBy, String searchWord) throws SQLException {
        new Thread() {
            public void run() {
                try {
                    ArrayList asd = theQueries.get(queryIndex).search(searchBy, searchWord);
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    //UPDATE VIEW while loop?
                                    System.out.println(asd.get(0).toString());
                                    System.out.println(asd.get(1).toString());
                                    artistTableView.showTable(asd);
                                }
                            });
                } catch (SQLException ex) {
                    // SHOW ALERT MESSAGE
                    System.out.println("ooo");
                }
            }
                
        }.start();
    
        /*ArrayList<Director> asd = theQueries.get(2).search(searchBy, searchWord);
        System.out.println(asd.get(0).toString());
        System.out.println(asd.get(1).toString());*/
    }
    
    public void insert1(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        theInserts.get(0).addNew(title, genre, ratingAM, rDate, name, ratingAD, nationality);
    
    }
    
    /*@Override
    public ArrayList getByTitle(String title) {
        ArrayList<Object> tmp = null;
        new Thread() {
            public void run() {
                try {
                    ArrayList tmp = theQueries.get(queryIndex).getByTitle(title);
                } catch (SQLException ex) {
                    // ALERT MESSAGE 
                }
                Platform.runLater(
                        new Runnable() {
                            public void run() {
                                //UPDATE VIEW with tmp
                                
                            }
                        });
            }
        }.start();
        return theQueries.get(queryIndex).getByTitle(title);
    }*/
    

    
        /*new Thread() {
            public void run() {
                try {

                } catch (SQLException ex) {
                    // ALERT MESSAGE 
                }
                Platform.runLater(
                        new Runnable() {
                            public void run() {
                                //UPDATE VIEW
                                
                            }
                        });
            }
        }.start();
    */
    
    
    
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

