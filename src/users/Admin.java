package users;

import java.time.LocalDate;

/**
 * An implementation of the {@code Person} class for the admins of the library.
 * This class can be instatiated using it's constructor.
 * Admin can use some privileged method of {@code PersonManager}.
 */
public class Admin extends Person {

    /**
     * Constructor for the object Admin. It calls the superclass' constructor.
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
    public Admin(String name, String surname, LocalDate birth, String cityOfBirth, Sex sex, Credentials credentials) {
        super(name, surname, birth, cityOfBirth, sex, credentials);
    }

}
