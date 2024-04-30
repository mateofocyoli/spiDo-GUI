import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import users.Credentials;
import users.Person;
import users.PersonManager;
import users.User;
import users.Admin;

public class SavoMain {

    public static void main(String args[]) throws ParseException {
        
        PersonManager pm = PersonManager.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");

        Date birth = df.parse("30/08/2002");
        Credentials c = new Credentials("username", "null");
        Person p = new User("nome", "cognome", null, null, null, c);
        pm.add(p);






        
        
        pm.add(new Admin("tommaso", "savoldi", df.parse("30/08/2002"), "bs", Person.Sex.MALE, new Credentials("cyber", "ciao")));
        pm.add(new User("mato", "foco", df.parse("01/07/2002"), "bs", Person.Sex.MALE, new Credentials("mateofocyoli", "1")));
        pm.add(new User("marco", "patata", df.parse("06/02/2001"), "esine", Person.Sex.FEMALE, new Credentials("caroz", "ziopera")));

        Scanner scanner = new Scanner(System.in);
        boolean going = true;
        while (going) {
            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();
            System.out.println("... controllando ...");

            Person p = pm.login(username, password);
            
            if (p == null)
                System.out.println("[𐄂] error");
            else {
                System.out.println(p);
            }
            
            System.out.println("--- riprovare? ---");
            String response = scanner.nextLine();
            if (response.compareToIgnoreCase("n") == 0)
                going = false;
        }

        going = true;
        while (going) {
            System.out.print("Scegli come sortare: ");
            String sortType = scanner.nextLine();
            
            if(sortType.compareTo("Admin") == 0) {
                for (Person p : pm.getAdmins())
                    System.out.println(p);
            } else if(sortType.compareTo("User") == 0) {
                for (Person p : pm.getUsers())
                    System.out.println(p);
            } else {
                pm.sortBy(sortType);
                for (Person p : pm.getList())
                    System.out.println(p);
            }

            System.out.println("--- riprovare? ---");
            String response = scanner.nextLine();
            if (response.compareToIgnoreCase("n") == 0)
                going = false;
        }

        scanner.close(); 
        
        
        /* ArrayList<Credentials> credentials = new ArrayList<>();
        credentials.add(new Credentials("cyber", "ciao"));
        credentials.add(new Credentials("focco", "ciao"));
        credentials.add(new Credentials("scorrazzina", "ciao"));
        credentials.add(new Credentials("dio", "ciao"));
        
        credentials.sort(null);

        for(Credentials c : credentials) {
            System.out.println(c.getUsername());
        } */

        /* Scanner scanner = new Scanner(System.in);
        boolean going = true;
        while (going) {
            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();
            System.out.println("... controllando ...");

            if (c.check(username, password))
                System.out.println("[✓] ok");
            else
                System.out.println("[𐄂] error");

            System.out.println("--- riprovare? ---");
            String response = scanner.nextLine();
            if (response.compareToIgnoreCase("n") == 0)
                going = false;
        }
        scanner.close(); */



    }
}
