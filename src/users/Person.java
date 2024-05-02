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
}
