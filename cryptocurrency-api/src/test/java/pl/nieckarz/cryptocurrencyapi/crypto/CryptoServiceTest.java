package pl.nieckarz.cryptocurrencyapi.crypto;

import org.awaitility.Awaitility;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CryptoServiceTest {

    @Autowired
    private CryptoService underTest;

    @Autowired
    private CryptoRepository cryptoRepository;

    @AfterEach
    void tearDown() {
        cryptoRepository.deleteAll();
    }

    @Test
    void givenSymbol_WhenFindCrypto_ThenObjectsInResponseAsTheSameAsInDatabase() {
        //given
        String symbol = "BTC";

        //when
        List<Crypto> result = underTest.findCrypto(symbol);
        Optional<Crypto> expected = cryptoRepository.findById(symbol);

        //then
        assertThat(expected.get()).isEqualTo(result.get(0));
    }


    @Test
    void givenSymbol_WhenDeleteCrypto_ThenCryptoIsNotPresentInDatabase() {
        //given
        String symbol = "TEST";
        cryptoRepository.save(new Crypto(symbol,"sampleName",1D,2D,3));

        //when
        cryptoRepository.deleteById(symbol);
        boolean isPresent = cryptoRepository.findById(symbol).isPresent();

        //then
        assertThat(isPresent).isFalse();

    }

    @Test
    void scheduledUpdateCryptoDataTest(){
        //given
        String symbol = "BTC";
        cryptoRepository.save(new Crypto(symbol,"Bitcoin",1D,1D,1));

        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .until(()-> cryptoRepository.findById(symbol).get().getPrice()>1);
    }
}