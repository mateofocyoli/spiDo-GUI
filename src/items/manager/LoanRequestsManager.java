package items.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.BookNotInArchiveException;
import exceptions.InvalidAdminException;
import exceptions.InvalidUserException;
import items.Book;
import items.LoanRequest;
import items.Loanable;
import users.Admin;
import users.PersonManager;
import users.User;

public class LoanRequestsManager {


    
    private static LoanRequestsManager instance;

    private List<LoanRequest> requests;

    private LoanRequestsManager() {
        requests = new ArrayList<>();
    }

    public static LoanRequestsManager getInstance(){
        if (instance == null){
            instance = new LoanRequestsManager();
        }
        return instance;
    }


    public LoanRequest makeRequest(User applicant, Book book) throws InvalidUserException, BookNotInArchiveException {

        if (!PersonManager.getInstance().getUsers().contains(applicant)){
            throw new InvalidUserException();
        }
        if (!ArchiveManager.getInstance().isBookPresent(book)){ 
            throw new BookNotInArchiveException();
        }
        if (!ArchiveManager.getInstance().searchBook(book.getID()).get(0).getState().equals(Loanable.LoanState.IN_ARCHIVE)) {
            throw new BookNotInArchiveException();
        }
        
        for (LoanRequest request : requests){
            //if there is already a pending request, it won't be added
            if (request.getApplicant().equals(applicant) && request.getRequested().equals(book))
                return request;
        }
        LoanRequest newRequest = new LoanRequest(applicant, book, LocalDate.now());
        requests.add(newRequest);
        return newRequest;
    }


    public void acceptRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, BookNotInArchiveException  {
        if (!requests.contains(request)){
            throw new BookNotInArchiveException();
        }
        request.accept(applicant);
    }

    public void denyRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, BookNotInArchiveException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException();
        }
        if (!requests.contains(request)) {
            throw new BookNotInArchiveException();
        }
        requests.remove(request);
    }
    
}
