package pl.nieckarz.userapi.exceptions.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
public class UserExistsHandler {

    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(UserExistsException e) {

        UserExists userExists = new UserExists(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(userExists, HttpStatus.CONFLICT);
    }
}
