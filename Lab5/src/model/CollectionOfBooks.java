package model;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;


/**
 * A CollectionOfBooks contains a ArrayList of <code>Book</code> objects.
 * The list can be manipulated by adding and removing <code>Book</code> objects.
 *
 * @author Niklas Ålander
 * @version 1.1
 */
public class CollectionOfBooks extends Observable {
    //private ArrayList<Book> library;
    private ArrayList<Book> library;
    
    /**
     * Constructs a new ArrayList of <code>Book</code> objects.
     */
    public CollectionOfBooks() {
        library = new ArrayList<Book>();
    }
    
    /**
     * Constructs a <code>ArrayList</code> of <code>Book</code> objects.
     * @param library The new list of <code>Book</code>.
     */
    public CollectionOfBooks(ArrayList<Book> library) {
        this.library = library;
    }

    /**
     * Changes the <code>CollectionOfBooks</code> <code>books</code>.
     * @param library The new list of <code>Book</code>.
     */
    public void setBooks(ArrayList<Book> library) {
        this.library = library;
        notifyAllObservers();
    }
    
    /**
     * Return the list of type <code>Book</code>.
     * @return Return the list of type <code>Book</code>.
     */
    public ArrayList<Book> getList() {
        return (ArrayList<Book>) library;
    }
    
    /**
     * Return the size of the list.
     * @return Returns the size of list.
     */
    public int getSize() {
        return library.size();
    }
    
    /**
     * Adds a new <code>Book</code> to the list.
     * @param book The new <code>Book</code> to add to the list.
     */
    public void addBook(Book book) {
        library.add(book);
        notifyAllObservers();
    }
    
    /**
     * Removes a book from the list if given index is within the list and returns
     * <code>true</code> or <code>false</code> whether it was successful.
     * @param index The place in the list which specifies which book to remove.
     * @return Return <code>true</code> if the book was successfully removed,
     * otherwise return <code>false</code>.
     */
    public boolean removeBook(int index) {
        if (index >= 0 && index < library.size()) {
            library.remove(index);
            notifyAllObservers();
            return true;
        }
        else
            return false;
    }
    

    /**
     * Removes a book from the list if given <code>Book</code> is 
     * within the list and returns <code>true</code>.
     * @param book The Book that is to be removed.
     * @return Return <code>true</code> when the book has been removed.
     */
    public boolean removeBook(Book book) {
        library.remove(book);
        notifyAllObservers();
        return true;
    }
    
    /**
     * Removes a book from the list if given index is within the list and returns
     * removed book or <code>null</code> is it was unsuccessful.
     * @param index The place in the list which specifies which book to remove.
     * @return If the book was successfully removed, return the book,
     * otherwise return <code>null</code>.
     */
    public Book removeAndGetBook(int index) {
        if (index >= 0 && index < library.size()) {
            notifyAllObservers();
            return library.remove(index);
        }
        else
            return null;
    }
    
    /**
     * Changes the name of an <code>Author</code>.
     * @param author The <code>Author</code> whos name is to be changed.
     * @param name The new name of specified <code>Author</code>.
     */
    public void changeAuthorName(Author author, String name) {
        author.setName(name);
        notifyAllObservers();
    }
    
    /**
     * Adds an <code>Author</code> to a <code>Book</code> object.
     * @param index The index in the list of library that will gain an <code>Author</code>.
     * @param name The name of the new <code>Author</code>.
     * @return Return <code>true</code> if the <code>Author</code> was successfully added,
     * otherwise return <code>false</code>.
     */
    public boolean addAuthor(int index, String name) {
        if (index >= 0 && index < library.size()) {
            Author tmpAuthor = findAuthor(name);
            if (tmpAuthor != null) {
                library.get(index).addAuthor(tmpAuthor);
                notifyAllObservers();
                return true;
            }
            else {
                library.get(index).addAuthor(name);
                notifyAllObservers();
                return true;
            }
        }
        else
            return false;
    }
    
    /**
     * Searches all library to find an <code>Author</code> to return.
     * @param name The name of the <code>Author</code> to be found.
     * @return If an <code>Author</code> is found return the <code>Author</code>,
     * otherwise return <code>null</code>.
     */
    public Author findAuthor(String name) {
        Author tmpAuthor = new Author(name);
        for (Book b : library) {
            ArrayList<Author> tmpList = b.getsAuthors();
            for (Author a : tmpList) {
                if (a.compareTo(tmpAuthor) == 0) {
                    return a;
                }
            }
        }
        return null;
    }
    
    /**
     * Searches for library by its <code>isbn</code>.
     * If a book's <code>isbn</code> contains the search word, the book
     * will be added to a list which will be returned.
     * @param isbn The <code>isbn</code> that is being searched.
     * @return Returns the list of library that had matching isbns.
     */
    public ArrayList<Book> searchByIsbn(String isbn) {
        ArrayList<Book> tmpBookList = new ArrayList<Book>();
        CharSequence tmpIsbn = isbn.toUpperCase();
        for (Book b : library) {
            if (b.getIsbn().toUpperCase().contains(tmpIsbn))
                tmpBookList.add(b);
        }
        Collections.sort(tmpBookList, new IsbnSort());
        return tmpBookList;
    }
    
    /**
     * Searches for library by its <code>title</code>.
     * If a book's <code>title</code> contains the search word, the book
     * will be added to a list which will be returned.
     * @param title The <code>title</code> that is being searched.
     * @return Returns the list of library that had matching titles.
     */
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> tmpBookList = new ArrayList<Book>();
        CharSequence tmpTitle = title.toUpperCase();
        for (Book b : library) {
            if (b.getTitle().toUpperCase().contains(tmpTitle))
                tmpBookList.add(b);
        }
        Collections.sort(tmpBookList, new TitleSort());
        return tmpBookList;
    }
    
    /**
     * Searches for library by its <code>Author</code>.
     * If a book's <code>Author</code> contains the search word, the book
     * will be added to a list which will be returned.
     * @param name The <code>Author</code> that is being searched.
     * @return Returns the list of library that had matching Authors.
     */
    public ArrayList<Book> searchByAuthor(String name) {
        ArrayList<Book> tmpBookList = new ArrayList<Book>();
        CharSequence tmpName = name.toUpperCase();
        Author tmpAuthor = new Author(name);
        for (Book b : library) {
            ArrayList<Author> tmpList = b.getsAuthors();
            for (Author a : tmpList) {
                if (a.getName().toUpperCase().contains(tmpName)) {
                    tmpBookList.add(b);
                    break;
                }
            }
        }
        Collections.sort(tmpBookList, new AuthorSort());
        return tmpBookList;
    }
    
    /**
     * Notifies observers that a change has happened.
     */
    public void notifyAllObservers() {
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Creates a text <code>String</code> with information of every
     * <code>Book</code> is put together.
     * @return Returns a text <code>String</code> with information
     * of every <code>Book</code> in the list.
     */
    public String toString() {
        String text = new String("\n");
        int i = 0;
        for (Book b : library) {
            text += "\nIndex: " + i + "\n" + b.toString() + "\n";
            i += 1;
        }
        return text;
    }
}