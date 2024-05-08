import java.time.LocalDate;
import java.util.ArrayList;

import database.FileManager;
import items.*;
import items.managers.ArchiveManager;
import items.managers.LoanRequestsManager;
import users.*;


public class MarcoMain {
    public static void main(String args[]) {

        LoanRequestsManager lrm = LoanRequestsManager.getInstance();

        PersonManager pm = PersonManager.getInstance();
        User[] users = { new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Person.Sex.MALE, new Credentials("kibo", "culo")), 
                        new User("Irene", "Treccani", LocalDate.now(), "Brescia", Person.Sex.FEMALE, new Credentials("merdina", "cacca")) 
                    };
        
        for (int i = 0; i < users.length; i++){
            pm.add(users[i]);
        }
                        
        /*Book[] books = { new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, Year.now(), 7) , 
                        new Book("Bibbia", "jesoo", Book.Genre.FANTASY, Year.now(), 7) 
                        };
        
        LoanRequest[] requs = {
            new LoanRequest(users[0], books[0], LocalDate.now()),
            new LoanRequest(users[1], books[1], LocalDate.now())
        };*/

        ArrayList<LoanRequest> requests = new ArrayList<LoanRequest>();


        /*for (int i = 0; i < requs.length; i++){
            requests.add(requs[i]);
        }*/

        ArchiveManager am = ArchiveManager.getInstance();



        ArrayList<Book> archive = new ArrayList<>();

        try {
            archive = FileManager.readArchiveJSON(FileManager.DEFAULT_ARCHIVE_FILENAME);
            am.initializeArchive(archive);
            requests = FileManager.readRequestsJSON(FileManager.DEFAULT_LOAN_REQ_FILENAME);
            lrm.initializeRequests(requests);
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (Book book : am.getSortedBooksBy("")) {
            System.out.println(book.toString());
        }

        
        for (LoanRequest lr : lrm.getSortedRequestsBy("")) {
            System.out.println(lr.getRequested().toString());
        }
        System.out.println();

    }
}
