import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import static java.util.Map.entry;   

public class ArchiveManager {

    private static ArchiveManager instance;

    private List<Book> archive; 

    
    private static Comparator<Book> compareByAuthor = (Book b1, Book b2) -> b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
    private static Comparator<Book> compareByTitle = (Book b1, Book b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle());
    private static Comparator<Book> compareByGenre = (Book b1, Book b2) -> b1.getGenre().compareTo(b2.getGenre());
    private static Comparator<Book> compareByNumPages = (Book b1, Book b2) -> Integer.compare(b1.getNumPages(), b2.getNumPages());
    private static Comparator<Book> compareByID = (Book b1, Book b2) -> b1.getID().compareToIgnoreCase(b2.getID());

    private static final Map<String, Comparator<Book>> ORDER_CRITERIAS = Map.ofEntries(
        entry("Author", compareByAuthor)
    );


    private ArchiveManager(){
        archive = new ArrayList<Book>();
    }

    public static ArchiveManager getInstance(){
        if (instance == null){
            instance = new ArchiveManager();
        }
        return instance;
    }

    public void orderBy(String orderCriteria){
        archive.sort(ORDER_CRITERIAS.getOrDefault(orderCriteria, compareByTitle));
    }
}
