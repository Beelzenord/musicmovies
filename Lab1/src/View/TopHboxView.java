
package View;
import Controller.Controller;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TopHboxView extends HBox{
    private Controller controller;
    private TextField searchField;
    private ComboBox<String> searchFor;
    private ComboBox<String> searchHow;
    private Button searchButton;
    
    public TopHboxView(Controller controller) {
        super(30);
        this.controller = controller;
        
        initView();
    }
    
    
    
    private void initView() {
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(5, 10, 10, 10));
        
        searchField = new TextField();
        searchField.setPromptText("Type here to search then press  --->");
        searchButton = new Button("Search");
        
        searchFor = new ComboBox<>();
        searchFor.getItems().addAll(
                "Album",
                "Artist",
                "Movie",
                "Director"
        );
        searchFor.setPromptText("Album");
        searchFor.setPrefWidth(90);
        
        setSearchHow("album");
        
        searchField.setPrefWidth(210);
        searchButton.setPrefWidth(90);
        
        addHandlers();
        
    }
    
    public void setSearchHow(String str) {
        searchHow = new ComboBox<>();
        if (str.equals("album")) {
            searchHow = new ComboBox<>();
            searchHow.getItems().addAll(
                    "Title",
                    "Genre",
                    "Rating",
                    "Artist"
            );
            searchHow.setPromptText("Title");
        }
        else if (str.equals("artist")) {
            searchHow = new ComboBox<>();
            searchHow.getItems().addAll(
                    "Name",
                    "Rating",
                    "Nationality",
                    "Album"
            );
            searchHow.setPromptText("Name");
        }
        else if (str.equals("movie")) {
            searchHow = new ComboBox<>();
            searchHow.getItems().addAll(
                    "Title",
                    "Genre",
                    "Rating",
                    "Director"
            );
            searchHow.setPromptText("Title");
        }
        else {
            searchHow = new ComboBox<>();
            searchHow.getItems().addAll(
                    "Name",
                    "Rating",
                    "Nationality",
                    "Movie"
            );
            searchHow.setPromptText("Name");
        }
        searchHow.setPrefWidth(90);
        this.getChildren().clear();
        this.getChildren().addAll(searchFor, searchHow, searchField, searchButton);
    }
    
    public String getSearchForText() {
        if (!searchFor.getSelectionModel().isEmpty())
            return searchFor.getValue().toLowerCase();
        else {
            String str = "album";
            return str;
        }
    } 
    
    public String getSearchHowText() {
        if (!searchHow.getSelectionModel().isEmpty())
            return searchHow.getValue().toLowerCase();
        else {
            int ind = generateIndex();
            if (ind == 0 || ind == 2) {
                String str = "title";
                return str;
            }
            else {
                String str = "name";
                return str;
            }
        }
    } 
    
    public String getSearched() {
        return searchField.getText();
    }
    
    private void addHandlers() {
        searchButton.setOnAction((ActionEvent event) -> {
            controller.setQueryIndex(generateIndex());
            String searchBy = getSearchHowText();
            String searchWord = "";
            searchWord += getSearched();
            controller.search(searchBy, searchWord);
        });
        
        searchFor.setOnAction((ActionEvent event) -> {
            setSearchHow(getSearchForText());
        });
    }
    
    private int generateIndex() {
        String str = getSearchForText();
        if (str.equals("album"))
            return 0;
        else if (str.equals("artist"))
            return 1;
        else if (str.equals("movie"))
            return 2;
        else
            return 3;
    }
}
    
    
    
    /*
    private Controller cont;
    private Button showMovies;
    private Button showMusic;
    //public TopHboxView(Controller controller){
        super(30);
        cont = controller;
        System.out.println("Shadow Moses");
        cont.confirm();
        initView();
    }
    //private void initView(){
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10,10,5,10));   
         
        showMovies = new Button("Display movie list");
        showMusic = new Button("Display music list");
        this.getChildren().addAll(showMovies,showMusic);
    }
}*/
