package items;

import java.time.LocalDate;

import exceptions.InvalidAdminException;
import exceptions.NotInArchiveException;
import users.Admin;
import users.User;
import users.managers.PersonManager;

public class LoanRequest {

    private static final String OBJ_NOT_AVAILABLE_MSG = "The request cannot be accepted, this object is currently unavailable";

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can accept or deny requests";

    private User applicant;
    private Loanable requested;
    private boolean accepted;
    private LocalDate dateOfRequest;


    public LoanRequest(User applicant, Loanable requested, LocalDate dateOfRequest) {
        this.applicant = applicant;
        this.requested = requested;
        this.dateOfRequest = dateOfRequest;
        this.accepted = false;
    }







    public User getApplicant() {
        return applicant;
    }


    public Loanable getRequested() {
        return requested;
    }


    public boolean isAccepted() {
        return accepted;
    }

        
    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    /**The loan request is accepted: the loanable object will be borrowed by the applicant
     * @param admin an admin is necessary to accept loan requests
     * @throws InvalidAdminException if admin is not accredited
     * @throws NotInArchiveException if the object is not available to borrow
     */
    public void accept(Admin admin) throws InvalidAdminException, NotInArchiveException {
        if (!PersonManager.getInstance().getAdmins().contains(admin)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }
        if (!this.requested.getState().equals(Loanable.LoanState.IN_ARCHIVE)){
            throw new NotInArchiveException(OBJ_NOT_AVAILABLE_MSG);
        }

        this.accepted = true;
        this.requested.setOnLoanTo(admin, applicant);
    }


    




    

}
