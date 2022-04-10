package pl.nieckarz.cryptocurrencyapi.crypto;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Crypto {

    @Id
    private String symbol;

    private String name;

    private Double price;

    private Double percentChange24h;

    private Integer volume24h;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Crypto crypto = (Crypto) o;
        return symbol != null && Objects.equals(symbol, crypto.symbol);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
