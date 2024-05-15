package users.sanctions;

import java.time.LocalDate;
import items.Book;

public class BookLostSanction extends BookSanction {

    public static final String NAME_STRING = "Book lost";
    public static final String DESCRIPTION_STRING = "The book has been lost. It must be bought new";
    public static final Severity SEVERITY_VALUE = Severity.HIGH;

    public BookLostSanction(LocalDate date, Book book) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date, book);
    }

}
