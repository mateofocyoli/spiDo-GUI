package users.sanctions;

import java.time.LocalDate;
import items.Book;

public class BookBrokenSanction extends BookSanction {

    public static final String NAME_STRING = "Book broken";
    public static final String DESCRIPTION_STRING = "The book has been returned broken. It can not be rented again and must be bought new";
    public static final Severity SEVERITY_VALUE = Severity.HIGH;

    public BookBrokenSanction(LocalDate date, Book book) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date, book);
    }

}
