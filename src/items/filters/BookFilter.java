package items.filters;

import items.Book;

public interface BookFilter<T> {
    public boolean test(Book t, T argument);

}
