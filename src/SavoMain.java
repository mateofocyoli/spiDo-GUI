import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import users.Credentials;
import users.Person;
import users.PersonManager;
import users.User;
import users.Person.Sex;
import users.Admin;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import database.FileManager;
import database.gsonAdapters.LocalDateTypeAdapter;
import database.gsonAdapters.RuntimeTypeAdapterFactory;

public class SavoMain {

    public static void main(String args[]) throws ParseException, JsonIOException, IOException {
        
        PersonManager pm = PersonManager.getInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        pm.add(new Admin("tommaso", "savoldi", LocalDate.parse("30/08/2002", dtf), "bs", Person.Sex.MALE, new Credentials("cyber", "ciao")));
        pm.add(new User("mato", "foco", LocalDate.parse("01/07/2002", dtf), "bs", Person.Sex.MALE, new Credentials("mateofocyoli", "1")));
        pm.add(new User("marco", "patata", LocalDate.parse("06/02/2001", dtf), "esine", Person.Sex.FEMALE, new Credentials("caroz", "ziopera")));
        pm.add(new Admin("francesco", "jarjamanna", LocalDate.parse("03/10/2002", dtf), "bs", Person.Sex.FEMALE, new Credentials("jey", "culo")));
        pm.add(new User("simon", "rugio", LocalDate.parse("10/07/2002", dtf), "torbolon", Person.Sex.MALE, new Credentials("colione", "deineri")));
        pm.add(new User("lorenzo", "ferari", LocalDate.parse("10/11/1953", dtf), "sotto un pontos", Person.Sex.MALE, new Credentials("lollo", "oppa")));

        Person p = new User("luca", "avanz", LocalDate.parse("03/05/2010", dtf), "leno", Sex.MALE, new Credentials("lucaavanz", "duke125"));
        System.out.println(p);

        Gson parser = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                        .of(Person.class, "type")
                        .registerSubtype(User.class, "user")
                        .registerSubtype(Admin.class, "admin"))
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        
        TypeToken<User> userTypeToken = new TypeToken<>() {};
        TypeToken<Person> personTypeToken = new TypeToken<>() {};

        String json = parser.toJson(p, Person.class);
        System.out.println(json);
        User u = parser.fromJson(json, userTypeToken);
        Person p1 = parser.fromJson(json, personTypeToken);
        System.out.println();
        System.out.println(u);
        System.out.println();
        System.out.println(p1);

        FileManager.writePeopleJSON(pm.getList(), "./assets/savefiles/accounts");

        showList(FileManager.readPeopleJSON("./assets/savefiles/accounts"));

        /* List<Person> list = pm.sortBy("Sex");
        showList(list);
        
        System.out.println();
        List<User> list1 = pm.getUsers();
        showList(list1);

        System.out.println();
        List<Admin> list2 = pm.getAdmins();
        showList(list2);

        System.out.println();
        list = (List<Person>) pm.filterBy("Birth", df.parse("30/08/2002"));
        showList(list);

        System.out.println();
        list = (List<Person>) pm.filterBy("Sex", Person.Sex.FEMALE);
        showList(list);

        System.out.println();
        list = PersonManager.filterBy(list, "Type", User.class);
        showList(list); */
    }

    public static <E extends Object> void showList(List<E> list) {
        System.out.println(list.getClass().getCanonicalName() + " con " + list.size() + " elementi:");
        for(E element : list) {
            System.out.println(element.toString());
        }
    }
}
