package users;

import java.time.LocalDate;

public class Admin extends Person {

    public Admin(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);
    }

}
