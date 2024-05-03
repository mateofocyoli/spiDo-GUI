package items;

import users.User;

import java.time.LocalDate;

import exceptions.InvalidAdminException;
import users.Admin;
import users.PersonManager;

public class LoanRequest {

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

    public void accept(Admin admin) throws InvalidAdminException {
        if (!PersonManager.getInstance().getAdmins().contains(admin)) {
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        }

        this.accepted = true;
        this.requested.setOnLoanTo(admin, applicant);
    }


    




    

}
