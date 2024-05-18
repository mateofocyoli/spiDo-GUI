package users.sanctions;

import java.time.LocalDate;

/**
 * Sanction used to indicate that a user has never brought back books for multiple times
 */
public class RepeatedBookLostSanction extends Sanction {

    public static final String NAME = "Repeated lost book";
    public static final String DESCRIPTION = "This user has lost many books. His licence must be revoked";
    public static final Severity SEVERITY = Severity.WORST;
    public static final int LIMIT = 2;

    public RepeatedBookLostSanction(LocalDate date) {
        super(date);
    }

}
