package users;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public abstract class Person implements Comparable<Credentials> {

    private String name;
    private String surname;
    private Date birth;
    private String cityOfBirth;
    private Sex sex;
    private Credentials credentials;

    public enum Sex {
        MALE, FEMALE;
    }

    protected Person(String name, String surname, Date birth, String cityOfBirth, Sex sex, Credentials credentials) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.cityOfBirth = cityOfBirth;
        this.sex = sex;
        this.credentials = credentials;
    }

    public String getID() {
        return "calcolato";
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
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
}
