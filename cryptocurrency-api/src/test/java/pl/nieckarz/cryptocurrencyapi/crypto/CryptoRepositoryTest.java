package pl.nieckarz.cryptocurrencyapi.crypto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CryptoRepositoryTest {

    @Autowired
    private CryptoRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindAllSymbols() {
        //given
        Crypto crypto1 = new Crypto("TEST1","sampleName1",1D,2D,3);
        Crypto crypto2 = new Crypto("TEST2","sampleName2",1D,2D,3);
        List<String> symbols = new ArrayList<>();

        symbols.add(crypto1.getSymbol());
        symbols.add(crypto2.getSymbol());

        underTest.save(crypto1);
        underTest.save(crypto2);

        //when
        List<String> expected = underTest.findAllSymbols();

        //then
        assertThat(expected).isEqualTo(symbols);

    }
}