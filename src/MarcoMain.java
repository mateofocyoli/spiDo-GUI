import java.time.LocalDate;
import java.util.ArrayList;

import database.FileManager;
import items.*;
import items.manager.ArchiveManager;
import items.manager.LoanRequestsManager;
import users.*;
import users.Person.Sex;


public class MarcoMain {
    public static void main(String args[]) {

        LoanRequestsManager lrm = LoanRequestsManager.getInstance();

        User user = new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Sex.MALE, new Credentials("Kibo", "Siuum"));
        User admin = new User("Treccani", "Irene", LocalDate.now(), "Brescia", Sex.FEMALE, new Credentials("merdina", "cacca"));
        PersonManager pm = PersonManager.getInstance();
        pm.add(user);
        pm.add(admin);

        ArchiveManager am = ArchiveManager.getInstance();



        ArrayList<Book> archive = new ArrayList<>();
        ArrayList<LoanRequest> requests = new ArrayList<>();

        try {
            archive = FileManager.readArchiveJSON(FileManager.DEFAULT_ARCHIVE_FILENAME);
            requests = FileManager.readRequestsJSON(FileManager.DEFAULT_LOAN_REQ_FILENAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        am.initializeArchive(archive);
        lrm.initializeRequests(requests);





        
        for (LoanRequest lr : lrm.getSortedRequestsBy("")) {
            System.out.println(lr.getRequested().toString());
        }

        for (Book book : am.getSortedBooksBy("")) {
            System.out.println(book.toString());
        }
        System.out.println();
        /*
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

        //FileManager.writeArchiveJSON();
        */

    }
}
