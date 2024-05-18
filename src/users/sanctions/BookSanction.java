package users.sanctions;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

import items.Book;

/**
 * Abstract class used to represent book related sanctions
 */
public abstract class BookSanction extends Sanction {

    @Expose
    public final Book book;

    protected BookSanction(String name, String description, Severity severity, LocalDate date, Book book) {
        super(name, description, severity, date);
        this.book = book;
    }
}
