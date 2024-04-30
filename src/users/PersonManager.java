package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import users.sanctions.Sanction;

import static java.util.Map.entry;

public class PersonManager {

    private static final String INVALID_ADMIN_MSG = "Permission Denied! Only an admin can can change the loan terms";

    private static Comparator<Person> compareByName = (Person p1, Person p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private static Comparator<Person> compareBySurname = (Person p1, Person p2) -> p1.getSurname().compareToIgnoreCase(p2.getSurname());
    private static Comparator<Person> compareByBirth = (Person p1, Person p2) -> p1.getBirth().compareTo(p2.getBirth());
    private static Comparator<Person> compareByCityOfBirth = (Person p1, Person p2) -> p1.getCityOfBirth().compareToIgnoreCase(p2.getCityOfBirth());
    private static Comparator<Person> compareBySex = (Person p1, Person p2) -> p1.getSex().compareTo(p2.getSex());
    private static Comparator<Person> compareByCredentials = (Person p1, Person p2) -> p1.getCredentials().compareTo(p2.getCredentials());
    private static Comparator<Person> compareByType = (Person p1, Person p2) -> p1.getClass().getCanonicalName().compareTo(p2.getClass().getCanonicalName());

    private static final Map<String, Comparator<Person>> ORDER_CRITERIAS = Map.ofEntries(
        entry("Name", compareByName),
        entry("Surname", compareBySurname),
        entry("Birth", compareByBirth),
        entry("CityOfBirth", compareByCityOfBirth),
        entry("Sex", compareBySex),
        entry("Credentials", compareByCredentials),
        entry("Type", compareByType)
    );

    private static PersonFilter<String> filterByName = (Person p, String name) -> p.getName().compareToIgnoreCase(name) == 0;
    private static PersonFilter<String> filterBySurname = (Person p, String surname) -> p.getName().compareToIgnoreCase(surname) == 0;
    private static PersonFilter<Date> filterByBirth = (Person p, Date birth) -> p.getBirth().compareTo(birth) == 0;
    private static PersonFilter<String> filterByCityOfBirth = (Person p1, String cityOfBirth) -> p1.getCityOfBirth().compareToIgnoreCase(cityOfBirth) == 0;
    private static PersonFilter<Person.Sex> filterBySex = (Person p1, Person.Sex sex) -> p1.getSex().compareTo(sex) == 0;
    private static PersonFilter<String> filterByUsername = (Person p1, String username) -> p1.getCredentials().compareTo(new Credentials(username, "useless")) == 0;
    private static PersonFilter<Class<? extends Person>> filterByType = (Person p1, Class<? extends Person> type) -> p1.getClass().getCanonicalName().compareTo(type.getCanonicalName()) == 0;

    private static final Map<String, PersonFilter<?>> FILTER_CRITERIAS = Map.ofEntries(
        entry("Name", filterByName),
        entry("Surname", filterBySurname),
        entry("Birth", filterByBirth),
        entry("CityOfBirth", filterByCityOfBirth),
        entry("Sex", filterBySex),
        entry("Username", filterByUsername),
        entry("Type", filterByType)
    );

    private static PersonManager instance;

    private List<Person> people;

    private PersonManager() {
        people = new ArrayList<>();
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

    public Person login(String username, String password) {

        List<Person> list = filter((PersonFilter<String>) FILTER_CRITERIAS.get("Username"), username);
        
        if(list.isEmpty())
            return null;

        Person p = list.get(0);

        if(p == null)
            return null;

        if(p.getCredentials().check(username, password))
            return p;
        
        return null;
    }

    public boolean add(Person p) {
        return people.add(p);
    }

    /**
     * It is necessary to pass the applicant instance to verify that he can modify the list.
     * @param applicant
     * @param p
     * @return
     * @throws IllegalAccessException
     */
    public boolean remove(Admin applicant, Person p) throws IllegalAccessException {
        if(applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        
        return people.remove(p);
    }

    public void sortBy(String criteria) {
        people.sort(ORDER_CRITERIAS.getOrDefault(criteria, compareBySurname));
    }

    public List<Person> getList() {
        ArrayList<Person> cloneList = new ArrayList<>();
        cloneList.addAll(people);
        return cloneList;
    }

    public List<User> getUsers() {
        ArrayList<User> usersList = new ArrayList<>();
        
        for(Person p : people) {
            if(p instanceof User)
                usersList.add((User) p);
        }

        return usersList;
    }

    public List<Admin> getAdmins() {
        ArrayList<Admin> adminsList = new ArrayList<>();
        
        for(Person p : people) {
            if(p instanceof Admin)
                adminsList.add((Admin) p);
        }

        return adminsList;
    }

    public static void sortBy(List<? extends Person> list, String criteria) {
        list.sort(ORDER_CRITERIAS.getOrDefault(criteria, compareBySurname));
    }

    public List<User> getUsersSortedBy(String criteria) {
        List<User> users = getUsers();
        sortBy(users, criteria);
        return users;
    }

    public List<Admin> getAdminsSortedBy(String criteria) {
        List<Admin> admins = getAdmins();
        sortBy(admins, criteria);
        return admins;
    }



    private <T> List<Person> filter(PersonFilter<T> predicate, T argument) {
        List<Person> list = new ArrayList<>();
        for(Person p : people) {
            if (predicate.test(p, argument))
                list.add(p);
        }
        return list;
    }


    /**
     * Gives the sanction passed as parameter to the victim. Only an admin can call this method
     * @param applicant
     * @param victim
     * @param s
     * @return
     * @throws IllegalAccessException
     */
    public boolean sanction(Admin applicant, User victim, Sanction s) throws IllegalAccessException {
        if(applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        
        return victim.addSanction(s);
    }

    /**
     * Removes the sanction from the victim. Only an admin can call this method
     * @param applicant
     * @param victim
     * @param s
     * @return
     * @throws IllegalAccessException
     */
    public boolean pardon(Admin applicant, User victim, Sanction s) throws IllegalAccessException {
        if(applicant == null)
            throw new IllegalAccessException(INVALID_ADMIN_MSG);
        
        return victim.removeSanction(s);
    }
}
