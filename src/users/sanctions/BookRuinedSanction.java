package users.sanctions;

import java.time.LocalDate;
import items.Book;

/**
 * Sanction used to indicate that a book has been brought back ruined
 */
public class BookRuinedSanction extends BookSanction {

    public static final String NAME = "Book ruined";
    public static final String DESCRIPTION = "The book has been returned ruined. It can be rent without needing repair";
    public static final Severity SEVERITY = Severity.MEDIUM;

    public BookRuinedSanction(LocalDate date, Book book) {
        super(date, book);
    }

}
