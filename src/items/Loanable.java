package items;

import java.util.Date;

import users.Admin;

public abstract class Loanable {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can can change the loan terms";
    
    public static enum LoanState{
        ON_LOAN,
        IN_ARCHIVE,
        LOST,
        RUINED
    }

    private LoanState state;
    private Date dueDate;
    
    public Loanable(LoanState state, Date dueDate) {
        this.state = state;
        this.dueDate = dueDate;
    }

    

    public void setState(Admin applicant, LoanState state) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        this.state = state;
    }



    public void setDueDate(Admin applicant, Date dueDate) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
    }



    public LoanState getState() {
        return state;
    }

    public Date getDueDate() {
        return dueDate;
    }

    
}
