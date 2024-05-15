package users.sanctions;

import java.time.LocalDate;

public class RepeatedBookLostSanction extends Sanction {

    public static final String NAME_STRING = "Repeated lost book";
    public static final String DESCRIPTION_STRING = "This user has lost many books. His licence must be revoked";
    public static final Severity SEVERITY_VALUE = Severity.WORST;
    public static final int LIMIT = 2;

    public RepeatedBookLostSanction(LocalDate date) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date);
    }

}
