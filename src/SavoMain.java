import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import users.Credentials;
import users.Person;
import users.PersonManager;
import users.User;
import users.Admin;
import java.time.LocalDate;

public class SavoMain {

    public static void main(String args[]) throws ParseException {
        
        PersonManager pm = PersonManager.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        pm.add(new Admin("tommaso", "savoldi", df.parse("30/08/2002"), "bs", Person.Sex.MALE, new Credentials("cyber", "ciao")));
        pm.add(new User("mato", "foco", df.parse("01/07/2002"), "bs", Person.Sex.MALE, new Credentials("mateofocyoli", "1")));
        pm.add(new User("marco", "patata", df.parse("06/02/2001"), "esine", Person.Sex.FEMALE, new Credentials("caroz", "ziopera")));
        pm.add(new Admin("francesco", "jarjamanna", df.parse("3/10/2002"), "bs", Person.Sex.FEMALE, new Credentials("jey", "culo")));
        pm.add(new User("simon", "rugio", df.parse("10/07/2002"), "torbolon", Person.Sex.MALE, new Credentials("colione", "deineri")));
        pm.add(new User("lorenzo", "ferari", df.parse("10/11/1953"), "sotto un pontos", Person.Sex.MALE, new Credentials("lollo", "oppa")));

        List<Person> list = pm.sortBy("Sex");
        showList(list);
        
        System.out.println();
        List<User> list1 = pm.getUsers();
        showList(list1);

        System.out.println();
        List<Admin> list2 = pm.getAdmins();
        showList(list2);

        System.out.println();
        list = (List<Person>) pm.filterBy("Sex", Person.Sex.FEMALE);
        showList(list);

        System.out.println();
        list = (List<Person>) pm.filterBy("Birth", df.parse("30/08/2002"));
        showList(list);
    }

    public static <E extends Object> void showList(List<E> list) {
        System.out.println(list.getClass().getCanonicalName() + " con " + list.size() + " elementi:");
        for(E element : list) {
            System.out.println(element.toString());
        }
    }
}
