package app;
import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import GUI.LoginFrame;
import database.FileManager;
import items.LoanRequest;
import items.managers.ArchiveManager;
import items.managers.LoanRequestsManager;
import users.managers.PersonManager;

/**
 * Main file
 */
public class Library {

    private static final String SAVEFILES_DIR_NAME = "./assets/savefiles/";
    private static final String ACCOUNTS_FILE_NAME = "accounts.json";
    private static final String BOOKS_FILE_NAME = "books.json";
    private static final String LOAN_REQUESTS_FILE_NAME = "loanRequests.json";

    public static void main(String args[]) throws SecurityException, IOException {
        
        FileManager.assertFileExistency(SAVEFILES_DIR_NAME, new String[]{ACCOUNTS_FILE_NAME, BOOKS_FILE_NAME, LOAN_REQUESTS_FILE_NAME});
        
        // Initilize PersonManager
        PersonManager pm = PersonManager.getInstance();
        pm.initializePeopleList(FileManager.readPeopleJSON(SAVEFILES_DIR_NAME + ACCOUNTS_FILE_NAME));

        //Initialize ArchiveManager
        ArchiveManager arcMgr = ArchiveManager.getInstance();
        arcMgr.initializeArchive(FileManager.readArchiveJSON(FileManager.DEFAULT_ARCHIVE_FILENAME));

        //Initialize LoanRequestsManager
        LoanRequestsManager lnReqMgr = LoanRequestsManager.getInstance();
        lnReqMgr.initializeRequests(FileManager.readRequestsJSON(FileManager.DEFAULT_LOAN_REQ_FILENAME));

        // Start the app
        new LoginFrame();
        System.out.println("ciao");
    }

    public static void exitProcedure() {
        try {
            FileManager.assertFileExistency(SAVEFILES_DIR_NAME, new String[]{ACCOUNTS_FILE_NAME, BOOKS_FILE_NAME, LOAN_REQUESTS_FILE_NAME});
        
            // Retrieve the PersonManager instance
            PersonManager pm = PersonManager.getInstance();
            // Save the list
            FileManager.writePeopleJSON(pm.getList(), SAVEFILES_DIR_NAME + ACCOUNTS_FILE_NAME);
        } catch (IOException | SecurityException e) {
            System.err.println("failed to save all files. closing with error -1");
            System.exit(-1);
        }

        System.out.println("session saved. closing app...");
        System.exit(0);
    }
}
