import java.util.ArrayList;
import java.util.Scanner;

import users.Credentials;

public class SavoMain {

    public static void main(String args[]) {
        ArrayList<Credentials> credentials = new ArrayList<>();
        credentials.add(new Credentials("cyber", "ciao"));
        credentials.add(new Credentials("focco", "ciao"));
        credentials.add(new Credentials("scorrazzina", "ciao"));
        credentials.add(new Credentials("dio", "ciao"));
        
        credentials.sort(null);

        for(Credentials c : credentials) {
            System.out.println(c.getUsername());
        }

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
