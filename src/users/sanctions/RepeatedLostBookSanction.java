package users.sanctions;

import java.util.Date;

public class RepeatedLostBookSanction extends Sanction {

    public static final String NAME_STRING = "Repeated lost book";
    public static final String DESCRIPTION_STRING = "This user has lost many books. His licence must be revoked";
    public static final Severity SEVERITY_VALUE = Severity.WORST;

    public RepeatedLostBookSanction(Date date) {
        super(NAME_STRING, DESCRIPTION_STRING, SEVERITY_VALUE, date);
    }

}
