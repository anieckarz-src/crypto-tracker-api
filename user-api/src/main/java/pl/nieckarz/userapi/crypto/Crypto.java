package pl.nieckarz.userapi.crypto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Crypto {

    private String symbol;
    private String name;
    private Double price;
    private Double percentChange24h;
    private Integer volume24h;
}
