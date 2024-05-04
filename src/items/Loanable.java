package items;

import java.time.LocalDate;
import java.util.UUID;

import exceptions.InvalidAdminException;

import users.Admin;
import users.User;
import users.PersonManager;

public abstract class Loanable {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can can change the loan terms";
    
    /**State of loanable object, which can be borrowed only if it is IN_ARCHIVE
     */
    public static enum LoanState{
        ON_LOAN,
        IN_ARCHIVE,
        LOST,
        RUINED
    }

    private String name;
    private LoanState state;
    private LocalDate dueDate;
    private final String ID;
    private User borrower;
    
    public Loanable(String name, LoanState state, LocalDate dueDate) {
        this.name = name;
        this.state = state;
        this.dueDate = dueDate;
        this.ID = createID();
        this.borrower = null;
    }

    private String createID() {
        return UUID.randomUUID().toString();
    }
    
    /**Sets the loanable object state to ON_LOAN to borrower for one month starting from the moment this method is called
     * @param applicant an admin is necessary to modify loan states
     * @param borrower the user that will have on loan the object
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void setOnLoanTo(Admin applicant, User borrower) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.ON_LOAN;
        this.dueDate = LocalDate.now().plusMonths(1);
        this.borrower = borrower;
    }

    /**Sets the loanable object to IN_ARCHIVE, so it will be available for other borrowers, sets the borrower to null
     * @param applicant an admin is necessary to modify loan states
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void setOnArchive(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.IN_ARCHIVE;
        this.borrower = null;
    }

    /**Sets the loanable object state to LOST without modifying the borrower, so that the person who is resposible is known
     * @param applicant an admin is necessary to modify loan states
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void setLost(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.LOST;
        
    }

    /**Sets the loanable object state to RUINED without modifying the borrower, so that the person who is resposible is known
     * @param applicant an admin is necessary to modify loan states
     * @throws InvalidAdminException if the admin is not accredited
     */
    public void setRuined(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.RUINED;
    }

    

    public String getName() {
        return name;
    }

    public LoanState getState() {
        return state;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public User getBorrower() {
        return borrower;
    }

    public String getID(){
        return ID;
    }

    
}
