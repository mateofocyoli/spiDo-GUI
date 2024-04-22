package users;

import java.util.ArrayList;

public class PersonManager {

    private static PersonManager instance;

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
}
