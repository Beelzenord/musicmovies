
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author fauzianordlund
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       Model model = new Model();
       View view  = new View(primaryStage, model);
       Controller controller = new Controller(model,view);
       // LogIn logIn = new LogIn();
       //Controller controller = new Controller();
        
       //View view = new View(primaryStage);
    }

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
