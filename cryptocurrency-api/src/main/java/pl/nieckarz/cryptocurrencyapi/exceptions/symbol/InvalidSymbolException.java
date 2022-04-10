package pl.nieckarz.cryptocurrencyapi.exceptions.symbol;


public class InvalidSymbolException extends RuntimeException {

    public InvalidSymbolException(String message) {
        super(message);
    }
}
