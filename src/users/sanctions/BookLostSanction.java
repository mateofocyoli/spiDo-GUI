package users.sanctions;

import java.time.LocalDate;
import items.Book;

/**
 * Sanction used to indicate that a user has never brought back the book
 */
public class BookLostSanction extends BookSanction {

    public static final String NAME = "Book lost";
    public static final String DESCRIPTION = "The book has been lost. It must be bought new";
    public static final Severity SEVERITY = Severity.HIGH;

    public BookLostSanction(LocalDate date, Book book) {
        super(NAME, DESCRIPTION, SEVERITY, date, book);
    }

}
