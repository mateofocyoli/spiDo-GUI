package users;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import users.sanctions.Sanction;

/**
 * An implementation of the {@code Person} class for the users of the library.
 * This class can be instatiated using it's constructor.
 * Users have a list of {@code Sanctions} and {@code Books} can be assigned to
 * them.
 */
public class User extends Person {

    @Expose
    private List<Sanction> sanctions;

    /**
     * Constructor for the object User. It calls the superclass' constructor.
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
    public User(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);

        sanctions = new ArrayList<>();
    }

    /**
     * Adds directly a sanction to the list. This method is supposed to be used only
     * by a
     * {@code PersonManager}. Refer to {@code PersonManager.sanction(...)} in order
     * to give
     * a sanction to a User object.
     * 
     * @param s The sanction to add to the list (can not be null)
     * @return The return value of the call to the method {@code List.add(Object o)}
     * @throws InvalidParameterException If the sanction object is null
     */
    public boolean addSanction(Sanction s) {
        if (s == null)
            throw new InvalidParameterException("sanction can not be null");

        return sanctions.add(s);
    }

    /**
     * Removes directly a sanction to the list. This method is supposed to be used
     * only by a
     * {@code PersonManager}. Refer to {@code PersonManager.pardon(...)} in order
     * to remove a sanction from a User object.
     * 
     * @param s The sanction to remove from the list
     * @return The return value of the call to the method {@code List.remove(Object o)}
     */
    public boolean removeSanction(Sanction s) {
        return sanctions.remove(s);
    }

    public List<Sanction> getSanctions() {
        return sanctions;
    }
}
