package users.sanctions;

import java.time.LocalDate;
import items.Book;

public class BookDelayedSanction extends BookSanction {

    public static final String NAME_STRING = "Book delayed";
    public static final String DESCRIPTION_STRING = "The book has been returned after the expiration date";
    public static final Severity SEVERITY_VALUE = Severity.LOW;

    public BookDelayedSanction(LocalDate date, Book book) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date, book);
    }

}
