package model;

import java.util.Comparator;

/**
 * titleComp takes the title of two <code>Book</code>s and compares them.
 * titleComp implements <code>Comparator</code> in order to compare <code>title</code>.
 *
 * @author Niklas Ålander
 * @version 1.0
 */
public class TitleSort implements Comparator<Book> {
    
    /**
     * Compares the title of two different <code>Book</code>s.
     * @param b1 <code>Book</code> 1 to be compared.
     * @param b2 <code>Book</code> 2 to be compared.
     * @return Returns 0 if the <code>title</code>s are the same,
     * return a negative number if <code>b1</code>s <code>title</code>
     * is less than <code>b2</code>s <code>title</code>,
     * return a positive number if <code>b1</code>s <code>title</code>
     * is greater than <code>b2</code>s <code>title</code>,
     */
    @Override
    public int compare(Book b1, Book b2) {
        String title1 = b1.getTitle();
        String title2 = b2.getTitle();
        
        return title1.compareTo(title2);
    }
}
