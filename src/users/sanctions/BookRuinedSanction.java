package users.sanctions;

import java.time.LocalDate;
import items.Book;

public class BookRuinedSanction extends BookSanction {

    public static final String NAME_STRING = "Book ruined";
    public static final String DESCRIPTION_STRING = "The book has been returned ruined. It can be rent without needing repair";
    public static final Severity SEVERITY_VALUE = Severity.MEDIUM;

    public BookRuinedSanction(LocalDate date, Book book) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date, book);
    }

}
