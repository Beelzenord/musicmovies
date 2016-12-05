
package View;
import Controller.Controller;
import Model.Model;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private ArrayList<Scene> scenes;
    private ArrayList<BorderPane> bPanes;
    private Scene scene2;
    private Controller cont;
    private Stage stage2;
    private ArtistTableView artistTableView;
    private AlbumTableView albumTableView;
    private GridPane grid;
    private Stage stage;
    
    private CenterTableView cView;
    private TextField theUserName;
    private PasswordField pwBox;
    
    private TopHboxView hBox;
    private ArrayList<TopHboxView> hBoxes;
    private ArrayList<BotHboxView> botBoxes;
    
    private LogInWindow lW;
    public View(Stage stage, Model model){
        
        this.stage = stage;
        
        scenes = new ArrayList();
        bPanes = new ArrayList();
        albumTableView = new AlbumTableView();
        artistTableView = new ArtistTableView();
        
        this.model = model;
        initScenes();
        //bPane = new BorderPane();
        //scene = new Scene(bPane,400, 500);

        lW = new LogInWindow();
        this.stage.setTitle("musicMovies");
        this.stage.setScene(scenes.get(0));
        this.stage.show();
        saveBeforeQuit();
    }
    
    private void initScenes() {
        
        for (int i = 0; i < 2; i++) {
            bPanes.add(new BorderPane());
            scenes.add(new Scene(bPanes.get(i), 800, 500));
            
        }
    }
    
    public void startMainView(){
        
        System.out.println("Liquid");
        cView = new CenterTableView();
        hBoxes = new ArrayList();
        botBoxes = new ArrayList();
        //hBox = new TopHboxView(cont);
        
       // bPane.getChildren().addAll(cView);
        //bPane.setCenter(artistTableView);
        for (int i = 0; i < 2; i++) {
            hBoxes.add(new TopHboxView(cont));
            botBoxes.add(new BotHboxView(cont));
            if (i == 0)
                bPanes.get(i).setCenter(albumTableView);
            else if (i == 1)
                bPanes.get(i).setCenter(artistTableView);
            bPanes.get(i).setTop(hBoxes.get(i));
            bPanes.get(i).setBottom(botBoxes.get(i));
        }
        cont.connectViews(albumTableView, artistTableView);
    }
   
   
    public void ControllerHook(Controller controller){
        cont = controller;
        lW.logToController(controller);
        System.out.println("hooked to controller");
    }
    
    public void changeScene(int index) {
        stage.setScene(scenes.get(index));
    }
    
    private void saveBeforeQuit(){
        stage.setOnCloseRequest(event -> {
            event.consume();
            cont.exitProgram();
        });
    }
    
    public void fullyQuit() {
        stage.close();
    }
    
     public void hide() {
        stage2.close();
    }
    
}
