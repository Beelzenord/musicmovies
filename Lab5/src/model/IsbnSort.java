package model;

import java.util.Comparator;

/**
 * isbnComp takes the isbn of two <code>Book</code>s and compares them.
 * isbnComp implements <code>Comparator</code> in order to compare <code>isbn</code>.
 *
 * @author Niklas Ålander
 * @version 1.0
 */
public class IsbnSort implements Comparator<Book> {
    
    /**
     * Compares the isbn of two different <code>Book</code>s.
     * @param b1 <code>Book</code> 1 to be compared.
     * @param b2 <code>Book</code> 2 to be compared.
     * @return Returns 0 if the <code>isbn</code>s are the same,
     * return a negative number if <code>b1</code>s <code>isbn</code>
     * is less than <code>b2</code>s <code>isbn</code>,
     * return a positive number if <code>b1</code>s <code>isbn</code>
     * is greater than <code>b2</code>s <code>isbn</code>,
     */
    @Override
    public int compare(Book b1, Book b2) {
        String isbn1 = b1.getIsbn();
        String isbn2 = b2.getIsbn();
        
        return isbn1.compareTo(isbn2);
    }
}