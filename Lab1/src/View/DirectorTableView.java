/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Director;
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
public class DirectorTableView extends TableView implements AllTableViews {
    private TableColumn name;
    private TableColumn rating;
    private TableColumn nationality;
    private TableColumn movie;

    public DirectorTableView() {
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
        movie = new TableColumn("Movie");
        movie.setMinWidth(120);
        
        this.getColumns().addAll(name, rating, nationality, movie);
        name.setCellValueFactory(
            new PropertyValueFactory<Director, String>("name"));
        rating.setCellValueFactory(
            new PropertyValueFactory<Director, String>("rating"));
        nationality.setCellValueFactory(
            new PropertyValueFactory<Director, String>("nationality"));
        movie.setCellValueFactory(
            new PropertyValueFactory<Director, String>("movie"));
    }    
    
    @Override
    public void showTable(ArrayList<? extends Object> theResult) {
        ObservableList<Director> tmpObv;
        tmpObv = FXCollections.observableArrayList((ArrayList<Director>) theResult);
        this.setItems(tmpObv);
    }
    
    @Override
    public int userRating() {
        ObservableList<Director> selectedAritst;

        selectedAritst = this.getSelectionModel().getSelectedItems();
        ArrayList<Director> tmp = new ArrayList();
        for (Director b : selectedAritst) {
            tmp.add(b);
            return tmp.get(0).getDirectorId();
        }
        return -1;
    }
}
