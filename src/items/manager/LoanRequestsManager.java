package items.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.BookNotInArchiveException;
import exceptions.InvalidAdminException;
import exceptions.InvalidUserException;
import exceptions.ManagerAlreadyInitializedException;
import exceptions.RequestNotPresentException;

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

    public void initializeRequests(ArrayList<LoanRequest> requests) throws ManagerAlreadyInitializedException {
        if (this.requests.size() > 0) {
            throw new ManagerAlreadyInitializedException();
        }

        this.requests.addAll(requests);

    }


    /**File a request for a book on loan with the date of today, if one with same applicant and book request was already filed
     * @param applicant the user who wants to borrow a book
     * @param book the requested book
     * @throws InvalidUserException if the user is not accredited
     * @throws BookNotInArchiveException if the book is not available to loan (e.g. already borrowed, lost or ruined)
     */
    public LoanRequest makeBookRequest(User applicant, Book book) throws InvalidUserException, BookNotInArchiveException {

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


    /**If the request is filed, it will be accepted and removed from the pending ones
     * @param applicant an admin is necessary to approve/deny loans
     * @param request the loan request to accept
     * @throws InvalidAdminException if the admin is not accredited
     * @throws RequestNotPresentException if the request was not filed
     */
    public void acceptRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, RequestNotPresentException  {
        if (!requests.contains(request)) {
            throw new RequestNotPresentException();
        }
        request.accept(applicant);
        requests.remove(request);

    }

    /**If the request is filed, it will be removed from the pending ones
     * @param applicant an admin is necessary to approve/deny loans
     * @param request the loan request to deny
     * @throws InvalidAdminException if the admin is not accredited
     * @throws RequestNotPresentException if the request was not filed
     */
    public void denyRequest(Admin applicant, LoanRequest request) throws InvalidAdminException, RequestNotPresentException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException();
        }
        if (!requests.contains(request)) {
            throw new RequestNotPresentException();
        }
        requests.remove(request);
    }
    
}
