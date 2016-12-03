
package View;
import Controller.Controller;
import Model.QueryExecutor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopHboxView extends HBox{
    private Controller cont;
    private Button showMovies;
    private Button showMusic;
    public TopHboxView(Controller controller){
        super(30);
        cont = controller;
        System.out.println("Shadow Moses");
        cont.confirm();
        initView();
    }
    private void initView(){
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10,10,5,10));   
         
        showMovies = new Button("Display movie list");
        showMusic = new Button("Display music list");
        this.getChildren().addAll(showMovies,showMusic);
    }
}
