
package View;
import Controller.Controller;
import Controller.Connector;

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
   
    private Connector conn;
    private ArrayList<Scene> scenes;
    private ArrayList<BorderPane> bPanes;
    private Controller cont;
    private Stage stage2;
    private ArtistTableView artistTableView;
    private AlbumTableView albumTableView;
    private MovieTableView movieTableView;
    private DirectorTableView directorTableView;
    private Stage stage;
    
    private ArrayList<TopHboxView> hBoxes;
    private ArrayList<BotHboxView> botBoxes;
    private LogInWindow lW;
    
    public View(Stage stage, Connector conn){
        this.stage = stage;
        
        scenes = new ArrayList();
        bPanes = new ArrayList();
        albumTableView = new AlbumTableView();
        artistTableView = new ArtistTableView();
        movieTableView = new MovieTableView();
        directorTableView = new DirectorTableView();
        
        this.conn = conn;
        initScenes();

        lW = new LogInWindow();
        this.stage.setTitle("kTunes - Media Library");
        this.stage.setScene(scenes.get(0));
        this.stage.show();
        saveBeforeQuit();
    }
    
    private void initScenes() {
        
        for (int i = 0; i < 4; i++) {
            bPanes.add(new BorderPane());
            scenes.add(new Scene(bPanes.get(i), 800, 500));
            
        }
    }
    
    public void startMainView(){
        hBoxes = new ArrayList();
        botBoxes = new ArrayList();
        for (int i = 0; i < 4; i++) {
            hBoxes.add(new TopHboxView(cont));
            botBoxes.add(new BotHboxView(cont));
            if (i == 0)
                bPanes.get(i).setCenter(albumTableView);
            else if (i == 1)
                bPanes.get(i).setCenter(artistTableView);
            else if (i == 2)
                bPanes.get(i).setCenter(movieTableView);
            else if (i == 3)
                bPanes.get(i).setCenter(directorTableView);
            bPanes.get(i).setTop(hBoxes.get(i));
            bPanes.get(i).setBottom(botBoxes.get(i));
        }
        cont.connectViews(albumTableView, artistTableView, movieTableView, directorTableView);
    }
   
   
    public void ControllerHook(Controller controller){
        cont = controller;
        lW.logToController(controller);
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
