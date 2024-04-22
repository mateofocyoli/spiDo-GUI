package users;

import java.util.ArrayList;
import java.util.Date;

public class User extends Person {

    private ArrayList<String> prestiti;

    public User(String name, String surname, Date birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);

        prestiti = new ArrayList<>();
    }
}
