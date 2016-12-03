package View;

import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CenterTableView extends TableView{
    private TableColumn album;
    private TableColumn artist;
    private TableColumn movie;
    private TableColumn director;
    private TableColumn rating;
    ArrayList<TableColumn> tablecolumn;
    public CenterTableView(Object o) {
        //ArrayList<String> attributeNames = o.getClass();
        
        
        
        
        album = new TableColumn("album");
        album.setMinWidth(100);
        artist = new TableColumn("artist");
        artist.setMinWidth(100);
        rating = new TableColumn("rating");
        rating.setMinWidth(20);//
        initView();
        System.out.println("dedom");
    }
    public void initView(){
        this.getColumns().addAll(album,artist,rating);
    }
}
