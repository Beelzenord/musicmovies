package View;
import Controller.Controller;
import Controller.Connector;

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
    private GridPane grid2;
    private TextField theUserName;
    private PasswordField pwBox;
    private Scene scene2;
    private Stage stage2;
    private Scene scene3;
    private Stage stage3;
    private Button establishNewProfile;
    private TextField newUserName;
    private TextField newPassword;
    private Button newProfileButton;
    private String Uname;
    private String Pass;
    
    public LogInWindow() {
        initLoginWindow();
    }
    
    private void initLoginWindow() {
// newProfileButton();
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
        Button mongo = new Button("Mongo Button");
        btn2.prefWidth(40);
        HBox hbBtn = new HBox(10);
        HBox signBtn = new HBox(10);
        HBox mongoBox = new HBox(10);
        mongo.prefWidth(40); 
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        signBtn.setAlignment(Pos.BOTTOM_LEFT);
        mongoBox.setAlignment(Pos.BOTTOM_LEFT);
        
        
        newProfileButton = new Button("Create New Profile");
        hbBtn.getChildren().addAll(newProfileButton);
        mongoBox.getChildren().addAll(mongo);
        signBtn.getChildren().addAll(btn2);
        //newProfileButton();
        grid.add(hbBtn, 1, 4);
        grid.add(signBtn, 0, 4);
        grid.add(mongoBox, 0, 5);
        btn2.setOnAction((event) -> {
            //  System.out.print(theUserName.getText());
              String username = theUserName.getText();
              String password = "";
              password += pwBox.getText();
              try {
                  cont.transfer(username, password);
                  cont.initViews();
              }  catch (Exception ex) {
              }
        });
        
        mongo.setOnAction((event) -> {
            try {
                cont.mongoInit();
                cont.initViews();
            } catch (Exception ex) {
            }
        });
        
        scene2 = new Scene(grid, 250, 250);   
        stage2 = new Stage();
        stage2.setTitle("Log in Screen");
        stage2.setScene(scene2);
        stage2.show();
    }
    public void logToController(Controller controller){
        this.cont = controller;
        //cont.acknowledgeLogIn(this);
    }
    private void newProfileWindow(){
        grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        establishNewProfile = new Button("Confirm new profile");
        Label usersName = new Label("please insert your full name name: ");
        Label passLabel = new Label("insert your password ");
        Text scenetitle = new Text("New User Profile");
        officiateNewProfile();
        grid2.add(scenetitle, 0, 0, 2, 1);
        grid2.add(usersName, 0, 1);
        grid2.add(passLabel, 0, 2);
        newUserName = new TextField();
        newPassword= new TextField();
        grid2.add(newUserName, 1, 1);
        grid2.add(newPassword, 1, 2);
        grid2.add(establishNewProfile, 1, 3);
        scene3 = new Scene(grid2,200,200); 
        stage3 = new Stage();
        stage3.setTitle("Log in Screen");
        stage3.setScene(scene3);
        stage3.show();
     }
     public void newProfileButton(){
         newProfileButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 System.out.println("Closer to the sdge");
                 newProfileWindow();
                 
             }
             
         });
     }
     
    
    private void officiateNewProfile(){
         establishNewProfile.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Uname = newUserName.getText().trim();
                 Pass  = newPassword.getText().trim();
                 System.out.println("test test test");
                 System.out.println(Uname+Pass);
                 
                 /*try {
                     cont.launchNewUserDetail(Uname, Pass);
                 } catch (SQLException ex) {
                     Logger.getLogger(LogInWindow.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
             }
         });
     }
     public void hide() {
        stage2.close();
    }
}