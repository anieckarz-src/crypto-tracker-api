package pl.nieckarz.userapi.exceptions.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class InvalidEmailHandler {

    @ExceptionHandler(value = {InvalidEmailException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(InvalidEmailException e) {

        InvalidEmail invalidEmail = new InvalidEmail(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(invalidEmail, HttpStatus.BAD_REQUEST);
    }
}
