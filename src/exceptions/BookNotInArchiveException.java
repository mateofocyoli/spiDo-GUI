package exceptions;

import java.util.NoSuchElementException;

public class BookNotInArchiveException extends NoSuchElementException {
    
    public static final String DEFAULT_MESSAGE = "Impossible! Perhaps the archives are incomplete";

    public BookNotInArchiveException(String message){
        super(message);
    }

    public BookNotInArchiveException(){
        super(DEFAULT_MESSAGE);
    }


}
