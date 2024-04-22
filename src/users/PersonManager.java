package users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

public class PersonManager {

    private static Comparator<Person> compareByName = (Person p1, Person p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    private static Comparator<Person> compareBySurname = (Person p1, Person p2) -> p1.getSurname().compareToIgnoreCase(p2.getSurname());
    private static Comparator<Person> compareByBirth = (Person p1, Person p2) -> p1.getBirth().compareTo(p2.getBirth());
    private static Comparator<Person> compareByCityOfBirth = (Person p1, Person p2) -> p1.getCityOfBirth().compareToIgnoreCase(p2.getCityOfBirth());
    private static Comparator<Person> compareBySex = (Person p1, Person p2) -> p1.getSex().compareTo(p2.getSex());
    private static Comparator<Person> compareByCredentials = (Person p1, Person p2) -> p1.getCredentials().compareTo(p2.getCredentials());
    private static Comparator<Person> compareByType = (Person p1, Person p2) -> p1.getClass().getCanonicalName().compareTo(p2.getClass().getCanonicalName());

    private static PersonManager instance;
    private static final Map<String, Comparator<Person>> ORDER_CRITERIAS = Map.ofEntries(
        entry("Name", compareByName),
        entry("Surname", compareBySurname),
        entry("Birth", compareByBirth),
        entry("CityOfBirth", compareByCityOfBirth),
        entry("Sex", compareBySex),
        entry("Credentials", compareByCredentials),
        entry("Type", compareByType)
    );

    private ArrayList<Person> people;

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

        Person p = findBy("username", username);

        if(p == null)
            return null;

        if(p.getCredentials().check(username, password))
            return p;
        
        return null;
    }

    public Person findBy(String method, String argument) {
        // Find by username
        for(Person p : people) {
            if(p.getCredentials().compareTo(new Credentials(argument, "useless")) == 0)
                return p;
        }
        return null;
    }

    public boolean add(Person p) {
        return people.add(p);
    }

    public boolean remove(Person p) {
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
}
