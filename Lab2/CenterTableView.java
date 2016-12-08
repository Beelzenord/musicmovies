public class CenterTableView extends TableView{
    private TableColumn album;
    private TableColumn artist;
    private TableColumn movie;
    private TableColumn director;
    private TableColumn rating;
    public CenterTableView(){
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
