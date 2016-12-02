package model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Observable;


/**
 * A <code>Book</code> has a <code>title</code>, <code>isbn</code>, <code>edition</code>,
 * <code>price</code> and a List of <code>Author</code>s.
 * <code>Book</code> implements <code>Serializable</code> in order to save object in a file.
 *
 * @author Niklas Ålander
 * @version 1.1
 */
public class Book extends Observable implements Serializable {
    private String title;
    private String isbn;
    private int edition;
    private double price;
    private ArrayList<Author> authors;
    
    /**
     * Creates a new <code>Book</code>.
     * @param title The title.
     * @param isbn The isbn.
     * @param edition The edition.
     * @param price The price.
     * @param authors The list of all authors names.
     */
    public Book(String title, String isbn, int edition, double price, ArrayList<String> authors) {
        this.title = title;
        this.isbn = isbn;
        this.edition = edition;
        this.price = price;
        this.authors = new ArrayList<Author>();
        for (String s : authors)
            this.authors.add(new Author(s));
    }
    
    /**
     * Returns the <code>title</code> of the <code>Book</code>.
     * @return Returns the <code>title</code> of the <code>Book</code>.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Changes the <code>Book</code> <code>title</code>.
     * @param title The <code>Book</code> new <code>title</code>.
     */
    public void setTitle(String title) {
        this.title = title;
        notifyAllObservers();
    }
    
    /**
     * Returns the <code>isbn</code> of the <code>Book</code>.
     * @return Returns the <code>isbn</code> of the <code>Book</code>.
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Changes the <code>Book</code> <code>isbn</code>.
     * @param isbn The <code>Book</code> new <code>isbn</code>.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
        notifyAllObservers();
    }
    
    /**
     * Returns the <code>edition</code> of the <code>Book</code>.
     * @return Returns the <code>edition</code> of the <code>Book</code>.
     */
    public int getEdition() {
        return edition;
    }
    
    /**
     * Changes the <code>Book</code> <code>edition</code>.
     * @param edition The <code>Book</code> new <code>edition</code>.
     */
    public void setEdition(int edition) {
        this.edition = edition;
        notifyAllObservers();
    }
    
    /**
     * Returns the <code>price</code> of the <code>Book</code>.
     * @return Returns the <code>price</code> of the <code>Book</code>.
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Changes the <code>Book</code> <code>price</code>.
     * @param price The <code>Book</code> new <code>price</code>.
     */
    public void setPrice(double price) {
        this.price = price;
        notifyAllObservers();
    }
    
    /**
     * Creates a <code>clone</code> of the list of <code>Author</code>s
     * of the <code>Book</code> to be returned.
     * @return Creates a <code>clone</code> of the list of
     * <code>Author</code>s of the <code>Book</code> to be returned.
     */
    public ArrayList<Author> getsAuthors() {
        return (ArrayList<Author>) authors.clone();
    }
    
    /**
     * Creates a <code>String</code> of the list of <code>Author</code>s
     * of the <code>Book</code> to be returned.
     * @return Creates a <code>String</code> of the list of
     * <code>Author</code>s of the <code>Book</code> to be returned.
     */
    public String getAuthors() {
        String tmp = new String("");
        for (Author a : authors) {
            tmp += a.getName() + ", ";
        }
        return tmp;
    }
    
    /**
     * Creates a new <code>Author</code> and adds it to the list.
     * @param name The name of the new <code>Author</code>.
     */
    public void addAuthor(String name) {
        authors.add(new Author(name));
        notifyAllObservers();
    }
    
    /**
     * Creates a new <code>Author</code> and adds it to the list.
     * @param author The new <code>Author</code>.
     */
    public void addAuthor(Author author) {
        authors.add(author);
    }
    
    /**
     * Notifies observers that a change has happened.
     */
    public void notifyAllObservers() {
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Creates a text <code>String</code> with information
     * about the <code>Book</code>.
     * @return Returns a text <code>String</code> with information
     * about the <code>Book</code>.
     */
    public String toString() {
        String text = new String("");
        text += "Title: " + title;
        text += "\nISBN: " + isbn;
        text += "\nEdition: " + edition;
        text += "\nPrice: " + price;
        text += "\nAuthor(s): ";
        for (Author a : authors) {
            text += a.toString() + ", ";
        }
        return text;
    }
}