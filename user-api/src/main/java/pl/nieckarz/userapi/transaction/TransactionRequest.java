package pl.nieckarz.userapi.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransactionRequest {

    private String symbol;
    private Double boughtAt;
    private Double volume;
    private TransactionType type;

}
