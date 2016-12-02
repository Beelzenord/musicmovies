/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SearchFieldView extends HBox {
    private Controller controller;
    private TextField searchField;
    private ComboBox<String> searchComboBox;
    private Button searchButton;
    
    public SearchFieldView(Controller controller) {
        super(30);
        this.controller = controller;
        
        initView();
    }
    
    
    
    private void initView() {
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(5, 10, 10, 10));
        
        searchField = new TextField();
        searchField.setPromptText("Type here to search then press  --->");
        searchButton = new Button("Search");
        
        searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll(
                "Title",
                "ISBN",
                "Author"
        );
        searchComboBox.setPromptText("Title");
        
        searchComboBox.setPrefWidth(90);
        searchField.setPrefWidth(210);
        searchButton.setPrefWidth(90);
        
        addHandlers();
        
        this.getChildren().addAll(searchComboBox, searchField, searchButton);

    }
    
    public String getComboText() {
        return searchComboBox.getValue();
    } 
    
    public String getSearched() {
        return searchField.getText();
    }
    
    private void addHandlers() {
        searchButton.setOnAction((ActionEvent event) -> {
            controller.searchBook(getComboText(), getSearched());
        });
    }
}
