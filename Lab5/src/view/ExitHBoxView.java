/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ExitHBoxView extends HBox{
    private Button yesButton;
    private Button noButton;
    private Button cancelButton;
    
    public ExitHBoxView(Controller controller){
        super(10);
        
        initView(controller);
    }

    private void initView(Controller controller) {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        
        yesButton = new Button("Save");
        noButton = new Button("Don't save");
        cancelButton = new Button("Cancel");
        yesButton.setPrefWidth(90);
        noButton.setPrefWidth(90);
        cancelButton.setPrefWidth(90);

        this.getChildren().addAll(yesButton, noButton, cancelButton);
        
        addHandlers(controller);
    }
    
    private void addHandlers(Controller controller) {
        yesButton.setOnAction(event ->{
            try {
                controller.closeWithSaving();
            } 
            catch (IOException e) {
            }
        });
        noButton.setOnAction(event ->{
            controller.closeWithoutSaving();
        });
        cancelButton.setOnAction(event ->{
            controller.closeCanceled();
        });
    }
}
