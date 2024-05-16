package items.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Map.entry;

import java.time.Year;

import exceptions.NotInArchiveException;
import exceptions.InvalidAdminException;
import exceptions.InvalidBookException;
import exceptions.ManagerAlreadyInitializedException;

import users.Admin;
import users.managers.PersonManager;
import items.Book;
import items.Loanable;
import items.filters.BookFilter;


public class ArchiveManager {


  
    public static enum Criteria {
        LOAN_STATE, ID, RELEASE_YEAR, NUM_PAGES, GENRE, AUTHOR, TITLE, NUM_PAGES_MORE, NUM_PAGES_LESS
    }

    private static final String FILTER_NOT_COHERENT_MSG = "Criteria and argument are not coherent";

    private static final String BOOK_ON_LOAN_MSG = "The book is currently on loan, you cannot remove it";

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can modify the archive";

    private static final String INVALID_BOOK_MSG = "This book cannot be added to the archive";

    private static ArchiveManager instance;

    private Map<String, Book> archive; 

    
    private static Comparator<Book> compareByAuthor = 
        (Book b1, Book b2) -> b1.getAuthor().compareToIgnoreCase(b2.getAuthor());

    private static Comparator<Book> compareByTitle = 
        (Book b1, Book b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle());

    private static Comparator<Book> compareByGenre = 
        (Book b1, Book b2) -> b1.getGenre().compareTo(b2.getGenre());

    private static Comparator<Book> compareByNumPages = 
        (Book b1, Book b2) -> Integer.compare(b1.getNumPages(), b2.getNumPages());

    private static Comparator<Book> compareByReleaseYear = 
        (Book b1, Book b2) -> b1.getReleaseYear().compareTo(b2.getReleaseYear());

    private static Comparator<Book> compareByID = 
        (Book b1, Book b2) -> b1.getID().compareToIgnoreCase(b2.getID());

    /**Map used to store the comparators used to sort the archive Map
     */
    private static final Map<Criteria, Comparator<Book>> ORDER_CRITERIAS = Map.ofEntries(
        entry(Criteria.TITLE, compareByTitle),
        entry(Criteria.AUTHOR, compareByAuthor),
        entry(Criteria.GENRE, compareByGenre),
        entry(Criteria.NUM_PAGES, compareByNumPages),
        entry(Criteria.RELEASE_YEAR, compareByReleaseYear),
        entry(Criteria.ID, compareByID)
    );

    private static BookFilter<String> filterByTitle = 
        (Book b, String title) -> b.getTitle().compareToIgnoreCase(title) == 0;

    private static BookFilter<String> filterByAuthor = 
        (Book b, String author) -> b.getAuthor().compareToIgnoreCase(author) == 0;

    private static BookFilter<Book.Genre> filterByGenre = 
        (Book b, Book.Genre genre) -> b.getGenre().compareTo(genre) == 0;

    private static BookFilter<Year> filterByYear = 
        (Book b, Year releaseYear) -> b.getReleaseYear().compareTo(releaseYear) == 0;

    private static BookFilter<Loanable.LoanState> filterByLoanState = 
        (Book b, Loanable.LoanState loanState) -> b.getState().compareTo(loanState) == 0;

    private static BookFilter<Integer> filterByNumPagesMoreThan = 
        (Book b, Integer numPages) -> b.getNumPages() >= numPages;

    
    private static BookFilter<Integer> filterByNumPagesLessThan = 
        (Book b, Integer numPages) -> b.getNumPages() <= numPages;

    
    private static BookFilter<Integer> filterByNumPagesEqual = 
        (Book b, Integer numPages) -> b.getNumPages() == numPages;
    
    /**Map used to store the filters used to get specific elements of the archive Map
     */
    private static final Map<Criteria, BookFilter<?>> FILTER_CRITERIAS = Map.ofEntries(
        entry(Criteria.TITLE, filterByTitle),
        entry(Criteria.AUTHOR, filterByAuthor),
        entry(Criteria.GENRE, filterByGenre),
        entry(Criteria.RELEASE_YEAR, filterByYear),
        entry(Criteria.LOAN_STATE, filterByLoanState),
        entry(Criteria.NUM_PAGES, filterByNumPagesEqual),
        entry(Criteria.NUM_PAGES_LESS, filterByNumPagesLessThan),
        entry(Criteria.NUM_PAGES_MORE, filterByNumPagesMoreThan)
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

    /**Initialize the archive with a list of books
     * @param books to add to the archive
     * @throws ManagerAlreadyInitializedException if the archive was already initialized, in this case
     * you'll have to use the addBooks method to add books to the archive
     */
    public void initializeArchive(List<Book> books) throws ManagerAlreadyInitializedException {
        if (this.archive.size() > 0) {
            throw new ManagerAlreadyInitializedException();
        }
        
        for (Book book : books){
            if (book != null)
                archive.put(book.getID(), book);
        }

    }


    /**Method used to sort the books (values of the map archive) in the archive by a criteria specified,
     * if the criteria is invalid it will be sorted by title
     * @param orderCriteria of the books in the archive, can be "title", "author", "genre", "num pages",
     * "release year" or "id" default to title
     * @return a list of sorted books by the specified criteria
     */
    public void sortBy(Criteria orderCriteria){
        List<Entry<String, Book>> list = new ArrayList<>(archive.entrySet());
        list.sort(Entry.comparingByValue(ORDER_CRITERIAS.getOrDefault(orderCriteria, compareByTitle)));
        archive.clear();
        for (Entry<String, Book> entry : list) {
            archive.put(entry.getKey(), entry.getValue());
        }
    }

    /**Obtain a sorted list of the books (values of the map archive) in the archive by a criteria specified,
     * if the criteria is invalid it will be sorted by title
     * @param orderCriteria of the books in the archive, the options offered are in the static Criteria enum in this class,
     * if not valid defaults to TITLE
     */
    public List<Book> getSortedBooksBy(Criteria orderCriteria) {
        List<Book> list = new ArrayList<>(archive.values());
        list.sort(ORDER_CRITERIAS.getOrDefault(orderCriteria, compareByTitle));
        return list;
    }

    /**Add a book to the archive
     * @param applicant an admin is necessary to modify the archive
     * @param book to add to the archive
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void addBook(Admin applicant, Book book) throws InvalidAdminException, InvalidBookException {
        if (applicant == null || !PersonManager.getInstance().validApplicant(applicant))
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        if (book == null)
            throw new InvalidBookException(INVALID_BOOK_MSG);
        archive.putIfAbsent(book.getID(), book);
    }

    /**Add a list of books to the archive
     * @param applicant an admin is necessary to modify the archive
     * @param books to add to the archive
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void addBooks(Admin applicant, List<Book> books) throws InvalidAdminException, InvalidBookException {
        for (Book book : books) {
            addBook(applicant, book);
        }
    }

    /**Remove a book from the archive, if possible
     * @param applicant an admin is necessary to modify the archive
     * @param book to remove from the archive
     * @throws InvalidAdminException if the admin is not accredited
     * @throws NotInArchiveException if the book is on loan to someone
     */
    public void removeBook(Admin applicant, Book book) throws InvalidAdminException, NotInArchiveException {
        removeBook(applicant, book.getID());
    }

    /**Remove a book from the archive, if possible
     * @param applicant an admin is necessary to modify the archive
     * @param id of the book to remove from the archive
     * @throws InvalidAdminException if the admin is not accredited
     * @throws NotInArchiveException if the book is on loan to someone
     */
    public void removeBook(Admin applicant, String id) throws InvalidAdminException, NotInArchiveException {
        if (!PersonManager.getInstance().validApplicant(applicant)){
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        if (!archive.containsKey(id)){
            throw new NotInArchiveException();
        }
        if (archive.get(id).getState().equals(Loanable.LoanState.ON_LOAN)){
            throw new NotInArchiveException(BOOK_ON_LOAN_MSG);
        }
        archive.remove(id);
    }
    
    /**Search books in the archive whose attributes match generic query 
     * @param searchQuery can be the book ID (the list will be of one element if present), 
     * title, author name, genre, release year (in ISO 8601 format)
     * @return a list of books which attributes match the query
     */
    public ArrayList<Book> searchBook(String searchQuery) {
        searchQuery = searchQuery.trim();
        boolean isYear = false;
        try {
            Year.parse(searchQuery);
            isYear = true;
        } catch (Exception e) {
            isYear = false;
        }
        ArrayList<Book> booksFound= new ArrayList<>();
        Book temp = archive.get(searchQuery);
        if (temp != null)
            booksFound.add(temp);
        else for (Entry<String, Book> entry : archive.entrySet()) {
            temp = entry.getValue();
            if (temp.getAuthor().equalsIgnoreCase(searchQuery) ||
                temp.getTitle().equalsIgnoreCase(searchQuery) ||
                temp.getGenre().name().equalsIgnoreCase(searchQuery) ||
                (isYear && temp.getReleaseYear().equals(Year.parse(searchQuery)) )
                ) {
                    booksFound.add(temp);
                }
        }
        return booksFound;
    }

    public boolean isBookPresent(Book book) {
        return archive.containsKey(book.getID());
    }


    /**Get a filtered list of pending loan requests by the specified criteria and argument
     * @param <T> the type of argument
     * @param criteria to filter the list of requests by, the options offered are in the static Criteria enum in this class,
     * if not valid defaults to TITLE
     * @param argument that has to match the specified criteria
     * @return a list of loan requests filtered by the specified criteria and argument
     * @throws ClassCastException
     */
    public <T> List<Book> filterBy(Criteria criteria, T argument) throws ClassCastException {
        List<Book> newList = new ArrayList<>();
        try {
            BookFilter<T> filter = (BookFilter<T>) FILTER_CRITERIAS.getOrDefault(criteria, filterByTitle);
            for(Book element : archive.values()) {
                if (filter.test(element, argument))
                    newList.add(element);
            }
        } catch (Exception e) {
            throw new ClassCastException(FILTER_NOT_COHERENT_MSG);
        }
        
        return newList;
    }
}
