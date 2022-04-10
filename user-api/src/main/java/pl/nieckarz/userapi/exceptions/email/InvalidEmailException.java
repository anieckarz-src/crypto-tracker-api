package pl.nieckarz.userapi.exceptions.email;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super("email: " + message + " is not valid");
    }
}
