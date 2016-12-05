/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Niklas
 */
public class AddNewMovDirView extends Stage{
    private Controller controller;
    
    private Label titleL;
    private Label genreL;
    private Label ratingAL;
    private Label dateYL;
    private Label dateML;
    private Label dateDL;
    
    private Label nameL;
    private Label ratingML;
    private Label nationalityL;
    
    private TextField title;
    private TextField genre;
    private TextField ratingA;
    private TextField dateY;
    private TextField dateM;
    private TextField dateD;
    
    private TextField name;
    private TextField ratingM;
    private TextField nationality;
    
    private ComboBox<String> ratingAC;
    private ComboBox<String> ratingMC;

    private Button addNew;
    private Button cancel;
    
    private GridPane grid;
    private HBox hBox;
    private BorderPane border;
    
    public AddNewMovDirView(Controller controller) {
        this.controller = controller;
        initView();
    }
    
    private void initView() {
        // create new labels, textfields and buttons
        initFields();
        
        initComboBox();
        
        clearFields();
        
        // create new gridpane
        initGridPane();
        
        // create new hbox
        initHbox();
        
        // create new borderpane
        initBorderPane();

        //event handerls for this stage
        addEventHandlers();
        
        // create the new stage
        initStage();
    }
    
    private void initFields() {
        titleL = new Label("Title:");
        genreL = new Label("Genre:");
        ratingAL = new Label("Rating:");
        dateYL = new Label("Release Year:");
        dateML = new Label("Release Month:");
        dateDL = new Label("Release Day:");
        
        nameL = new Label("Name:");
        ratingML = new Label("Rating:");
        nationalityL = new Label("Nationality:");
        
        setBlackLabels();

        title = new TextField();
        genre = new TextField();
        ratingA = new TextField();
        dateY = new TextField();
        dateM = new TextField();
        dateD = new TextField();
        
        name = new TextField();
        ratingM = new TextField();
        nationality = new TextField();
        
        title.setPromptText("Movie title here");
        genre.setPromptText("Movie genre here");
        ratingA.setPromptText("Movie rating here");
        dateY.setPromptText("Release year (1948-2016)");
        dateM.setPromptText("Release month (1-12)");
        dateD.setPromptText("Release day (1-31)");
        name.setPromptText("Director name here");
        ratingM.setPromptText("Director rating here");
        nationality.setPromptText("Arting nationality here");
        
        addNew = new Button("Add To Database");
        cancel = new Button("Cancel");
    }
    
    private void initComboBox() {
        ratingAC = new ComboBox<>();
        ratingAC.getItems().addAll(
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
        ratingAC.setPromptText("0");
        
        ratingMC = new ComboBox<>();
        ratingMC.getItems().addAll(
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
        ratingMC.setPromptText("0");
    }
    
    public void clearFields() {
        title.clear();
        genre.clear();
        ratingA.clear();
        dateY.clear();
        dateM.clear();
        dateD.clear();
        name.clear();
        ratingM.clear();
        nationality.clear();
    }
    
    private void initGridPane() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 20, 5, 20));
        
        grid.add(titleL, 0, 0);
        grid.add(genreL, 0, 1);
        grid.add(ratingAL, 0, 2);
        grid.add(dateYL, 0, 3);
        grid.add(dateML, 0, 4);
        grid.add(dateDL, 0, 5);
        grid.add(nameL, 0, 6);
        grid.add(ratingML, 0, 7);
        grid.add(nationalityL, 0, 8);
        
        grid.add(title, 1, 0);
        grid.add(genre, 1, 1);
        grid.add(ratingAC, 1, 2);
        grid.add(dateY, 1, 3);
        grid.add(dateM, 1, 4);
        grid.add(dateD, 1, 5);
        grid.add(name, 1, 6);
        grid.add(ratingMC, 1, 7);
        grid.add(nationality, 1, 8);
    }
    
    private void initHbox() {
        hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.getChildren().addAll(addNew, cancel);
    }
    
    private void initBorderPane() {
        border = new BorderPane();
        border.setCenter(grid);
        border.setBottom(hBox);
    }
    
    private void initStage() {
        Scene scene = new Scene(border);
        // user can't interact with any other stage
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        this.setTitle("Add new Movie and Director");
        this.setScene(scene);
        this.show();
    }
    
    public void setBlackLabels() {
        titleL.setTextFill(Color.BLACK);
        genreL.setTextFill(Color.BLACK);
        ratingAL.setTextFill(Color.BLACK);
        dateYL.setTextFill(Color.BLACK);
        dateML.setTextFill(Color.BLACK);
        dateDL.setTextFill(Color.BLACK);
        nameL.setTextFill(Color.BLACK);
        ratingML.setTextFill(Color.BLACK);
        nationalityL.setTextFill(Color.BLACK);
    }
    
    public void setRedLabels(int index) {
        if (index == 0)
            titleL.setTextFill(Color.RED);
        else if (index == 1)
            genreL.setTextFill(Color.RED);
        else if (index == 2)
            ratingAL.setTextFill(Color.RED);
        else if (index == 3)
            dateYL.setTextFill(Color.RED);
        else if (index == 4)
            dateML.setTextFill(Color.RED);
        else if (index == 5)
            dateDL.setTextFill(Color.RED);
        else if (index == 6)
            nameL.setTextFill(Color.RED);
        else if (index == 7)
            ratingML.setTextFill(Color.RED);
        else if (index == 8)
            nationalityL.setTextFill(Color.RED);
    }
    
    public List<String> getInfo() {
        List<String> tmp = new ArrayList();
        tmp.add(title.getText().trim());
        tmp.add(genre.getText().trim());
        if (!ratingAC.getSelectionModel().isEmpty())
            tmp.add(ratingAC.getValue().trim());
        else
            tmp.add("0");
        tmp.add(dateY.getText().trim());
        tmp.add(dateM.getText().trim());
        tmp.add(dateD.getText().trim());
        tmp.add(name.getText().trim());
        if (!ratingMC.getSelectionModel().isEmpty())
            tmp.add(ratingMC.getValue().trim());
        else
            tmp.add("0");
        tmp.add(nationality.getText().trim());
        
        return tmp;
    }
    
    public String getratingAC() {
        return ratingAC.getValue();
    } 
    
    public String getratingMC() {
        return ratingMC.getValue();
    } 
    
    public void exitStage() {
        clearFields();
        this.close();
    }
    
    private void addEventHandlers() {
        addNew.setOnAction((ActionEvent event) -> {
            controller.handleAddNewMovDir();
        });
        
        cancel.setOnAction((ActionEvent event) -> {
            controller.handleAddNewMovDirCancel();
        });
    }
}
