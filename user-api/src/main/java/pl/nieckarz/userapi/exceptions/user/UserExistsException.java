package pl.nieckarz.userapi.exceptions.user;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String email) {
        super("User with email: " + email + " already exists");
    }
}
