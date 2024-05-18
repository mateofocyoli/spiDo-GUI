package users;

import java.security.InvalidParameterException;
import java.time.LocalDate;

import com.google.gson.annotations.Expose;

/**
 * Class used to represent the people signed up in the library.
 * This is an abstract class used only to enable polymorphism.
 * In order to create a object, use the subclasses.
 */
public abstract class Person implements Comparable<Credentials> {

    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private LocalDate birth;
    @Expose
    private String cityOfBirth;
    @Expose
    private Sex sex;
    @Expose
    private Credentials credentials;

    public enum Sex {
        MALE, FEMALE;
    }

    /**
     * Constructor for the object Person. Since it's an abstract class, it can only
     * be
     * called by a non-abstract subclass.
     * 
     * @param name        The name of the person (can not be null, neither blank)
     * @param surname     The surname of the person (can not be null, neither blank)
     * @param birth       The date of birth of the person (can not be null)
     * @param cityOfBirth The city of birth of the person (can not be null, neither
     *                    blank)
     * @param sex         The sex of the person (use the appropriate enum)
     * @param credentials The credential object of the person (can not be null)
     * @throws IllegalParameterException If an invalid parameter is passed
     */
    protected Person(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex,
            Credentials credentials) {
        if (name == null)
            throw new InvalidParameterException("name can not be null");
        if (name.isBlank())
            throw new InvalidParameterException("name can not be blank");

        if (surname == null)
            throw new InvalidParameterException("surname can not be null");
        if (surname.isBlank())
            throw new InvalidParameterException("surname can not be blank");

        if (birth == null)
            throw new InvalidParameterException("birth can not be null");

        if (cityOfBirth == null)
            throw new InvalidParameterException("cityOfBirth can not be null");
        if (cityOfBirth.isBlank())
            throw new InvalidParameterException("cityOfBirth can not be blank");

        if (sex == null)
            throw new InvalidParameterException("sex can not be null");

        if (credentials == null)
            throw new InvalidParameterException("credentials can not be null");

        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.cityOfBirth = cityOfBirth;
        this.sex = sex;
        this.credentials = credentials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Compares the credentials of this person object with the credentials passed to the method.
     * It calls the {@code compareTo(Credentials o)} method of this object's credentials.
     */
    @Override
    public int compareTo(Credentials o) {
        return this.credentials.compareTo(o);
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + " [name=" + name + ", surname=" + surname + ", birth=" + birth
                + ", cityOfBirth=" + cityOfBirth
                + ", sex=" + sex + ", username=" + credentials.getUsername() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((birth == null) ? 0 : birth.hashCode());
        result = prime * result + ((cityOfBirth == null) ? 0 : cityOfBirth.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        result = prime * result + ((credentials == null) ? 0 : credentials.hashCode());
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
        Person other = (Person) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (birth == null) {
            if (other.birth != null)
                return false;
        } else if (!birth.equals(other.birth))
            return false;
        if (cityOfBirth == null) {
            if (other.cityOfBirth != null)
                return false;
        } else if (!cityOfBirth.equals(other.cityOfBirth))
            return false;
        if (sex != other.sex)
            return false;
        if (credentials == null) {
            if (other.credentials != null)
                return false;
        } else if (!credentials.equals(other.credentials))
            return false;
        return true;
    }
}
