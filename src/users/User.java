package users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import items.Book;
import users.sanctions.Sanction;

public class User extends Person {

    private List<Book> prestiti;
    private List<Sanction> sanctions;

    public User(String name, String surname, Date birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);

        prestiti = new ArrayList<>();
        sanctions = new ArrayList<>();
    }

    public boolean addSanction(Sanction s) {
        return sanctions.add(s);
    }

    public boolean removeSanction(Sanction s) {
        return sanctions.remove(s);
    }
}
