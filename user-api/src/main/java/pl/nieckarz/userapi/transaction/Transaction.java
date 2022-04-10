package pl.nieckarz.userapi.transaction;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nieckarz.userapi.user.AppUser;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private AppUser appUser;

    private Double boughtAt;
    private String symbol;
    private Double volume;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Transaction(AppUser appUser,String symbol, Double boughtAt, Double volume, TransactionType type) {
        this.appUser = appUser;
        this.boughtAt = boughtAt;
        this.symbol = symbol;
        this.volume = volume;
        this.type = type;
    }
}
