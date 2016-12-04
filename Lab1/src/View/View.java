
package View;
import Controller.Controller;
import Model.Model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author fauzianordlund
 */
public class View {
   
    private Model model;
    private BorderPane bPane;
    private StackPane sPane2;
    private Scene scene;
    private Scene scene2;
    private Controller cont;
    private Stage stage2;
    
    private GridPane grid;
    
    private CenterTableView cView;
    private TextField theUserName;
    private PasswordField pwBox;
    
    private TopHboxView hBox;
    
    private LogInWindow lW;
    public View(Stage stage, Model model){
      
        this.model = model;
        bPane = new BorderPane();
        scene = new Scene(bPane,400, 500); 
        lW = new LogInWindow();
        stage.setTitle("musicMovies");
        stage.setScene(scene);
        stage.show();
    }
    public void startMainView(){
        System.out.println("Liquid");
        cView = new CenterTableView();
        hBox = new TopHboxView(cont);
       // bPane.getChildren().addAll(cView);
        bPane.setLeft(cView);
        bPane.setTop(hBox);   
    }
   
   
    public void ControllerHook(Controller controller){
        cont = controller;
        lW.logToController(controller);
        System.out.println("hooked to controller");
    }
    
     public void hide() {
        stage2.close();
    }
    
}
