/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 *
 * @author Niklas
 */
public class BotHboxView extends HBox{
    private Controller controller;
    private ComboBox<String> chooseRating;
    private Button addAlbumArtist;
    private Button addMovieDirector;
    private Button rate;
    
    public BotHboxView(Controller controller) {
        super(30);
        this.controller = controller;
        
        initView();
    }
    
    
    
    private void initView() {
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(5, 10, 10, 10));
        
        addAlbumArtist = new Button("Add Album and Artist");
        addMovieDirector = new Button("Add Movie and Director");
        rate = new Button ("Choose rating");
        
        chooseRating = new ComboBox<>();
        chooseRating.getItems().addAll(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9"
        );
        chooseRating.setPromptText("0");
        
        addAlbumArtist.setPrefWidth(200);
        addMovieDirector.setPrefWidth(200);
        chooseRating.setPrefWidth(90);
        rate.setPrefWidth(150);
        
        addHandlers();
        
        this.getChildren().addAll(addAlbumArtist, addMovieDirector, chooseRating, rate);
    }
    
    
    public String getChooseRating() {
        return chooseRating.getValue();
    } 
    
    private void addHandlers() {
        addAlbumArtist.setOnAction((ActionEvent event) -> {
            controller.addNewAlbArt();
        });
        
        addMovieDirector.setOnAction((ActionEvent event) -> {
            controller.addNewMovDir();
        });
        
        rate.setOnAction((ActionEvent event) -> {
            String rating = getChooseRating();
            controller.updateRating(rating);
        });
    }
    
}