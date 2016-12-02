/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import model.Author;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import model.Book;
import model.CollectionOfBooks;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CenterTableView extends TableView implements Observer {
    private CollectionOfBooks library;
    private ObservableList<Book> observBooks;
    private TableColumn title;
    private TableColumn isbn;
    private TableColumn edition;   
    private TableColumn price;        
    private TableColumn author;
    
    
    public CenterTableView (CollectionOfBooks library) {
        this.library = library;
        
        initView();
    }
    
    public ArrayList<Book> removeBook() {
        ObservableList<Book> librarySelected;

        librarySelected = this.getSelectionModel().getSelectedItems();
        ArrayList<Book> tmp = new ArrayList();
        for (Book b : librarySelected) {
            tmp.add(b);
        }
        return tmp;
    }
    
    private void initView() {
        this.setEditable(true);
        title = new TableColumn("Title");
        title.setMinWidth(100);
        isbn = new TableColumn("Isbn");
        isbn.setMinWidth(80);
        edition = new TableColumn("Edition");
        edition.setMinWidth(80);
        price = new TableColumn("Price");
        price.setMinWidth(80);
        author = new TableColumn("Author");
        author.setMinWidth(128);
        
        this.getColumns().addAll(title, isbn, edition, price, author);
        
        title.setCellValueFactory(
            new PropertyValueFactory<Book, String>("title"));
        isbn.setCellValueFactory(
            new PropertyValueFactory<Book, String>("isbn"));
        edition.setCellValueFactory(
            new PropertyValueFactory<Book, Integer>("edition"));
        price.setCellValueFactory(
            new PropertyValueFactory<Book, Double>("price"));
        author.setCellValueFactory(
            new PropertyValueFactory<Book, ArrayList<Author>>("authors"));
        
        observBooks = FXCollections.observableArrayList(library.getList());
        refresh();
    }    
    
    public void setSearchedList(ArrayList<Book> tmpBooks) {
        ObservableList<Book> tmpObvBooks;
        tmpObvBooks = FXCollections.observableArrayList(tmpBooks);
        this.setItems(tmpObvBooks);
    }
    
    public void refresh() {
        observBooks.removeAll(observBooks);
        observBooks.addAll(library.getList());
        this.setItems(observBooks);
    }

    @Override
    public void update(Observable o, Object o1) {
        refresh();
    }
}
