package exceptions;

import java.io.InvalidObjectException;

public class InvalidBookException extends InvalidObjectException {

    public static final String DEFAULT_MESSAGE = "This book is not valid";

    public InvalidBookException(String message){
        super(message);
    }

    public InvalidBookException(){
        super(DEFAULT_MESSAGE);
    }
}