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
public class ArtistTableView extends TableView implements AllTableViews {
    private TableColumn name;
    private TableColumn rating;
    private TableColumn nationality;
    private TableColumn album;

    public ArtistTableView() {
        initView();
    }
    
    private void initView() {
        this.setEditable(true);
        name = new TableColumn("Name");
        name.setMinWidth(100);
        rating = new TableColumn("Rating");
        rating.setMinWidth(80);
        nationality = new TableColumn("Nationality");
        nationality.setMinWidth(80);
        album = new TableColumn("Album");
        album.setMinWidth(120);
        
        this.getColumns().addAll(name, rating, nationality, album);
        name.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("name"));
        rating.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("rating"));
        nationality.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("nationality"));
        album.setCellValueFactory(
            new PropertyValueFactory<Artist, String>("album"));
    }    
    
    @Override
    public void showTable(ArrayList<? extends Object> theResult) {
        ObservableList<Artist> tmpObv;
        tmpObv = FXCollections.observableArrayList((ArrayList<Artist>) theResult);
        this.setItems(tmpObv);
    }
    
    @Override
    public int userRating() {
        ObservableList<Artist> selectedAritst;

        selectedAritst = this.getSelectionModel().getSelectedItems();
        ArrayList<Artist> tmp = new ArrayList();
        for (Artist b : selectedAritst) {
            tmp.add(b);
            return tmp.get(0).getArtistId();
        }
        return -1;
    }
    
}
