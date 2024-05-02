package items.manager;

import java.util.ArrayList;
import java.util.List;

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

    public void makeRequest(User applicant, Book book) throws InvalidUserException {
        if (!PersonManager.getInstance().getUsers().contains(applicant))
            throw new InvalidUserException();
        
        
    }
    
}
