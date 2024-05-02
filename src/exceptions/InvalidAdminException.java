package exceptions;

public class InvalidAdminException extends IllegalAccessException {

    public static final String DEFAULT_MESSAGE = "Permission Denied! Only an admin can do this";

    public InvalidAdminException(String message){
        super(message);
    }

    public InvalidAdminException(){
        super(DEFAULT_MESSAGE);
    }
}
