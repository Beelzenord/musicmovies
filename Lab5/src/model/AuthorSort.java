package model;

import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

/**
 * authorComp takes the first <code>Author</code> of two <code>Book</code>s and compares them.
 * authorComp implements <code>Comparator</code> in order to compare <code>Author</code>.
 *
 * @author Niklas Ålander
 * @version 1.0
 */
public class AuthorSort implements Comparator<Book> {
    
    /**
     * Compares the first <code>Author</code> of two different <code>Book</code>s.
     * @param b1 <code>Book</code> 1 to be compared.
     * @param b2 <code>Book</code> 2 to be compared.
     * @return Returns 0 if the <code>Author</code>s are the same,
     * return a negative number if <code>b1</code>s <code>Author</code>
     * is less than <code>b2</code>s <code>Author</code>,
     * return a positive number if <code>b1</code>s <code>Author</code>
     * is greater than <code>b2</code>s <code>Author</code>,
     */
    @Override
    public int compare(Book b1, Book b2) {
        
        ArrayList<Author> a1 = b1.getsAuthors();
        ArrayList<Author> a2 = b2.getsAuthors();
        Collections.sort(a1, new AuthorSort2());
        Collections.sort(a2, new AuthorSort2());
        
        
        return a1.get(0).getName().compareTo(a2.get(0).getName());
    }
}