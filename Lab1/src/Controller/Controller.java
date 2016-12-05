/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AddNewAlbArt;
import Model.AddNewMovDir;
import Model.AddNewUser;
import Model.Album;
import Model.DBQueries;
import Model.Artist;
import Model.Director;
import Model.GetAlbums;
import Model.GetArtists;
import Model.GetDirectors;
import Model.GetMovies;
import Model.Movie;
import Model.QueryGenerator;
import Model.InsertGenerator;
<<<<<<< Updated upstream
import View.AddNewAlbArtView;
import View.AddNewMovDirView;
import View.AlertView;
import View.AllTableViews;
=======
import View.ArtistTableView;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private ArrayList<AllTableViews> theTables;
    private AddNewAlbArtView addNewAlbArtView;
    private AddNewMovDirView addNewMovDirView;
    private AlertView alertView;
    private Connector model;
=======
    private ArtistTableView artistTableView;
    private Model model;
>>>>>>> Stashed changes
    private View view;
    private int queryIndex;
    private int insertIndex;
    
    private AddNewUser aNU;
    
<<<<<<< Updated upstream
    public Controller(Connector model, View view) {
=======
    public Controller(Model model, View view) {
        artists = new ArrayList();
>>>>>>> Stashed changes
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
        theQueries.add(new GetMovies(model.getMyConn()));
        theQueries.add(new GetDirectors(model.getMyConn()));
        theInserts.add(new AddNewAlbArt(model.getMyConn()));
<<<<<<< Updated upstream
        theInserts.add(new AddNewMovDir(model.getMyConn()));
    }
    
    public void connectViews(AllTableViews altw, AllTableViews artw, AllTableViews motw, AllTableViews ditw) {
        System.out.println("CONNECTED VIEWS");
        theTables.add(altw);
        theTables.add(artw);
        theTables.add(motw);
        theTables.add(ditw);
=======
        /*theInserts.add(new AddNewMovDir(model.getMyConn()));*/
        //insertAlbum();
    }
    
    public void connectViews(ArtistTableView artistTableView) {
        System.out.println("CONNECTED VIEWS");
        this.artistTableView = artistTableView;
>>>>>>> Stashed changes
    }
    
    public void handleButton(){
        test();
        System.out.println("Conasdasdsafirm");
    }
    
    public void transfer(String userName, String passWord) throws SQLException{
        System.out.println("fasdfa<");
        String actualName = userName;
        if(userName.equals("Ad")){
            System.out.println("your the admin");
        }
        else{
            userName = "guest";
            passWord = "me";
            System.out.println("you are not the admin");
            doIt(userName,passWord,actualName);
        }
        if (model.validateUserIdentity(userName,passWord)) {
            systemAccess();
            //test();
            // new view of everything
            //view.hide();
            //artistsByName();
        }
    }
    public void doIt(String userName, String passWord,String actualName){
        new Thread() {
            public void run() {
                try {
                    
                    Platform.runLater(
                                      new Runnable() {
                        public void run() {
                            try {
                                //UPDATE VIEW while loop?
                                
                                aNU = new AddNewUser(actualName,passWord,model.getMyConn());
                            } catch (SQLException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                } catch (Exception ex) {
                    // SHOW ALERT MESSAGE
                    System.out.println("ooo");
                }
            }
            
        }.start();
    }
    public void initViews() {
        view.startMainView();
    }
    
    public void confirm(){
        System.out.println("confirm");
    }
    
    
<<<<<<< Updated upstream
    public void setQueryIndex(int index) {
        queryIndex = index;
=======
    
    
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    alertView.showAlert("Search Failed!\n"
                                        + "You might not be authorized for this action!");    
                                }
                            });
=======
                    System.out.println("ooo");
>>>>>>> Stashed changes
                }
            }
            
        }.start();
        
        /*ArrayList<Director> asd = theQueries.get(2).search(searchBy, searchWord);
         System.out.println(asd.get(0).toString());
         System.out.println(asd.get(1).toString());*/
    }
    
<<<<<<< Updated upstream
    
    
    public void chooseRating(String rating) {
        int primaryKey = theTables.get(queryIndex).userRating();
        if (primaryKey != -1) {
            new Thread() {
                public void run() {
                    try {
                        theQueries.get(queryIndex).updateRating(primaryKey, rating);
                        Platform.runLater(
                                new Runnable() {
                                    public void run() {
                                        //UPDATE VIEW
                                        search(lastSearchBy, lastSearchWord);
                                    }
                                });
                    } catch (SQLException ex) {
                        // SHOW ALERT MESSAGE
                        Platform.runLater(
                                new Runnable() {
                                    public void run() {
                                        alertView.showAlert("Rating Update Failed!\n"
                                            + "You might not be authorized for this action!");
                                    }
                                });
                    }
                }
            }.start();
        }
=======
    public void insert1(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) throws SQLException {
        theInserts.get(0).addNew(title, genre, ratingAM, rDate, name, ratingAD, nationality);
        
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                try {
                    theInserts.get(insertIndex).addNew(title, genre, ratingAM, rDate, name, ratingAD, nationality);
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    //UPDATE VIEW
                                    search(lastSearchBy, lastSearchWord);
                                }
                            });
                } catch (SQLException ex) {
                    // SHOW ALERT MESSAGE
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    alertView.showAlert("Insert Failed!\n"
                                        + "You might not be authorized for this action!");
                                }
                            });
                }
=======
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
>>>>>>> Stashed changes
            }
        }.start();
    }
    
<<<<<<< Updated upstream
    public void exitProgram() {
        try {
            model.exitSafetly();
            view.fullyQuit();
        } catch (SQLException ex) {
            alertView.showAlert("Could not close connection!");
        }
    }
    
    public void addNewAlbArt() {
        //open addNewAlbArtView view 
        addNewAlbArtView = new AddNewAlbArtView(this);
    }
    
    public void addNewMovDir() {
        //open addNewMovDirView view 
        addNewMovDirView = new AddNewMovDirView(this);
    }
    
    public void handleAddNewArtAlb() {
        addNewAlbArtView.setBlackLabels();
        List<String> tmp = addNewAlbArtView.getInfo();
        Boolean error = false;
        int year = 0;
        int month = 0;
        int day = 0;
        int r1 = 0;
        int r2 = 0;
        
        for (int i = 0; i < 9; i++) {
            if (tmp.get(i).length() <= 0) {
                addNewAlbArtView.setRedLabels(i);
                error = true;
            }
=======
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
>>>>>>> Stashed changes
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
    
<<<<<<< Updated upstream
    public void handleAddNewMovDir() {
        addNewMovDirView.setBlackLabels();
        List<String> tmp = addNewMovDirView.getInfo();
        Boolean error = false;
        int year = 0;
        int month = 0;
        int day = 0;
        int r1 = 0;
        int r2 = 0;
        
        for (int i = 0; i < 9; i++) {
            if (tmp.get(i).length() <= 0) {
                addNewMovDirView.setRedLabels(i);
                error = true;
            }
        }
        
        try {
            year = Integer.parseInt(tmp.get(3));
            if (year < 1948 || year > 2016) {
                addNewMovDirView.setRedLabels(3);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewMovDirView.setRedLabels(3);
            error = true;
=======
    public void movieByDirector() {
        System.out.println("\nmovieByDirector");
        String director = "e";
        ArrayList<Movie> tmp;
        tmp = artists.get(0).getMoviesByDirector(director);
        for (Movie a : tmp) {
            System.out.println(a.toString());
>>>>>>> Stashed changes
        }
        try {
            month = Integer.parseInt(tmp.get(4));
            if (month < 1 || month > 12) {
                addNewMovDirView.setRedLabels(4);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewMovDirView.setRedLabels(4);
            error = true;
        }
        try {
            day = Integer.parseInt(tmp.get(5));
            if (day < 1 || day > 31) {
                addNewMovDirView.setRedLabels(5);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewMovDirView.setRedLabels(5);
            error = true;
        }
        try {
            r1 = Integer.parseInt(tmp.get(2));
            if (r1 < 0 || r1 > 9) {
                addNewMovDirView.setRedLabels(2);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewMovDirView.setRedLabels(2);
            error = true;
        }
        try {
            r2 = Integer.parseInt(tmp.get(7));
            if (r2 < 0 || r2 > 9) {
                addNewMovDirView.setRedLabels(7);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewMovDirView.setRedLabels(7);
            error = true;
        }
        
        
        if (error) {
            alertView.showAlert("Make sure you fill all fields!\n"
                + "Make sure you enter a valid Date!");
        }
        else {
            //year,mone -> Date
            Date rDate = new Date(year-1900, month-1, day);
            insertNewItem(tmp.get(0), tmp.get(1), tmp.get(2), rDate, tmp.get(6), tmp.get(7), tmp.get(8));

            addNewMovDirView.exitStage();
        }
    }
    
    public void handleAddNewMovDirCancel() {
        addNewMovDirView.exitStage();
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
    public void launchNewUserDetail(String name, String pass) throws SQLException{
        //aNU = new AddNewUser(name,pass,model.getMyConn());
        new Thread() {
            public void run() {
                try {
                    //  ArrayList asd = theQueries.get(queryIndex).search(searchBy, searchWord);
                    Platform.runLater(
                                      new Runnable() {
                        public void run() {
                            try {
                                //UPDATE VIEW while loop?
                                System.out.println("Will Carver");
                                // aNU = new AddNewUser(name,pass,model);
                            } catch (Exception ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
                catch (Exception ex) {
                    // SHOW ALERT MESSAGE
                    System.out.println("ooo");
                }
            }
            
        }.start();
        
    }
    
    
    
}

