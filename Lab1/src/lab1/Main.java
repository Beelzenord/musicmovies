
import javafx.application.Application;
import javafx.stage.Stage;
import Controller.Connector;
import View.View;
import Controller.Controller;
import java.sql.SQLException;

/**
 *
 * @author fauzianordlund
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws SQLException {
       Connector model = new Connector();
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
