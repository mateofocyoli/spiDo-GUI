package users.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import exceptions.InvalidAdminException;
import exceptions.ManagerAlreadyInitializedException;
import items.Book;
import items.Loanable;
import items.managers.ArchiveManager;
import users.sanctions.BookLostSanction;
import users.sanctions.RepeatedBookLostSanction;
import users.sanctions.Sanction;
import users.*;
import users.Person.Sex;
import users.filters.*;

import static java.util.Map.entry;

import java.time.LocalDate;

public class PersonManager {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can can change the loan terms";
    private static final String FILTER_NOT_COHERENT_MSG = "Criteria and argument are not coherent";

    public enum Criteria {
        NAME, SURNAME, DATE_OF_BIRTH, CITY_OF_BIRTH, SEX, USERNAME, TYPE
    }

    private static Comparator<Person> compareByName = (Person p1, Person p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private static Comparator<Person> compareBySurname = (Person p1, Person p2) -> p1.getSurname().compareToIgnoreCase(p2.getSurname());
    private static Comparator<Person> compareByBirth = (Person p1, Person p2) -> p1.getBirth().compareTo(p2.getBirth());
    private static Comparator<Person> compareByCityOfBirth = (Person p1, Person p2) -> p1.getCityOfBirth().compareToIgnoreCase(p2.getCityOfBirth());
    private static Comparator<Person> compareBySex = (Person p1, Person p2) -> p1.getSex().compareTo(p2.getSex());
    private static Comparator<Person> compareByUsername = (Person p1, Person p2) -> p1.getCredentials().compareTo(p2.getCredentials());
    private static Comparator<Person> compareByType = (Person p1, Person p2) -> p1.getClass().getCanonicalName().compareTo(p2.getClass().getCanonicalName());

    private static final Map<Criteria, Comparator<Person>> ORDER_CRITERIAS = Map.ofEntries(
        entry(Criteria.NAME, compareByName),
        entry(Criteria.SURNAME, compareBySurname),
        entry(Criteria.DATE_OF_BIRTH, compareByBirth),
        entry(Criteria.CITY_OF_BIRTH, compareByCityOfBirth),
        entry(Criteria.SEX, compareBySex),
        entry(Criteria.USERNAME, compareByUsername),
        entry(Criteria.TYPE, compareByType)
    );

    private static PersonFilter<String> filterByName = (Person p, String name) -> p.getName().toLowerCase().contains(name.toLowerCase());
    private static PersonFilter<String> filterBySurname = (Person p, String surname) -> p.getName().toLowerCase().contains(surname.toLowerCase());
    private static PersonFilter<LocalDate> filterByBirth = (Person p, LocalDate birth) -> p.getBirth().compareTo(birth) == 0;
    private static PersonFilter<String> filterByCityOfBirth = (Person p1, String cityOfBirth) -> p1.getCityOfBirth().toLowerCase().contains(cityOfBirth.toLowerCase());
    private static PersonFilter<Person.Sex> filterBySex = (Person p1, Person.Sex sex) -> p1.getSex().compareTo(sex) == 0;
    private static PersonFilter<String> filterByUsername = (Person p1, String username) -> p1.getCredentials().getUsername().toLowerCase().contains(username.toLowerCase());
    private static PersonFilter<Class<? extends Person>> filterByType = (Person p1, Class<? extends Person> type) -> p1.getClass().getCanonicalName().compareTo(type.getCanonicalName()) == 0;

    private static final Map<Criteria, PersonFilter<?>> FILTER_CRITERIAS = Map.ofEntries(
        entry(Criteria.NAME, filterByName),
        entry(Criteria.SURNAME, filterBySurname),
        entry(Criteria.DATE_OF_BIRTH, filterByBirth),
        entry(Criteria.CITY_OF_BIRTH, filterByCityOfBirth),
        entry(Criteria.SEX, filterBySex),
        entry(Criteria.USERNAME, filterByUsername),
        entry(Criteria.TYPE, filterByType)
    );

    private static final Admin DEFAULT_ADMIN = new Admin("admin", "account",
                                                        LocalDate.of(1970, 1, 1),
                                                        "java-town", Sex.FEMALE,
                                                        new Credentials("a", "p"));

    private static PersonManager instance;

    private List<Person> people;

    private PersonManager() {
        this.people = new ArrayList<>();
    }

    /**
     * Only way to get an instance of this class, so there are no other instances around
     * @return
     */
    public static PersonManager getInstance() {
        if(instance == null)
            instance = new PersonManager();

        return instance;
    }

    /* public PersonManager(Collection<? extends Person> people) {
        this.people = new ArrayList<>();
        this.people.addAll(people);
    } */

    /**
     * Finds the user's object associated to the credentials passed as arguments.
     * @param username String representing the user's username
     * @param password String representing the user's password
     * @return The person object instance of the user who is trying to log in. If the password is wrong or the username does not belong to any user registered in the system, the method returns {@code null}.
     */
    public Person login(String username, String password) {

        List<Person> list = filterBy(people, Criteria.USERNAME, username);
        
        if(list.isEmpty())
            return null;

        Person p = list.get(0);

        if(p == null)
            return null;

        if(p.getCredentials().check(username, password))
            return p;
        
        return null;
    }

    /**
     * Adds a new account to the system. No admin object is required.
     * The person object can not have any field left {@code null} or empty.
     * There cannot be two accounts with the same credentials (same username).
     * @param p The new account's object to add into the system
     * @return {@code true} if the account has been added successfully
     */
    public boolean add(Person p) {
        if(p.getName() == null || p.getName().isBlank())
            return false;
        
        if(p.getSurname() == null || p.getSurname().isBlank())
            return false;
        
        if(p.getBirth() == null)
            return false;
        
        if(p.getCityOfBirth() == null || p.getCityOfBirth().isBlank())
            return false;
        
        if(p.getSex() == null)
            return false;
        
        if(p.getCredentials() == null)
            return false;
        
        // Find an account with the same username and return false if one is found
        String username = p.getCredentials().getUsername();
        if(!validUsername(username))
            return false;

        return people.add(p);
    }

    /**
     * It is necessary to pass the applicant instance to verify that he can modify the list.
     * An Admin can not remove itself.
     * You can't remove a user that has ON_LOAN books attached to him.
     * @param applicant The admin requesting the action.
     * @param p The person who is to be removed from the system
     * @return {@code true} if the system did contain the person removed.
     * @throws InvalidAdminException 
     */
    public boolean remove(Admin applicant, Person p) throws InvalidAdminException {
        if(!validApplicant(applicant))
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        
        if(applicant.equals(p))
            return false;
        
        if(p instanceof User) {
            User u = (User) p;
            ArchiveManager am = ArchiveManager.getInstance();
            List<Book> onLoan = am.filterBy(ArchiveManager.Criteria.LOAN_STATE, Loanable.LoanState.ON_LOAN);
            for(Book b : onLoan) {
                if(b.getBorrower().equals(u))
                    return false;
            }
        }
        
        return people.remove(p);
    }

    public List<Person> sortBy(Criteria criteria) {
        people.sort(ORDER_CRITERIAS.getOrDefault(criteria, compareBySurname));
        return getList();
    }

    public List<Person> getList() {
        ArrayList<Person> cloneList = new ArrayList<>();
        cloneList.addAll(people);
        return cloneList;
    }

    public List<User> getUsers() {
        List<Person> personList = filterBy(people, Criteria.TYPE, User.class);
        List<User> usersList = new ArrayList<>();
        
        for(Person p : personList)
            usersList.add((User) p);

        return usersList;
    }

    public List<Admin> getAdmins() {
        List<Person> personList = filterBy(people, Criteria.TYPE, Admin.class);
        List<Admin> adminsList = new ArrayList<>();
        
        for(Person p : personList)
            adminsList.add((Admin) p);

        return adminsList;
    }

    public static void sortBy(List<? extends Person> list, Criteria criteria) {
        list.sort(ORDER_CRITERIAS.getOrDefault(criteria, compareBySurname));
    }

    public List<User> getUsersSortedBy(Criteria criteria) {
        List<User> users = getUsers();
        sortBy(users, criteria);
        return users;
    }

    public List<Admin> getAdminsSortedBy(Criteria criteria) {
        List<Admin> admins = getAdmins();
        sortBy(admins, criteria);
        return admins;
    }

    public <T> List<Person> filterBy(Criteria criteria, T argument) {
        List<Person> list = filterBy(people, criteria, argument);
        return list;
    }

    /**
     * Filters the list of person passed as argument and returns a list containg only the objects that met the criteria.
     * @param <T> The type of the criteria (String, LocalDate, Credentials...)
     * @param list The list to be filtered
     * @param criteria A String representing the filter criteria
     * @param argument The object that represents the criteria to be met
     * @return A list containing the objects that met the criteria
     * @throws ClassCastException when argument and criteria don't match
     */
    public static <T, E extends Person> List<E> filterBy(List<E> list, Criteria criteria, T argument) throws ClassCastException {
        List<E> newList = new ArrayList<>();
        try {
            PersonFilter<T> filter = (PersonFilter<T>) FILTER_CRITERIAS.get(criteria);
            for(E element : list) {
                if (filter.test(element, argument))
                    newList.add((E) element);
            }
        } catch (Exception e) {
            throw new ClassCastException(FILTER_NOT_COHERENT_MSG);
        }

        return newList;
    }

    /**
     * Gives the sanction passed as parameter to the victim. Only an admin can call this method
     * @param applicant
     * @param victim
     * @param sanction
     * @return
     * @throws InvalidAdminException If the applicant's object is not a valid admin
     */
    public boolean sanction(Admin applicant, User victim, Sanction sanction) throws InvalidAdminException {
        if(!validApplicant(applicant))
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        
        boolean result = victim.addSanction(sanction);

        // If the sanction is a BookLostSanction and the user has multiple of it, give him a RepeatedLostBookSanction
        if(sanction instanceof BookLostSanction) {
            int bls = 0;
            for(Sanction s : victim.getSanctions())
                if(s instanceof BookLostSanction)
                    bls++;
            
            if(bls > RepeatedBookLostSanction.LIMIT)
                victim.addSanction(new RepeatedBookLostSanction(LocalDate.now()));
        }

        return result;
    }

    /**
     * Removes the sanction from the victim. Only an admin can call this method
     * @param applicant
     * @param victim
     * @param s
     * @return
     * @throws InvalidAdminException
     */
    public boolean pardon(Admin applicant, User victim, Sanction s) throws InvalidAdminException {
        if(!validApplicant(applicant))
            throw new InvalidAdminException(INVALID_ADMIN_MSG);
        
        return victim.removeSanction(s);
    }

    /**
     * Initialize person manager with a list of people.
     * If there are no admin accounts, it will add the default one.
     * Warning: the default admin account must be removed as soon as possible.
     * @param personList a list of people to be added to the manager's list
     * @throws ManagerAlreadyInitializedException when the manager was already been initialized
     */
    public void initializePeopleList(List<Person> personList) {
        if(this.people.size() > 0)
            throw new ManagerAlreadyInitializedException();
            
        for (Person p : personList) {
            String username = p.getCredentials().getUsername();

            // Add the account only if there are no accounts with the same username
            if(validUsername(username))
                people.add(p);
        }

        if(getAdmins().isEmpty())
            people.add(DEFAULT_ADMIN);
    }

    /**
     * Checks if the string passed is a valid username.
     * A valid username is one that is not blank (composed of only blank symbols), has not the same
     * username as the default admin and the username is univoke.
     * @param username The string to check against the archive.
     * @return {@code true} if the username can be used for a new account.
     */
    public boolean validUsername(String username) {
        if(username == null)
            return false;

        if(username.isBlank())
            return false;
        
        if(username.compareTo(DEFAULT_ADMIN.getCredentials().getUsername()) == 0)
            return false;
        
        return filterBy(Criteria.USERNAME, username).isEmpty();
    }

    /**
     * Checks if the object is a valid applicant.
     * A valid applicant is one that is contained inside the list of people.
     * @param applicant The object to be tested
     * @return {@code true} if it is a valid applicant.
     */
    public boolean validApplicant(Person applicant) {
        if(applicant == null)
            return false;
        
        return people.contains(applicant);
    }
}
