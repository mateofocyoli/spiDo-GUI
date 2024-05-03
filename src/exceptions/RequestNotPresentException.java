package exceptions;
import java.util.NoSuchElementException;

public class RequestNotPresentException extends NoSuchElementException {
    
    public static final String DEFAULT_MESSAGE = "Request not present";

    public RequestNotPresentException(String message){
        super(message);
    }

    public RequestNotPresentException(){
        super(DEFAULT_MESSAGE);
    }


}
