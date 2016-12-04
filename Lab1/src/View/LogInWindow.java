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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author fauzianordlund
 */
public class LogInWindow {
    private Controller cont;
    private GridPane grid;
    private TextField theUserName;
    private PasswordField pwBox;
    private Scene scene2;
    private Stage stage2;
    public LogInWindow(){
        initLoginWindow();
    }
    private void initLoginWindow(){
         grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("Welcome to kTunes");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField theUserName = new TextField();
        grid.add(theUserName, 1, 1);
        
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        System.out.println(theUserName.getText());
        pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button btn2 = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn2);
        grid.add(hbBtn, 1, 4);
        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(pwBox.getText());
              //  System.out.print(theUserName.getText());
                String username = theUserName.getText();
                String password = pwBox.getText();
                try {
                    cont.transfer(username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
         scene2 = new Scene(grid, 250, 250);   
        stage2 = new Stage();
        stage2.setTitle("Log in Screen");
        stage2.setScene(scene2);
        stage2.show();
    }
    public void logToController(Controller controller){
        System.out.println("Login Sceww");
        this.cont = controller;
        cont.acknowledgeLogIn(this);
    }
     public void hide() {
        stage2.close();
    }
}
