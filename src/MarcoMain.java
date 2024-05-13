import java.time.LocalDate;
import java.util.ArrayList;

import database.FileManager;
import items.*;
import items.managers.ArchiveManager;
import items.managers.LoanRequestsManager;
import users.*;
import users.managers.PersonManager;


public class MarcoMain {
    private static final String DEFAULT_LOAN_REQ_FILENAME = "assets/savefiles/loanRequests.json";
    private static final String DEFAULT_ARCHIVE_FILENAME = "assets/savefiles/archive.json";

    public static void main(String args[]) {

        LoanRequestsManager lrm = LoanRequestsManager.getInstance();

        PersonManager pm = PersonManager.getInstance();
        User[] users = { new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Person.Sex.MALE, new Credentials("kibo", "culo")), 
                        new User("Irene", "Treccani", LocalDate.now(), "Brescia", Person.Sex.FEMALE, new Credentials("merdina", "cacca")) 
                    };
        
        for (int i = 0; i < users.length; i++){
            pm.add(users[i]);
        }

        Admin admin = new Admin("Marco", "Corazzina", LocalDate.now(), "Brescia", Person.Sex.MALE, new Credentials("phoenix", "sonounamerda"));
        pm.add(admin);           
        

        ArrayList<LoanRequest> requests = new ArrayList<LoanRequest>();


        ArchiveManager am = ArchiveManager.getInstance();



        ArrayList<Book> archive = new ArrayList<>();

        try {
            archive = FileManager.readArchiveJSON(DEFAULT_ARCHIVE_FILENAME);
            am.initializeArchive(archive);
            requests = FileManager.readRequestsJSON(DEFAULT_LOAN_REQ_FILENAME);
            lrm.initializeRequests(requests);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Book[] books = { new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, Year.now(), 7, "") , 
                        new Book("Bibbia", "jesoo", Book.Genre.FANTASY, Year.now(), 7, "") 
                        };
        
        /*LoanRequest[] requs = {
            new LoanRequest(users[0], books[0], LocalDate.now()),
            new LoanRequest(users[1], books[1], LocalDate.now())
        };

        try {
            am.addBook(admin, books[0]);
            am.addBook(admin, books[1]);
            lrm.makeBookRequest(users[0], books[0]);
            lrm.makeBookRequest(users[1], books[1]);
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        for (Book book : am.getSortedBooksBy(null)) {
            System.out.println(book.toString());
        }

        
        for (LoanRequest lr : lrm.getSortedRequestsBy(null)) {
            System.out.println(lr.getRequested().toString());
        }
        System.out.println();


        try {
            lrm.acceptRequest(admin, lrm.filterBy(LoanRequestsManager.Criteria.APPLICANT, pm.filterBy(PersonManager.Criteria.USERNAME, "merdina").get(0)).get(0));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Book book : am.getSortedBooksBy(null)) {
            System.out.println(book.toString());
        }

        
        for (LoanRequest lr : lrm.getSortedRequestsBy(null)) {
            System.out.println(lr.getRequested().toString());
        }
        System.out.println();


        try {
            FileManager.writeArchiveJSON(am.getSortedBooksBy(ArchiveManager.Criteria.TITLE), DEFAULT_ARCHIVE_FILENAME);
            FileManager.writeRequestsJSON(lrm.getSortedRequestsBy(LoanRequestsManager.Criteria.DATE_OF_REQ), DEFAULT_LOAN_REQ_FILENAME);
            
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
