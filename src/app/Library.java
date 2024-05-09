package app;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import GUI.LoginFrame;
import database.FileManager;
import items.managers.ArchiveManager;
import items.managers.LoanRequestsManager;
import users.managers.PersonManager;

/**
 * Main file
 */
public class Library {

    private static final String ASSETS_DIR_NAME = "assets";
    private static final String SAVEFILES_DIR_NAME = "savefiles";
    private static final String ACCOUNTS_FILE_NAME = "accounts.json";
    private static final String BOOKS_FILE_NAME = "archive.json";
    private static final String LOAN_REQUESTS_FILE_NAME = "loanRequests.json";
    private static final Path SAVEFILES_DIR_PATH = Path.of(ASSETS_DIR_NAME, SAVEFILES_DIR_NAME);
    private static final Path ACCOUNTS_FILE_PATH = Path.of(SAVEFILES_DIR_PATH.toString(), ACCOUNTS_FILE_NAME);
    private static final Path BOOKS_FILE_PATH = Path.of(SAVEFILES_DIR_PATH.toString(), BOOKS_FILE_NAME);
    private static final Path LOAN_REQUESTS_FILE_PATH = Path.of(SAVEFILES_DIR_PATH.toString(), LOAN_REQUESTS_FILE_NAME);

    private static final String ERROR_TITLE = "Error";
    private static final String SECURITY_EXCEPTION_ERROR_MESSAGE = "The program has not the right privilege to access the savefiles directory.";
    private static final int SECURITY_EXCEPTION_ERROR_CODE = -1;
    private static final String IO_EXCEPTION_ERROR_MESSAGE = "An IO error occurred while working on the save files.";
    private static final int IO_EXCEPTION_ERROR_CODE = -2;
    private static final String JSON_PARSING_EXCEPTION_ERROR_MESSAGE = "An ";
    private static final int JSON_PARSING_EXCEPTION_ERROR_CODE = -2;
    
    private static final String NORMAL_EXIT_TITLE = "Closing program...";
    private static final String NORMAL_EXIT_MESSAGE = "The files have been saved successfully.";
    private static final int NORMAL_EXIT_CODE = 0;

    public static void main(String args[]) {
        
        try {
            FileManager.assertFileExistency(SAVEFILES_DIR_PATH, new String[]{ACCOUNTS_FILE_NAME, BOOKS_FILE_NAME, LOAN_REQUESTS_FILE_NAME});

            // Initilize PersonManager
            PersonManager pm = PersonManager.getInstance();
            pm.initializePeopleList(FileManager.readPeopleJSON(ACCOUNTS_FILE_PATH.toString()));

            //Initialize ArchiveManager
            ArchiveManager arcMgr = ArchiveManager.getInstance();
            arcMgr.initializeArchive(FileManager.readArchiveJSON(BOOKS_FILE_PATH.toString()));

            //Initialize LoanRequestsManager
            LoanRequestsManager lnReqMgr = LoanRequestsManager.getInstance();
            lnReqMgr.initializeRequests(FileManager.readRequestsJSON(LOAN_REQUESTS_FILE_PATH.toString()));

            // Start the app
            new LoginFrame();
        } catch (SecurityException e) {
            // The program could not access the files because it has not the right privilege to write on that directory
            JOptionPane.showMessageDialog(null, 
                        SECURITY_EXCEPTION_ERROR_MESSAGE + "\n\n" + e.getMessage(),
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

            System.exit(SECURITY_EXCEPTION_ERROR_CODE);
        } catch (IOException | JsonIOException e) {
            // An IO error occurred during the access of the files 
            JOptionPane.showMessageDialog(null, 
                        IO_EXCEPTION_ERROR_MESSAGE + "\n\n" + e.getMessage(),
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

            System.exit(IO_EXCEPTION_ERROR_CODE);
        } catch (JsonSyntaxException e) {
            // The JSON parser failed to parse some JSON
            JOptionPane.showMessageDialog(null, 
                        JSON_PARSING_EXCEPTION_ERROR_MESSAGE + "\n\n" + e.getMessage(),
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

            System.exit(JSON_PARSING_EXCEPTION_ERROR_CODE);
        }
    }

    public static void exitProcedure() {
        try {
            FileManager.assertFileExistency(SAVEFILES_DIR_PATH, new String[]{ACCOUNTS_FILE_NAME, BOOKS_FILE_NAME, LOAN_REQUESTS_FILE_NAME});
        
            // Retrieve the PersonManager instance
            PersonManager pm = PersonManager.getInstance();
            // Save the list
            FileManager.writePeopleJSON(pm.getList(), ACCOUNTS_FILE_PATH.toString());
        } catch (SecurityException e) {
            // The program could not access the files because it has not the right privilege to write on that directory
            JOptionPane.showMessageDialog(null, 
                        SECURITY_EXCEPTION_ERROR_MESSAGE + "\n\n" + e.getMessage(),
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

            System.exit(SECURITY_EXCEPTION_ERROR_CODE);
        } catch (IOException e) {
            // An IO error occurred during the creation of the files
            JOptionPane.showMessageDialog(null, 
                        IO_EXCEPTION_ERROR_MESSAGE + "\n\n" + e.getMessage(),
                        ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                        
            System.exit(IO_EXCEPTION_ERROR_CODE);
        }

        JOptionPane.showMessageDialog(null, 
                        NORMAL_EXIT_MESSAGE,
                        NORMAL_EXIT_TITLE, JOptionPane.INFORMATION_MESSAGE);
        System.exit(NORMAL_EXIT_CODE);
    }
}
