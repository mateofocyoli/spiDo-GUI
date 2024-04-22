package users;

import java.util.Date;

public class Admin extends Person {

    protected Admin(String name, String surname, Date birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);
    }

}
