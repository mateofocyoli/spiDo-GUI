package exceptions;

import java.util.NoSuchElementException;

public class NotInArchiveException extends NoSuchElementException {
    
    public static final String DEFAULT_MESSAGE = "Impossible! Perhaps the archives are incomplete";

    public NotInArchiveException(String message){
        super(message);
    }

    public NotInArchiveException(){
        super(DEFAULT_MESSAGE);
    }


}
