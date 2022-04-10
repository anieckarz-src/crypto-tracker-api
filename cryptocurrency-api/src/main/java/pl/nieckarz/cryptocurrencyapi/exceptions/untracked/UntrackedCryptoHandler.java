package pl.nieckarz.cryptocurrencyapi.exceptions.untracked;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class UntrackedCryptoHandler {

    @ExceptionHandler(value = {UntrackedCryptoException.class})
    public ResponseEntity<Object> handleMarketUntrackedException(UntrackedCryptoException e) {

        UntrackedCrypto untrackedCrypto = new UntrackedCrypto(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(untrackedCrypto, HttpStatus.NOT_FOUND);
    }
}
