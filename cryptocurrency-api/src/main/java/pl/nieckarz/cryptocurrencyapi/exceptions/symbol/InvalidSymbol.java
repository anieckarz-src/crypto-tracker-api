package pl.nieckarz.cryptocurrencyapi.exceptions.symbol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class InvalidSymbol {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
