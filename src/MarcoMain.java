import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import items.*;
import items.manager.ArchiveManager;
import items.manager.LoanRequestsManager;
import users.*;
import users.Person.Sex;


public class MarcoMain {
    public static void main(String args[]) {
        List<LoanRequest> req = new ArrayList<>();
        LoanRequestsManager lrm = LoanRequestsManager.getInstance();

        User u = new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Sex.MALE, new Credentials("Kibo", "Siuum"));
        PersonManager pm = PersonManager.getInstance();
        pm.add(u);

        Book b = new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, Year.now(), 7);
        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> books = new ArrayList<>();
        books.add(b);
        am.initializeArchive(books);

        req.add(new LoanRequest(u, b, LocalDate.now()));

        lrm.initializeRequests(req);

        for (LoanRequest lr : lrm.filterBy("date", LocalDate.now().plusDays(1))) {
            System.out.println(lr.getRequested().toString());
        }

        for (Book book : am.filterBy("title", "La mia vita di merda")) {
            System.out.println(book.toString());
        }
    }
}
