/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import externalfile.SaveAndLoad;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import model.Book;
import model.CollectionOfBooks;
import view.AddBookView;
import view.AlertView;
import view.SaveAnimationView;
import view.CenterTableView;
import view.ExitVBoxView;
import view.FileChooserView;

public class Controller {
    private CollectionOfBooks library;
    private AddBookView addBookView;
    private CenterTableView centerTableView;
    private FileChooserView fileChooserView;
    private SaveAndLoad saveAndLoad;
    private ExitVBoxView exitView;
    private Stage stage;
    private SaveAnimationView saveAniView;
    private AlertView alertView;
    
    public Controller(CollectionOfBooks library, 
            CenterTableView centerTableView, FileChooserView fileChooserView, 
            Stage stage, SaveAnimationView saveAniView) {
        this.library = library;
        this.centerTableView = centerTableView;
        this.fileChooserView = fileChooserView;
        this.stage = stage;
        this.saveAniView = saveAniView;
        saveAndLoad = new SaveAndLoad();
        alertView = new AlertView();
    }
    
    public void addBook() {
        addBookView = new AddBookView(this);
    }
    
    public boolean handleInput() {
        addBookView.setBlackLabels();
        List<String> tmp = addBookView.getInfo();
        Boolean error = false;
        Double price = 0.0;
        int edition = 0;
        for (int i = 0; i < 5; i++) {
            if (tmp.get(i).length() <= 0) {
                addBookView.setRedLabels(i);
                error = true;
            }
        }
        
        try {
            edition = Integer.parseInt(tmp.get(2));
            if (edition < 0) {
                addBookView.setRedLabels(2);
                error = true;
            }
        }
        catch(NumberFormatException e) {
            addBookView.setRedLabels(2);
            error = true;
        }
        try {
            price = Double.parseDouble(tmp.get(3));
            if (price < 0) {
                addBookView.setRedLabels(3);
                error = true;
            }
        }
        catch(NumberFormatException e) {
            addBookView.setRedLabels(3);
            error = true;
        }
        
        if (error) {
            alertView.showAlert("Make sure you fill all fields!\n"
                + "Make sure Edition and Price are Positive numbers!");
            return false;
        }
        else {
            String tmpS = tmp.get(4);
            String author = "";
            ArrayList<String> authors = new ArrayList();
            
            for (int i = 0; i < tmpS.length(); i++) {
                if (tmpS.charAt(i) != ',') {
                    author += tmpS.charAt(i);
                }
                else {
                    authors.add(author.trim());
                    author = "";
                } 
            }
            if (author.length() != 0)
                authors.add(author.trim());
            
            System.out.println(author.length());
            library.addBook(new Book(tmp.get(0), tmp.get(1), edition, price, authors));
            
            addBookView.exitStage();
            return true;
        }
    }
    
    public void handleAddBookCancel() {
        addBookView.exitStage();
    }
    
    public void removeBook() {
        ArrayList<Book> tmp = centerTableView.removeBook();
        for (Book b : tmp) {
            library.removeBook(b);
        }
    }
    
    public void searchBook(String searchedFor, String searched) {
        if ("Title".equals(searchedFor))
            centerTableView.setSearchedList(library.searchByTitle(searched));
        else if("ISBN".equals(searchedFor)) 
            centerTableView.setSearchedList(library.searchByIsbn(searched));
        else if("Author".equals(searchedFor)) 
            centerTableView.setSearchedList(library.searchByAuthor(searched));
        else
            centerTableView.setSearchedList(library.searchByTitle(searched));
            
    }
    
    public void refresh() {
        centerTableView.refresh();
    }
        
    public void exitProgram(){
        exitView = new ExitVBoxView(this);
    }
    
    public void closeWithSaving() throws IOException {
        saveToFile();
        exitView.close();
        stage.close();
    }
    
    public void closeWithoutSaving() {
        exitView.close();
        stage.close();
    }
    
    public void closeCanceled() {
        exitView.close();
    }
    
    public void saveToFile() throws IOException {
        String path = fileChooserView.saveToFile();
        if (path != null) {
            if (saveAndLoad.objectOutput(path, library))
                saveAniView.startAnimation();
            else
                alertView.showAlert("File did not save!");
        }
        else
            alertView.showAlert("File did not save!");
    }
    
    public void saveAsToFile() throws IOException {
        String path = fileChooserView.saveAsToFile();
        if (path != null) {
            if (saveAndLoad.objectOutput(path, library))
                saveAniView.startAnimation();
            else
                alertView.showAlert("File did not save!");
        }
        else
            alertView.showAlert("File did not save!");
    }
        
    public void loadFromFile() throws IOException {
        String path = fileChooserView.loadFromFile();
        if (path != null) {
            if (saveAndLoad.objectInput(path) != null) {
                library.setBooks(saveAndLoad.objectInput(path));
                centerTableView.refresh();
            }
            else
                alertView.showAlert("File did not load!");
        }
        else 
            alertView.showAlert("File did not load!");
    }
}
