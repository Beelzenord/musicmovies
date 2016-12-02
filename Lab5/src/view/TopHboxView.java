/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopHboxView extends HBox {
    private Controller controller;
    private Button addBook;
    private Button removeBook;
    private Button quickSave;
    private Button refresh;

    public TopHboxView(Controller controller) {
        super(30);
        this.controller = controller;
        initView();
        addEventHandlers();
    }
    
    private void initView() {
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 5, 10));
        
        addBook = new Button("Add Book");
        removeBook = new Button("Remove Book");
        quickSave = new Button("Save");
        refresh = new Button("Refresh");
        
        addBook.setMinWidth(90);
        removeBook.setMinWidth(90);
        quickSave.setMinWidth(90);
        refresh.setMinWidth(90);

        this.getChildren().addAll(addBook, removeBook, refresh, quickSave);
    }
    
    private void addEventHandlers() {
        
        // med lambda expressions
        addBook.setOnAction((ActionEvent event) -> {
            controller.addBook();
        });
        
        removeBook.setOnAction((ActionEvent event) -> {
            controller.removeBook();
        });
        
        refresh.setOnAction((ActionEvent event) -> {
            controller.refresh();
        });
        
        quickSave.setOnAction((ActionEvent event) -> {
            try {
                controller.saveToFile();
            } catch (IOException ex) {
            }
        });
    }
}

