package pl.nieckarz.cryptocurrencyapi.crypto;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto,String> {


    @Query(value = "SELECT symbol FROM Crypto", nativeQuery = true)
    List<String> findAllSymbols();
}
