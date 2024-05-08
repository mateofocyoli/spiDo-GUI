package items.managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import exceptions.NotInArchiveException;
import exceptions.InvalidAdminException;
import exceptions.InvalidUserException;
import exceptions.ManagerAlreadyInitializedException;
import exceptions.RequestNotPresentException;

import items.Book;
import items.LoanRequest;
import items.Loanable;
import items.filters.LoanRequestFilter;
import users.Admin;
import users.User;
import users.managers.PersonManager;

public class LoanRequestsManager {

    public static final String LOAN_STATE_TAG = "loan state";

    public static final String OBJ_ID_TAG = "id";

    public static final String OBJ_NAME_TAG = "name";

    public static final String APPLICANT_TAG = "applicant";

    public static final String DATE_OF_REQ_TAG = "date";

    private static final String FILTER_NOT_COHERENT_MSG = "Criteria and argument are not coherent";

    private static final String BOOK_NOT_AVAILABLE_MSG = "The request cannot be accepted, this book is currently unavailable";

    private static Comparator<LoanRequest> compareByApplicant = 
        (LoanRequest r1, LoanRequest r2) -> r1.getApplicant().compareTo(r2.getApplicant().getCredentials());

    private static Comparator<LoanRequest> compareByDateOfRequest = 
        (LoanRequest r1, LoanRequest r2) -> r1.getDateOfRequest().compareTo(r2.getDateOfRequest());

    private static Comparator<LoanRequest> compareByObjectName = 
        (LoanRequest r1, LoanRequest r2) -> r1.getRequested().getName().compareTo(r2.getRequested().getName());

    private static Comparator<LoanRequest> compareByID = 
        (LoanRequest r1, LoanRequest r2) -> r1.getRequested().getID().compareToIgnoreCase(r2.getRequested().getID());

    /**Map used to store the comparators used to sort the loan requests
     */
    private static final Map<String, Comparator<LoanRequest>> ORDER_CRITERIAS = Map.ofEntries(
        entry(APPLICANT_TAG, compareByApplicant),
        entry(DATE_OF_REQ_TAG, compareByDateOfRequest),
        entry(OBJ_NAME_TAG, compareByObjectName),
        entry(OBJ_ID_TAG, compareByID)
    );

    private static LoanRequestFilter<String> filterByRequestedName = 
        (LoanRequest r, String name) -> r.getRequested().getName().compareToIgnoreCase(name) == 0;

    private static LoanRequestFilter<User> filterByApplicant = 
        (LoanRequest r, User applicant) -> r.getApplicant().compareTo(applicant.getCredentials()) == 0;

    private static LoanRequestFilter<LocalDate> filterByDateOfRequest = 
        (LoanRequest r, LocalDate DateOfRequest) -> r.getDateOfRequest().compareTo(DateOfRequest) == 0;


    private static LoanRequestFilter<Loanable.LoanState> filterByRequestedLoanState = 
        (LoanRequest r, Loanable.LoanState loanState) -> r.getRequested().getState().compareTo(loanState) == 0;

    /**Map used to store the filters used to get specific loan requests from the list
     */
    private static final Map<String, LoanRequestFilter<?>> FILTER_CRITERIAS = Map.ofEntries(
        entry(OBJ_NAME_TAG, filterByRequestedName),
        entry(APPLICANT_TAG, filterByApplicant),
        entry(DATE_OF_REQ_TAG, filterByDateOfRequest),
        entry(LOAN_STATE_TAG, filterByRequestedLoanState)
    );
    
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

    /**Initialize the request manager with a list of loan requests
     * @param requests to add to the list
     * @throws ManagerAlreadyInitializedException if the requests were already initialized, in this case
     * you'll have to use the makeRequest method to add requests to the list
     */
    public void initializeRequests(List<LoanRequest> requests) throws ManagerAlreadyInitializedException {
        if (this.requests.size() > 0) {
            throw new ManagerAlreadyInitializedException();
        }

        this.requests.addAll(requests);

    }


    /**File a request for a book on loan with the date of today, if one with same applicant and book request
     * was already filed
     * @param applicant the user who wants to borrow a book
     * @param book the requested book
     * @throws InvalidUserException if the user is not accredited
     * @throws NotInArchiveException if the book is not available to loan (e.g. already borrowed, lost or ruined)
     */
    public LoanRequest makeBookRequest(User applicant, Book book) throws InvalidUserException, NotInArchiveException {

        if (!PersonManager.getInstance().getUsers().contains(applicant)){
            throw new InvalidUserException();
        }
        if (!ArchiveManager.getInstance().isBookPresent(book)){ 
            throw new NotInArchiveException(BOOK_NOT_AVAILABLE_MSG);
        }
        if (!ArchiveManager.getInstance().searchBook(book.getID()).get(0).getState().equals(Loanable.LoanState.IN_ARCHIVE)) {
            throw new NotInArchiveException(BOOK_NOT_AVAILABLE_MSG);
        }
        
        for (LoanRequest request : requests){
            //if there is already a pending request of the requested book by the same applicant, it won't be added
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

    /**Acces a list of requests sorted by the specified criteria
     * @param criteria used to sort the requests, can be "applicant", "date" (of request), "name" (of the object)
     * or "id", default to "date"
     * @return the list of requests sorted by the specified criteria
     */
    public List<LoanRequest> getSortedRequestsBy(String criteria){
        requests.sort(ORDER_CRITERIAS.getOrDefault(criteria.toLowerCase(), compareByDateOfRequest));
        return requests;
    }

    /**Get a filtered list of pending loan requests by the specified criteria and argument
     * @param <T> the type of argument
     * @param criteria to filter the list of requests by
     * @param argument that has to match the specified criteria
     * @return a list of loan requests filtered by the specified criteria and argument
     * @throws ClassCastException if the criteria specified and the type of argument passed are not coherent
     */
    public <T> List<LoanRequest> filterBy(String criteria, T argument) throws ClassCastException {
        List<LoanRequest> newList = new ArrayList<>();
        try {
            LoanRequestFilter<T> filter = (LoanRequestFilter<T>) FILTER_CRITERIAS.getOrDefault(criteria, filterByRequestedName);
            for(LoanRequest element : requests) {
                if (filter.test(element, argument))
                    newList.add(element);
            }
        } catch (Exception e) {
            throw new ClassCastException(FILTER_NOT_COHERENT_MSG);
        }
        
        return newList;
    }
    
}
