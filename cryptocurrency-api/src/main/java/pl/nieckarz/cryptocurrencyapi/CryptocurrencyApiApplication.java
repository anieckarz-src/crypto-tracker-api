package pl.nieckarz.cryptocurrencyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CryptocurrencyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyApiApplication.class, args);
    }
}
