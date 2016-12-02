/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        VBox imageBox = new VBox();
        MainView view = new MainView(primaryStage, imageBox);
        
        root.getChildren().setAll(view, imageBox); 
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}