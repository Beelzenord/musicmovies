/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Album;
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
public class AlbumTableView extends TableView implements AllTableViews{
    private TableColumn title;
    private TableColumn genre;
    private TableColumn rating;
    private TableColumn date;
    private TableColumn artists;

    public AlbumTableView() {
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
        artists = new TableColumn("Artist");
        artists.setMinWidth(120);
        
        this.getColumns().addAll(title, genre, rating, date, artists);
        title.setCellValueFactory(
            new PropertyValueFactory<Album, String>("title"));
        genre.setCellValueFactory(
            new PropertyValueFactory<Album, String>("genre"));
        rating.setCellValueFactory(
            new PropertyValueFactory<Album, String>("rating"));
        date.setCellValueFactory(
            new PropertyValueFactory<Album, Date>("releaseDate"));
        artists.setCellValueFactory(
            new PropertyValueFactory<Album, String>("artist"));
    }    
    
    @Override
    public void showTable(ArrayList<? extends Object> theResult) {
        ObservableList<Album> tmpObv;
        tmpObv = FXCollections.observableArrayList((ArrayList<Album>) theResult);
        this.setItems(tmpObv);
    }
    
    @Override
    public int userRating() {
        ObservableList<Album> selected;

        selected = this.getSelectionModel().getSelectedItems();
        ArrayList<Album> tmp = new ArrayList();
        for (Album b : selected) {
            tmp.add(b);
            return tmp.get(0).getAlbumId();
        }
        return -1;
    }
}