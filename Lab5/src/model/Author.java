package model;

import java.io.Serializable;
import java.util.Observable;

/**
 * An <code>Author</code> has a <code>name</code>.
 * <code>Author</code> extends <code>Observable</code> in order to notify obsrvers of changes.
 * <code>Author</code> implements <code>Serializable</code> in order to save object in a file.
 * <code>Author</code> implements <code>Comparable</code> in order to compare itself with itself
 * to other <code>Author</code>s.
 *
 * @author Niklas Ålander
 * @version 1.1
 */
public class Author extends Observable implements Serializable, Comparable<Author> {
    private String name;
    
    /**
     * Creates a new <code>Author</code> object.
     * @param name The name of the new <code>Author</code>.
     */
    public Author(String name) {
        this.name = name;
    }
    
    /**
     * Returns the <code>name</code> of the <code>Author</code>.
     * @return Returns the <code>name</code> of the <code>Author</code>.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Changes the authors <code>name</code>.
     * @param name The authors new <code>name</code>.
     */
    public void setName(String name) {
        this.name = name;
        notifyAllObservers();
    }
    
    /**
     * Compares the name of two different <code>Author</code>s.
     * @param other The <code>other</code> <code>Author</code>s name.
     * @return Returns 0 if the authors names are the same,
     * return a negative number if <code>this</code> authors <code>name</code>
     * is less than <code>other</code> authors <code>name</code>,
     * return a positive number if <code>this</code> authors <code>name</code>
     * is greater than <code>other</code> authors <code>name</code>.
     */
    @Override
    public int compareTo(Author other) {
        String tmp = this.name;
        String otherTmp = other.name;
        
        return tmp.compareTo(otherTmp);
    }
    
    /**
     * Notifies observers that a change has happened.
     */
    public void notifyAllObservers() {
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Creates a text <code>String</code> of the authors <code>name</code>.
     * @return Returns a text <code>String</code> of the authors <code>name</code>.
     */
    public String toString() {
        String text = new String(name);
        return text;
    }
}