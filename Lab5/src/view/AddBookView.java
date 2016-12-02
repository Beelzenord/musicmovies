/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBookView extends Stage{
    private Controller controller;
    
    private Label titleL;
    private Label isbnL;
    private Label editionL;
    private Label priceL;
    private Label authorL;
    private TextField title;
    private TextField isbn;
    private TextField edition;
    private TextField price;
    private TextField author;

    private Button addBook;
    private Button cancel;
    
    private GridPane grid;
    private HBox hBox;
    private BorderPane border;
    
    public AddBookView(Controller controller) {
        this.controller = controller;
        initView();
    }
    
    private void initView() {
        // create new labels, textfields and buttons
        initFields();
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
        isbnL = new Label("Isbn:");
        editionL = new Label("Edition:");
        priceL = new Label("Price:");
        authorL = new Label("Authors:");
        Tooltip authorTip= new Tooltip("Use ',' to separate authors!");
        authorL.setTooltip(authorTip);
        
        setBlackLabels();

        title = new TextField();
        isbn = new TextField();
        edition = new TextField();
        price = new TextField();
        author = new TextField();
        author.setTooltip(authorTip);
        
        title.setPromptText("Type title here");
        isbn.setPromptText("Type isbn here");
        edition.setPromptText("Type edition here");
        price.setPromptText("Type price here");
        author.setPromptText("Type authors here");
        
        addBook = new Button("Add Book");
        cancel = new Button("Cancel");
    }
    
    public void clearFields() {
        title.clear();
        isbn.clear();
        edition.clear();
        price.clear();
        author.clear();
    }
    
    private void initGridPane() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 20, 5, 20));
        
        grid.add(titleL, 0, 0);
        grid.add(isbnL, 0, 1);
        grid.add(editionL, 0, 2);
        grid.add(priceL, 0, 3);
        grid.add(authorL, 0, 4);
        
        grid.add(title, 1, 0);
        grid.add(isbn, 1, 1);
        grid.add(edition, 1, 2);
        grid.add(price, 1, 3);
        grid.add(author, 1, 4);
    }
    
    private void initHbox() {
        hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.getChildren().addAll(addBook, cancel);
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
        this.setTitle("Add a new Book");
        this.setScene(scene);
        this.show();
    }
    
    public void setBlackLabels() {
        titleL.setTextFill(Color.BLACK);
        isbnL.setTextFill(Color.BLACK);
        editionL.setTextFill(Color.BLACK);
        priceL.setTextFill(Color.BLACK);
        authorL.setTextFill(Color.BLACK);
    }
    
    public void setRedLabels(int index) {
        if (index == 0)
            titleL.setTextFill(Color.RED);
        else if (index == 1)
            isbnL.setTextFill(Color.RED);
        else if (index == 2)
            editionL.setTextFill(Color.RED);
        else if (index == 3)
            priceL.setTextFill(Color.RED);
        else if (index == 4)
            authorL.setTextFill(Color.RED);
    }
    
    public List<String> getInfo() {
        List<String> tmp = new ArrayList();
        tmp.add(title.getText().trim());
        tmp.add(isbn.getText().trim());
        tmp.add(edition.getText().trim());
        tmp.add(price.getText().trim());
        tmp.add(author.getText().trim());
        
        return tmp;
    }
    
    public void exitStage() {
        clearFields();
        this.close();
    }
    
    private void addEventHandlers() {
        addBook.setOnAction((ActionEvent event) -> {
            controller.handleInput();
        });
        
        cancel.setOnAction((ActionEvent event) -> {
            controller.handleAddBookCancel();
        });
    }
}
