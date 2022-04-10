package pl.nieckarz.cryptocurrencyapi.exceptions.symbol;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class InvalidSymbolHandler {

    @ExceptionHandler(value = {InvalidSymbolException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(InvalidSymbolException e) {

        InvalidSymbol invalidSymbol = new InvalidSymbol(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(invalidSymbol, HttpStatus.BAD_REQUEST);
    }
}
