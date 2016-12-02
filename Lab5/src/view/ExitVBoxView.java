/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class ExitVBoxView extends VBox {
    private Stage stage;
    private String message;
    private Label label;
    private ExitHBoxView exitHBox;
    
    public ExitVBoxView(Controller controller){
        super(10);
        this.stage = new Stage();
        this.message = "Do you wish to save before you Quit?";
        
        initView(controller);
    }

    private void initView(Controller controller) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Save");
        System.out.println(message);
        label = new Label();
        label.setText(message);
        
        exitHBox = new ExitHBoxView(controller);
        

        this.getChildren().addAll(label,exitHBox);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 10, 5, 10));
        
        Scene scene = new Scene(this);
        
        stage.setTitle("Save before exit?");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
}
