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
import View.AlbumTableView;
import View.AllTableViews;
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
    private ArrayList<AllTableViews> theTables;
    //private ArtistTableView artistTableView;
    //private AlbumTableView albumTableView;
    private Model model;
    private View view;
    private int queryIndex;
    private int insertIndex;
    
    public Controller(Model model, View view) {
        artists = new ArrayList();
        theQueries = new ArrayList();
        theInserts = new ArrayList();
        
        queryIndex = 0;
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
    
    public void connectViews(AllTableViews artw, AllTableViews altw) {
        System.out.println("CONNECTED VIEWS");
        theTables = new ArrayList();
        theTables.add(altw);
        theTables.add(artw);
        
    }
    
    public void handleButton(){
        test();
        System.out.println("Conasdasdsafirm");
    }
    
    public void transfer(String userName, String passWord) throws SQLException{
        System.out.println("fasdfa<");
        if (model.validateUserIdentity(userName,passWord)) {
            systemAccess();
            test();
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
    
    
    public void setIndex(int index) {
        queryIndex = index;
    }
    
    public void setIndexMov(int index) {
        insertIndex = index;
    }
    
    public void test() {
    }
    
    public void search(String searchBy, String searchWord) {
        new Thread() {
            public void run() {
                try {
                    ArrayList asd = theQueries.get(queryIndex).search(searchBy, searchWord);
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    //UPDATE VIEW while loop?
                                    System.out.println(asd.get(0).toString());
                                    theTables.get(queryIndex).showTable(asd);
                                    view.changeScene(queryIndex);
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
    
    public void addNew() {
        //open add view (insertIndex)
    }
    
    public void chooseRating(String rating) {
        
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
    

    

    
    
    
    public void exitProgram() {
        System.out.println("exitlul");
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

