package users.sanctions;

import java.time.LocalDate;
import items.Book;

public class BookSanction extends Sanction {

    public final Book book;

    public BookSanction(String name, String description, Severity severity, LocalDate date, Book book) {
        super(name, description, severity, date);
        this.book = book;
    }
}
