package users.sanctions;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

/**
 * Abstract class to represent a sanction given to a user. A sanction has a name, a description, a severity
 * and a date when the sanction was given.
 */
public abstract class Sanction {
    
    public static final String NAME = "abstract Sanction";
    public static final String DESCRIPTION = "This is an abstract sanction and should not be used";
    public static final Severity SEVERITY = null;
    
    @Expose
    public final LocalDate date;
    
    protected Sanction(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [name=" + NAME + ", severity=" + SEVERITY + ", date=" + date + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
        result = prime * result + ((SEVERITY == null) ? 0 : SEVERITY.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sanction other = (Sanction) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }
}
