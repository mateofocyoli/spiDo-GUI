import java.time.LocalDate;

import items.*;


public class MarcoMain {
    public static void main(String args[]) {
        Book book = new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, LocalDate.now(), 27);
        System.out.println(book.toString());
    }
}
