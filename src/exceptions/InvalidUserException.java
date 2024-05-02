package exceptions;

public class InvalidUserException extends IllegalAccessException{
    

    public static final String DEFAULT_MESSAGE = "Permission Denied! Only a registered user can do this";

    public InvalidUserException(String message){
        super(message);
    }

    public InvalidUserException(){
        super(DEFAULT_MESSAGE);
    }

}
