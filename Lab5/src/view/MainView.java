/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.CollectionOfBooks;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends VBox {
    private CollectionOfBooks library;
    private Stage stage;
    private Controller controller;
    
    public MainView(Stage stage, VBox imageBox) {
        this.stage = stage;
        
        initView(imageBox);
    }
    
    private void initView(VBox imageBox) {
        library = new CollectionOfBooks();
        SaveAnimationView saveAniView = new SaveAnimationView(imageBox);
        CenterTableView centerTable = new CenterTableView(library);
        FileChooserView fileChooser = new FileChooserView(stage);
        this.controller = new Controller(library, centerTable, 
                fileChooser, stage, saveAniView);
        
        TopHboxView topHbox = new TopHboxView(controller);
        SearchFieldView searchField = new SearchFieldView(controller);
        MenuFieldView menuField = new MenuFieldView(controller);
        
        library.addObserver(centerTable);
        
        this.setPrefSize(470, 420);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 0, 0));
        this.getChildren().addAll(menuField, topHbox, searchField, centerTable);
        this.setVgrow(centerTable, Priority.ALWAYS);
        
        saveBeforeQuit();
    }
    
    private void saveBeforeQuit(){
        stage.setOnCloseRequest(event -> {
            event.consume();
            controller.exitProgram();
        });
    }
}
