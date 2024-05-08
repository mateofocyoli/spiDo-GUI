package database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class FileManager {



    public static final String DEFAULT_LOAN_REQ_FILENAME = "assets/savefiles/loanRequests.json";
    public static final String DEFAULT_ARCHIVE_FILENAME = "assets/savefiles/archive.json";


    public static ArrayList<LoanRequest> readRequestsJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
      Type requestType = new TypeToken<List<LoanRequest>>() {
      }.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(
                  LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                  User.class, new UserTypeAdapter()).registerTypeAdapter(
                  Loanable.class, new BookTypeAdapter()).create();

      FileReader reader = new FileReader(filename);
      ArrayList<LoanRequest> requests = gson.fromJson(reader, requestType);
      return requests;

      
    }

    public static ArrayList<Book> readArchiveJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
      Type bookType = new TypeToken<List<Book>>() {
      }.getType();
      Gson gson = new GsonBuilder().registerTypeAdapter(
                    LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                    Year.class, new YearTypeAdapter()).registerTypeAdapter(
                    User.class, new UserTypeAdapter()).create();

      FileReader reader = new FileReader(filename);
      ArrayList<Book> archive = gson.fromJson(reader, bookType);
      return archive;

      
  }

    public static void writeRequestsJSON(List<LoanRequest> requests, String filename) throws JsonIOException, IOException {
      Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(
                      LocalDate.class, new LocalDateTypeAdapter()).registerTypeAdapter(
                      User.class, new UserTypeAdapter()).registerTypeAdapter(
                      Book.class, new BookTypeAdapter()).create();
                      
      FileWriter writer = new FileWriter(filename);
      gson.toJson(requests, writer);
      writer.close();
      
  }


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
    Gson parser = new GsonBuilder()
                      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Person.class, "type")
                                                .registerSubtype(User.class, "user")
                                                .registerSubtype(Admin.class, "admin"))
                      .excludeFieldsWithoutExposeAnnotation()
                      .create();
     

    PrintWriter writer = new PrintWriter(filename);
    for(Person p : people)
        writer.println(parser.toJson(p, Person.class));
    writer.close();
    
  }

  public static List<Person> readPeopleJSON(String filename) throws JsonIOException, JsonSyntaxException, IOException {
    Gson parser = new GsonBuilder()
                      .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                      .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                                                .of(Person.class, "type")
                                                .registerSubtype(User.class, "user")
                                                .registerSubtype(Admin.class, "admin"))
                      .excludeFieldsWithoutExposeAnnotation()
                      .create();

    try (Scanner scanner = new Scanner(new File(filename))) {
      ArrayList<Person> people = new ArrayList<>();
      while (scanner.hasNext()) {
        Person p = parser.fromJson(scanner.nextLine(), Person.class);
        people.add(p);
      }
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
  public static void assertFileExistency(String dirPath, String[] files) throws IOException, SecurityException {
    File dir = new File(dirPath);
    dir.mkdir();            // Make the directory if it doesn't exist
    for(String fileName : files) {
        File file = new File(dir, fileName);
        file.createNewFile();   // Create a file if it doesn't exist
    } 
  }
}
