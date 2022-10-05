package kz.com.aven.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super ();
    }

    public UserNotFoundException(String message) {
        super (message);
    }
}