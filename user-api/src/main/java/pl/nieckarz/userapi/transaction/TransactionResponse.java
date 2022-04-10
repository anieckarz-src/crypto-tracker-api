package pl.nieckarz.userapi.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.nieckarz.userapi.user.AppUser;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {


    private Long transaction_id;
    private Double boughtAt;
    private String symbol;
    private Double volume;
    private TransactionType type;
    private Double profit;
}
