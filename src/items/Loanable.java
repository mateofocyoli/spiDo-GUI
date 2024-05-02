package items;

import java.time.LocalDate;
import java.util.UUID;

import exceptions.InvalidAdminException;
import users.Admin;
import users.PersonManager;
import users.User;

public abstract class Loanable {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can can change the loan terms";
    
    public static enum LoanState{
        ON_LOAN,
        IN_ARCHIVE,
        LOST,
        RUINED
    }

    private LoanState state;
    private LocalDate dueDate;
    private final String ID;
    private User borrower;
    
    public Loanable(LoanState state, LocalDate dueDate) {
        this.state = state;
        this.dueDate = dueDate;
        this.ID = createID();
        this.borrower = null;
    }

    private String createID() {
        return UUID.randomUUID().toString();
    }
    

    public void setOnLoanTo(Admin applicant, User borrower) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.ON_LOAN;
        this.dueDate = LocalDate.now().plusMonths(1);
        this.borrower = borrower;
    }

    public void setOnArchive(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.IN_ARCHIVE;
        this.borrower = null;
    }

    public void setLost(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.LOST;
        
    }

    public void setRuined(Admin applicant) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(applicant)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        this.state = LoanState.RUINED;
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
