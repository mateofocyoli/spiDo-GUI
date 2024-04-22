package users.sanctions;

import java.util.Date;
import items.Book;

public class BookSanction extends Sanction {

    public final Book book;

    public BookSanction(String name, String description, Severity severity, Date date, Book book) {
        super(name, description, severity, date);
        this.book = book;
    }
}
