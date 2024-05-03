package exceptions;

public class ManagerAlreadyInitializedException extends IllegalStateException{

    public static final String DEFAULT_MESSAGE = "Instance already initialized";

    public ManagerAlreadyInitializedException(String message){
        super(message);
    }

    public ManagerAlreadyInitializedException(){
        super(DEFAULT_MESSAGE);
    }
}
