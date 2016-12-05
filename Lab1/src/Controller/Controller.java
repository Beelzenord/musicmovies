/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;

import Model.AddNewAlbArt;
import Model.AddNewMovDir;
import Model.GetAlbums;
import Model.GetArtists;
import Model.GetDirectors;
import Model.GetMovies;
import Model.Movie;
import Model.QueryGenerator;
import Model.InsertGenerator;
import View.AddNewAlbArtView;
import View.AddNewMovDirView;
import View.AlertView;
import View.AllTableViews;
import View.View;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Niklas
 */
public class Controller {
    private ArrayList<QueryGenerator> theQueries;
    private ArrayList<InsertGenerator> theInserts;
    private ArrayList<AllTableViews> theTables;
    private AddNewAlbArtView addNewAlbArtView;
    private AddNewMovDirView addNewMovDirView;
    private AlertView alertView;
    private Connector model;
    private View view;
    private int queryIndex;
    private int insertIndex;
    private String lastSearchBy;
    private String lastSearchWord;
    
    
    public Controller(Connector model, View view) {
        theQueries = new ArrayList();
        theInserts = new ArrayList();
        theTables = new ArrayList();
        this.alertView = new AlertView();
        queryIndex = 0;
        insertIndex = 0;
        lastSearchBy = "album";
        lastSearchWord = "title";
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
        theInserts.add(new AddNewMovDir(model.getMyConn()));
    }
    
    public void connectViews(AllTableViews altw, AllTableViews artw, AllTableViews motw, AllTableViews ditw) {
        System.out.println("CONNECTED VIEWS");
        theTables.add(altw);
        theTables.add(artw);
        theTables.add(motw);
        theTables.add(ditw);
    }
    
    public void handleButton(){
        System.out.println("Conasdasdsafirm");
    }
    
    public void transfer(String userName, String passWord) throws SQLException{
        if (model.validateUserIdentity(userName,passWord)) {
            systemAccess();
        }
    }
    
    public void initViews() {
        System.out.println("initViews");
        view.startMainView();
    }
    
    public void confirm(){
        System.out.println("confirm");
    }
    
    
    public void setQueryIndex(int index) {
        queryIndex = index;
    }
    
    public void setIndexMov(int index) {
        insertIndex = index;
    }
    
    public void search(String searchBy, String searchWord) {
        lastSearchBy = searchBy;
        lastSearchWord = searchWord;
        new Thread() {
            public void run() {
                try {
                    ArrayList tmp = theQueries.get(queryIndex).search(searchBy, searchWord);
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    //UPDATE VIEW
                                    theTables.get(queryIndex).showTable(tmp);
                                    view.changeScene(queryIndex);
                                }
                            });
                } catch (SQLException ex) {
                    // SHOW ALERT MESSAGE
                    Platform.runLater(
                            new Runnable() {
                                public void run() {
                                    alertView.showAlert("Search Failed!\n"
                                        + "You might not be authorized for this action!");    
                                }
                            });
                }
            }
        }.start();
    }
    
    
    
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
    }
    
    public void insertNewItem(String title, String genre, String ratingAM, Date rDate, String name, String ratingAD, String nationality) {
        new Thread() {
            public void run() {
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
            }
        }.start();
    }
    
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
        }
        
        try {
            year = Integer.parseInt(tmp.get(3));
            if (year < 1948 || year > 2016) {
                addNewAlbArtView.setRedLabels(3);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewAlbArtView.setRedLabels(3);
            error = true;
        }
        try {
            month = Integer.parseInt(tmp.get(4));
            if (month < 1 || month > 12) {
                addNewAlbArtView.setRedLabels(4);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewAlbArtView.setRedLabels(4);
            error = true;
        }
        try {
            day = Integer.parseInt(tmp.get(5));
            if (day < 1 || day > 31) {
                addNewAlbArtView.setRedLabels(5);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewAlbArtView.setRedLabels(5);
            error = true;
        }
        try {
            r1 = Integer.parseInt(tmp.get(2));
            if (r1 < 0 || r1 > 9) {
                addNewAlbArtView.setRedLabels(2);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewAlbArtView.setRedLabels(2);
            error = true;
        }
        try {
            r2 = Integer.parseInt(tmp.get(7));
            if (r2 < 0 || r2 > 9) {
                addNewAlbArtView.setRedLabels(7);
                error = true;
            }
        } catch(NumberFormatException e) {
            addNewAlbArtView.setRedLabels(7);
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

            addNewAlbArtView.exitStage();
        }
    }
    
    public void handleAddNewArtAlbCancel() {
        addNewAlbArtView.exitStage();
    }
    
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
    
}

