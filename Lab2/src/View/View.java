
package View;
import Controller.Controller;
import Controller.Connector;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
