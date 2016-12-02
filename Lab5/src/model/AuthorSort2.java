package model;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * authorSort sorts <code>Author</code>s of a <code>Book</code> in alphabetical order.
 * authorSort implements <code>Comparator</code> in order to sort <code>Author</code>s.
 *
 * @author Niklas Ålander
 * @version 1.0
 */
public class AuthorSort2 implements Comparator<Author> {
    
    /**
     * Sorts <code>Author</code>s of a <code>Book</code> in alphabetical order.
     * @param a1 <code>Author</code> 1 to be sorted.
     * @param a2 <code>Author</code> 2 to be sorted.
     * @return Returns 0 if the <code>Author</code>s <code>name</code>s are the same,
     * return a negative number if <code>a1</code>s <code>name</code>
     * is less than <code>a2</code>s <code>name</code>,
     * return a positive number if <code>a1</code>s <code>name</code>
     * is greater than <code>a2</code>s <code>name</code>,
     */
    @Override
    public int compare(Author a1, Author a2) {
        String name1 = a1.getName();
        String name2 = a2.getName();
        
        return name1.compareTo(name2);
    }
}
