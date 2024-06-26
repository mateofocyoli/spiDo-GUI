package database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import database.gsonAdapters.*;
import items.Book;
import items.LoanRequest;
import items.Loanable;
import users.Admin;
import users.Person;
import users.User;
import users.sanctions.BookDelayedSanction;
import users.sanctions.BookLostSanction;
import users.sanctions.BookRuinedSanction;
import users.sanctions.RepeatedBookLostSanction;
import users.sanctions.Sanction;

public class FileManager {

  /**Read a list of requests from a JSON file with references to applicant's id and requested object's id, an
   * ArchiveManager and a PersonManager will be needed to obtain Person and Book objects from username and id
   * @param filename of the JSON file
   * @return the list of loan requests
   * @throws JsonIOException
   * @throws JsonSyntaxException
   * @throws IOException
   */
  public static ArrayList<LoanRequest> readRequestsJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
    Type requestType = new TypeToken<List<LoanRequest>>() {
    }.getType();
    Gson gson = new GsonBuilder().registerTypeAdapter(
                LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                User.class, new UserTypeAdapter()).registerTypeAdapter(
                Loanable.class, new BookTypeAdapter()).create();

    FileReader reader = new FileReader(filename);
    ArrayList<LoanRequest> requests = gson.fromJson(reader, requestType);
    return requests == null ? new ArrayList<LoanRequest>() : requests;

    
  }

  /**Read a list of books from a JSON file with references to borrower's id, a PerosnManager
   * will be needed to obtain Person objects from username
   * @param filename of the JSON file
   * @return the list of loan requests
   * @throws JsonIOException
   * @throws JsonSyntaxException
   * @throws IOException
   */
  public static ArrayList<Book> readArchiveJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
    Type bookType = new TypeToken<List<Book>>() {
    }.getType();
    Gson gson = new GsonBuilder().registerTypeAdapter(
                  LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                  Year.class, new YearTypeAdapter()).registerTypeAdapter(
                  User.class, new UserTypeAdapter()).create();

    FileReader reader = new FileReader(filename);
    ArrayList<Book> archive = gson.fromJson(reader, bookType);
    return archive == null ? new ArrayList<Book>() : archive;

      
  }

  /**Writes a list of requests to a JSON file with references to requested object's id and its applicant's username 
   * @param requests to write to the JSON file
   * @param filename of the JSON file
   * @throws JsonIOException
   * @throws IOException
   */
  public static void writeRequestsJSON(List<LoanRequest> requests, String filename) throws JsonIOException, IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(
                    LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                    User.class, new UserTypeAdapter()).registerTypeAdapter(
                    Book.class, new BookTypeAdapter()).create();
                    
    FileWriter writer = new FileWriter(filename);
    gson.toJson(requests, writer);
    writer.close();
    
  }

  /**Writes a list of boks to a JSON file with references to its borrower's username 
   * @param requests to write to the JSON file
   * @param filename of the JSON file
   * @throws JsonIOException
   * @throws IOException
   */
  public static void writeArchiveJSON(List<Book> archive, String filename) throws JsonIOException, IOException{
    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().registerTypeAdapter(
                    LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                    Year.class, new YearTypeAdapter()).registerTypeAdapter(
                    User.class, new UserTypeAdapter()).create();
     

    FileWriter writer = new FileWriter(filename);
    gson.toJson(archive, writer);
    writer.close();
    
  }

  public static void writePeopleJSON(List<Person> people, String filename) throws JsonIOException, IOException{
    Gson parser = new GsonBuilder().setPrettyPrinting()
                      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Person.class, "type").recognizeSubtypes()
                                                .registerSubtype(User.class, "user")
                                                .registerSubtype(Admin.class, "admin"))
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Sanction.class, "type").recognizeSubtypes()
                                                .registerSubtype(BookDelayedSanction.class, "book_delayed")
                                                .registerSubtype(BookRuinedSanction.class, "book_ruined")
                                                .registerSubtype(BookLostSanction.class, "book_lost")
                                                .registerSubtype(RepeatedBookLostSanction.class, "rep_book_lost"))
                      .excludeFieldsWithoutExposeAnnotation()
                      .create();
     

    PrintWriter writer = new PrintWriter(filename);
    parser.toJson(people, writer);
    writer.close();
    
  }

  public static List<Person> readPeopleJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
    Gson parser = new GsonBuilder()
                      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Person.class, "type")
                                                .registerSubtype(User.class, "user")
                                                .registerSubtype(Admin.class, "admin"))
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Sanction.class, "type")
                                                .registerSubtype(BookDelayedSanction.class, "book_delayed")
                                                .registerSubtype(BookRuinedSanction.class, "book_ruined")
                                                .registerSubtype(BookLostSanction.class, "book_lost")
                                                .registerSubtype(RepeatedBookLostSanction.class, "rep_book_lost"))
                      .excludeFieldsWithoutExposeAnnotation()
                      .create();

    try (FileReader reader = new FileReader(filename)) {
      
      ArrayList<Person> people = new ArrayList<>();
      TypeToken<ArrayList<Person>> peopleType = new TypeToken<>() {};
      people = parser.fromJson(reader, peopleType);
      return people;
    } 
  }

  /**
   * Checks if the directory exists and if it does not it creates one. Checks also the existence of the other files and 
   * if they do not exist it creates them.
   * @param dirPath the direcory path to use
   * @param files an array of files to check/create
   * @throws IOException if an IO error occurs
   * @throws SecurityException if this app can not write on the path specified
   */
  public static void assertFileExistency(Path dirPath, String[] files) throws IOException, SecurityException {
    File dir = new File(dirPath.toString());
    dir.mkdir();            // Make the directory if it doesn't exist
    for(String fileName : files) {
        File file = new File(dir, fileName);
        file.createNewFile();   // Create a file if it doesn't exist
    } 
  }
}
