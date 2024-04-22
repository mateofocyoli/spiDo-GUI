import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import users.Credentials;
import users.Person;
import users.PersonManager;
import users.User;
import users.Admin;

public class SavoMain {

    public static void main(String args[]) {
        
        PersonManager pm = PersonManager.getInstance();
        pm.add(new Admin("tommaso", "savoldi", new Date(), "bs", Person.Sex.MALE, new Credentials("cyber", "ciao")));
        pm.add(new User("mato", "foco", new Date(), "bs", Person.Sex.MALE, new Credentials("mateofocyoli", "1")));
        pm.add(new User("marco", "patata", new Date(), "esine", Person.Sex.FEMALE, new Credentials("caroz", "ziopera")));

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
