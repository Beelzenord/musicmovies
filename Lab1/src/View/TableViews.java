/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Artist;
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
public class TableViews extends TableView implements AllTableViews {
    private ObservableList<Artist> observList;
    private TableColumn name;
    private TableColumn rating;
    private TableColumn nationality;
    private TableColumn album;

    public TableViews() {
        
    }
    
    private void initView() {
        this.setEditable(true);
        name = new TableColumn("Name");
        name.setMinWidth(100);
        rating = new TableColumn("Eating");
        rating.setMinWidth(80);
        nationality = new TableColumn("Eationality");
        nationality.setMinWidth(80);
        album = new TableColumn("Album");
        album.setMinWidth(80);
        
        this.getColumns().addAll(name, rating, nationality, album);
        name.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("name"));
        rating.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("rating"));
        nationality.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("nationality"));
        album.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("album"));
        
        //observBooks = FXCollections.observableArrayList(library.getList());
    }    
    
    public void showTable(ArrayList<Artist> tmpArtists) {
        ObservableList<Artist> tmpObvBooks;
        tmpObvBooks = FXCollections.observableArrayList(tmpArtists);
        this.setItems(tmpObvBooks);
    }
    
    /*public void refresh() {
        observList.removeAll(observList);
        observList.addAll(library.getList());
        this.setItems(observList);
    }*/
}
