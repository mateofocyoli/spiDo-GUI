package database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import items.Book;
import items.LoanRequest;
import items.Loanable;
import users.User;

public class FileManager {



    public static final String DEFAULT_LOAN_REQ_FILENAME = "assets/loanRequests.json";
    public static final String DEFAULT_ARCHIVE_FILENAME = "assets/archive.json";


    public static ArrayList<LoanRequest> readRequestsJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
      Type requestType = new TypeToken<ArrayList<LoanRequest>>() {
      }.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(
                  LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                  User.class, new UserTypeAdapter()).registerTypeAdapter(
                  Loanable.class, new BookTypeAdapter()).create();

      try (FileReader reader = new FileReader(filename)) {

        ArrayList<LoanRequest> requests = gson.fromJson(reader, requestType);
        return requests;

      } 
      catch (JsonIOException | JsonSyntaxException | IOException e) {
        throw e;
      }
    }

    public static ArrayList<Book> readArchiveJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
      Type bookType = new TypeToken<ArrayList<Book>>() {
      }.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(
                    LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                    Year.class, new YearTypeAdapter()).registerTypeAdapter(
                    User.class, new UserTypeAdapter()).create();

      try (FileReader reader = new FileReader(filename)) {

        ArrayList<Book> archive = gson.fromJson(reader, bookType);
        return archive;


      } 
      catch (JsonIOException | JsonSyntaxException | IOException e) {
        throw e;
      }
  }

    public static void writeRequestsJSON(ArrayList<LoanRequest> requests, String filename) throws JsonIOException, IOException {
      Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(
                      LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                      User.class, new UserTypeAdapter()).registerTypeAdapter(
                      Book.class, new BookTypeAdapter()).create();
                      
      /*TODO: remove in production
      User[] users = { new User("Alessandro", "Muscio", LocalDate.now(), "Brescia", Person.Sex.MALE, new Credentials("kibo", "culo")), 
                      new User("Irene", "Treccani", LocalDate.now(), "Brescia", Person.Sex.FEMALE, new Credentials("merdina", "cacca")) 
                    };
                      
      Book[] books = { new Book("La mia vita di merda", "Phoenix", Book.Genre.COMEDY, Year.now(), 7) , 
                      new Book("Bibbia", "jesoo", Book.Genre.FANTASY, Year.now(), 7) 
                    };

      LoanRequest[] requs = {
          new LoanRequest(users[0], books[0], LocalDate.now()),
          new LoanRequest(users[1], books[1], LocalDate.now())
      };

      ArrayList<LoanRequest> requests = new ArrayList<LoanRequest>();

      //ArrayList<LoanRequest> requests = new ArrayList<>();

      for (int i = 0; i < requs.length; i++){
          requests.add(requs[i]);
      }*/


      try (FileWriter writer = new FileWriter(filename)) {
        gson.toJson(requests, writer);
      } 
      catch (JsonIOException | IOException e) {
        throw e;
      }
  }


  public static void writeArchiveJSON(ArrayList<Book> archive, String filename) throws JsonIOException, IOException{
    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().registerTypeAdapter(
                    LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                    Year.class, new YearTypeAdapter()).registerTypeAdapter(
                    User.class, new UserTypeAdapter()).create();
     

    try (FileWriter writer = new FileWriter(filename)) {
      gson.toJson(archive, writer);
    } catch (JsonIOException | IOException e) {
      throw e;
    }
  }


}
