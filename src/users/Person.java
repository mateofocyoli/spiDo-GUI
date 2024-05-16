package users;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

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

    protected Person(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex, Credentials credentials) {
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

    @Override
    public int compareTo(Credentials o) {
        return this.credentials.compareTo(o);
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + " [name=" + name + ", surname=" + surname + ", birth=" + birth + ", cityOfBirth=" + cityOfBirth
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
