package items;

import java.time.LocalDate;
import java.util.UUID;

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
    private LocalDate dueDate;
    public final String ID;
    
    public Loanable(LoanState state, LocalDate dueDate) {
        this.state = state;
        this.dueDate = dueDate;
        this.ID = createID();
    }

    private String createID() {
        return UUID.randomUUID().toString();
    }
    

    public void setState(Admin applicant, LoanState state) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        this.state = state;
    }



    public void setDueDate(Admin applicant, LocalDate dueDate) throws IllegalAccessException {
        if (applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
    }



    public LoanState getState() {
        return state;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    
}
