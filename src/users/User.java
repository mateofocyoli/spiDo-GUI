package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import users.sanctions.Sanction;

public class User extends Person {

    @Expose
    private List<Sanction> sanctions;

    public User(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);

        sanctions = new ArrayList<>();
    }

    public boolean addSanction(Sanction s) {
        return sanctions.add(s);
    }

    public boolean removeSanction(Sanction s) {
        return sanctions.remove(s);
    }

    public List<Sanction> getSanctions() {
        List<Sanction> clone = new ArrayList<>();
        clone.addAll(sanctions);
        return clone;
    }
}
