/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Movie;
import java.sql.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Niklas
 */
public class MovieTableView extends TableView implements AllTableViews{
    private TableColumn title;
    private TableColumn genre;
    private TableColumn rating;
    private TableColumn date;
    private TableColumn directors;

    public MovieTableView() {
        initView();
    }
    
    private void initView() {
        this.setEditable(true);
        title = new TableColumn("Title");
        title.setMinWidth(100);
        genre = new TableColumn("Genre");
        genre.setMinWidth(80);
        rating = new TableColumn("Rating");
        rating.setMinWidth(80);
        date = new TableColumn("Release Date");
        date.setMinWidth(120);
        directors = new TableColumn("Directors");
        directors.setMinWidth(120);
        
        this.getColumns().addAll(title, genre, rating, date, directors);
        title.setCellValueFactory(
            new PropertyValueFactory<Movie, String>("title"));
        genre.setCellValueFactory(
            new PropertyValueFactory<Movie, String>("genre"));
        rating.setCellValueFactory(
            new PropertyValueFactory<Movie, String>("rating"));
        date.setCellValueFactory(
            new PropertyValueFactory<Movie, Date>("releaseDate"));
        directors.setCellValueFactory(
            new PropertyValueFactory<Movie, ArrayList<String>>("directors"));
        System.out.println("INITIATED");
    }    
    
    @Override
    public void showTable(ArrayList<? extends Object> theResult) {
        ObservableList<Movie> tmpObv;
        tmpObv = FXCollections.observableArrayList((ArrayList<Movie>) theResult);
        this.setItems(tmpObv);
    }
    
    @Override
    public int userRating() {
        ObservableList<Movie> selectedAritst;

        selectedAritst = this.getSelectionModel().getSelectedItems();
        ArrayList<Movie> tmp = new ArrayList();
        for (Movie b : selectedAritst) {
            tmp.add(b);
            return tmp.get(0).getMovieId();
        }
        return -1;
    }
}
