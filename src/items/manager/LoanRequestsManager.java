package items.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import exceptions.InvalidAdminException;
import exceptions.InvalidUserException;
import items.Book;
import items.LoanRequest;
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


    public LoanRequest makeRequest(User applicant, Book book) throws InvalidUserException, NoSuchElementException {

        if (!PersonManager.getInstance().getUsers().contains(applicant)){
            throw new InvalidUserException();
        }
        if (!ArchiveManager.getInstance().isBookPresent(book)){ 
            throw new NoSuchElementException();
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


    public void acceptRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, NoSuchElementException  {
        if (!requests.contains(request)){
            throw new NoSuchElementException();
        }
        request.accept(applicant);
    }

    public void denyRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, NoSuchElementException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException();
        }
        if (!requests.contains(request)) {
            throw new NoSuchElementException();
        }
        requests.remove(request);
    }
    
}
