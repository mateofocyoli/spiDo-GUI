package users.sanctions;

import java.time.LocalDate;

public class Sanction {

    public final String name;
    public final String description;
    public final Severity severity;
    public final LocalDate date;
    
    public Sanction(String name, String description, Severity severity, LocalDate date) {
        this.name = name;
        this.description = description;
        this.severity = severity;
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (severity != other.severity)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }
}
