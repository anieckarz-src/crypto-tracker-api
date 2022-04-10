package pl.nieckarz.cryptocurrencyapi.exceptions.untracked;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class UntrackedCrypto {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
