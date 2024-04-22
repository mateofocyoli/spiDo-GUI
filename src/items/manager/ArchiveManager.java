package items.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import users.Admin;
import items.Book;

import static java.util.Map.entry;   

public class ArchiveManager {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can modify the archive";

    private static ArchiveManager instance;

    private Map<String, Book> archive; 

    
    private static Comparator<Book> compareByAuthor = (Book b1, Book b2) -> b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
    private static Comparator<Book> compareByTitle = (Book b1, Book b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle());
    private static Comparator<Book> compareByGenre = (Book b1, Book b2) -> b1.getGenre().compareTo(b2.getGenre());
    private static Comparator<Book> compareByNumPages = (Book b1, Book b2) -> Integer.compare(b1.getNumPages(), b2.getNumPages());
    private static Comparator<Book> compareByReleaseDate = (Book b1, Book b2) -> b1.getReleaseDate().compareTo(b2.getReleaseDate());
    private static Comparator<Book> compareByID = (Book b1, Book b2) -> b1.getID().compareToIgnoreCase(b2.getID());

    /**Map used to store the comparators used to sort the archive Map
     */
    private static final Map<String, Comparator<Book>> ORDER_CRITERIAS = Map.ofEntries(
        entry("author", compareByAuthor),
        entry("title", compareByTitle),
        entry("genre", compareByGenre),
        entry("numPages", compareByNumPages),
        entry("releaseDate", compareByReleaseDate),
        entry("ID", compareByID)
    );


    private ArchiveManager(){
        archive = new HashMap<String, Book>();
    }

    public static ArchiveManager getInstance(){
        if (instance == null){
            instance = new ArchiveManager();
        }
        return instance;
    }

    /**Method used to sort the books (values of the map archive) in the archive by a criteria specified by a string, if the criteria is invalid it will be sorted by ID
     * @param orderCriteria of the books in the archive
     */
    public void sortBy(String orderCriteria){
        List<Entry<String, Book>> list = new ArrayList<>(archive.entrySet());
        list.sort(Entry.comparingByValue(ORDER_CRITERIAS.getOrDefault(orderCriteria, compareByID)));
        archive.clear();
        for (Entry<String, Book> entry : list) {
            archive.put(entry.getKey(), entry.getValue());
        }
    }

    public void addBook(Admin applicant, Book book) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        archive.putIfAbsent(book.getID(), book);
    }

    public void addBooks(Admin applicant, List<Book> books) throws IllegalAccessException {
        for (Book book : books) {
            addBook(applicant, book);
        }
    }

    public void removeBook(Admin applicant, Book book) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        archive.remove(book.getID());
    }


    public void removeBook(Admin applicant, String id) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        archive.remove(id);
    }

    public ArrayList<Book> searchBook(String searchQuery) {
        ArrayList<Book> booksFound= new ArrayList<>();
        Book temp = archive.get(searchQuery);
        if (temp != null)
            booksFound.add(temp);
        else for (Entry<String, Book> entry : archive.entrySet()) {
            temp = entry.getValue();
            if (temp.getAuthor().equalsIgnoreCase(searchQuery) ||
                temp.getTitle().equalsIgnoreCase(searchQuery) ||
                temp.getGenre().name().equalsIgnoreCase(searchQuery) //||
                //temp.getReleaseDate().equals(new DAtesearchQuery) || TODO: capire come fare per la data
                ) {
                    booksFound.add(temp);
                }
        }
        return booksFound;
    }
}
