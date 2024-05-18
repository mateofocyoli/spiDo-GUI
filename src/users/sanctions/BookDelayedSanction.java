package users.sanctions;

import java.time.LocalDate;
import items.Book;

/**
 * Sanction used to indicate that a book has been brought back in delay
 */
public class BookDelayedSanction extends BookSanction {

    public static final String NAME = "Book delayed";
    public static final String DESCRIPTION = "The book has been returned after the expiration date";
    public static final Severity SEVERITY = Severity.LOW;

    public BookDelayedSanction(LocalDate date, Book book) {
        super(date, book);
    }

}
