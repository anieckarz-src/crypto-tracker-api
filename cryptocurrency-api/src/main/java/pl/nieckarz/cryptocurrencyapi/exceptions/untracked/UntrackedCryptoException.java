package pl.nieckarz.cryptocurrencyapi.exceptions.untracked;

public class UntrackedCryptoException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public UntrackedCryptoException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("Data about %s with %s: '%s' in untracked", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}
