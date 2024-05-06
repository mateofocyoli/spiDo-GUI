import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import exceptions.NotInArchiveException;
import items.*;
import items.manager.ArchiveManager;
import items.manager.LoanRequestsManager;
import users.*;
import users.Person.Sex;


public class MarcoMain {
    public static void main(String args[]) {
        List<LoanRequest> req = new ArrayList<>();
        LoanRequestsManager lrm = LoanRequestsManager.getInstance();

        User user = new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Sex.MALE, new Credentials("Kibo", "Siuum"));
        Admin admin = new Admin("Treccani", "Irene", LocalDate.now(), "Brescia", Sex.FEMALE, new Credentials("Admin", "merdina"));
        PersonManager pm = PersonManager.getInstance();
        pm.add(user);
        pm.add(admin);

        Book b = new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, Year.now(), 7);
        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> books = new ArrayList<>();
        books.add(b);
        am.initializeArchive(books);

        req.add(new LoanRequest(user, b, LocalDate.now().plusDays(0)));

        lrm.initializeRequests(req);

        for (LoanRequest lr : lrm.filterBy("date", LocalDate.now())) {
            System.out.println(lr.getRequested().toString());
        }

        for (Book book : am.filterBy("title", "La mia vita di merda")) {
            System.out.println(book.toString());
        }
        System.out.println();

        Book b2 = new Book("La bibbia", "Gesù", Book.Genre.FANTASY, Year.now(), 7);

        try {
            am.addBook(admin, b2);
            lrm.makeBookRequest(user, b2);
        } catch (IllegalAccessException | NotInArchiveException e) {
            e.printStackTrace();
        }

        for (Book book : am.filterBy("title", "La bibbia")) {
            System.out.println(book.toString());
        }

        for (LoanRequest lr : lrm.filterBy("date", LocalDate.now())) {
            System.out.println(lr.getRequested().toString());
        }

        System.out.println();

        try {
            lrm.acceptRequest(admin, lrm.filterBy(LoanRequestsManager.APPLICANT_TAG, user).get(0));
        } catch (IllegalAccessException | NotInArchiveException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Book loan = null;
        try {
            for (Book book : am.filterBy(ArchiveManager.LOAN_STATE_TAG, Loanable.LoanState.ON_LOAN)) {
                System.out.println(book.toString());
                loan = book;

            }

        }
        catch (Exception e){
            e.printStackTrace();
            
        }

        System.out.println();


        try {
            loan.setOnArchive(admin);
            for (Book book : am.filterBy(ArchiveManager.LOAN_STATE_TAG, Loanable.LoanState.IN_ARCHIVE)) {
                System.out.println(book.toString());
            }

        }
        catch (Exception e){
            e.printStackTrace();
            
        }


    }
}
