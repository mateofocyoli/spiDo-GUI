package users.sanctions;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

/**
 * Abstract class to represent a sanction given to a user. A sanction has a name, a description, a severity
 * and a date when the sanction was given.
 */
public abstract class Sanction {
    
    @Expose
    public final String name;
    @Expose
    public final String description;
    @Expose
    public final Severity severity;
    @Expose
    public final LocalDate date;
    
    protected Sanction(String name, String description, Severity severty, LocalDate date) {
        this.name = name;
        this.description = description;
        this.severity = severty;
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [name=" + name + ", severity=" + severity + ", date=" + date + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((severity == null) ? 0 : severity.hashCode());
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
