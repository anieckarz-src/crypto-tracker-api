package pl.nieckarz.cryptocurrencyapi.crypto;


import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.nieckarz.cryptocurrencyapi.Secrets;
import pl.nieckarz.cryptocurrencyapi.exceptions.RestTemplateResponseErrorHandler;
import pl.nieckarz.cryptocurrencyapi.exceptions.symbol.InvalidSymbolException;
import pl.nieckarz.cryptocurrencyapi.exceptions.untracked.UntrackedCryptoException;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.*;


@Service
public class CryptoService {

    private final RestTemplate restTemplate;
    private final CryptoRepository cryptoRepository;
    private final String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?" +
            "CMC_PRO_API_KEY=" + Secrets.apiKey +
            "&symbol=";

    public CryptoService(RestTemplateBuilder restTemplateBuilder, CryptoRepository cryptoRepository) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        this.cryptoRepository = cryptoRepository;
    }


    private void getCryptoData(String request) {

        ResponseEntity<String> response = restTemplate.getForEntity(url + request, String.class);

        JSONObject root = new JSONObject(response.getBody());
        List<Crypto> cryptoToSave = new ArrayList<>();

        if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
            throw new InvalidSymbolException(root.getJSONObject("status").getString("error_message"));
        }

        List<String> symbols = new ArrayList<>(Arrays.asList(request.split(",")));

        symbols.forEach(symbol ->{

            if(root.getJSONObject("data").getJSONObject(symbol).getJSONObject("quote").getJSONObject("USD").getBigDecimal("price").compareTo(BigDecimal.ZERO) == 0){
                throw new UntrackedCryptoException("Crypto","symbol",symbol);
            }

            Crypto newCrypto = new Crypto(
                    root.getJSONObject("data").getJSONObject(symbol).getString("symbol"),
                    root.getJSONObject("data").getJSONObject(symbol).getString("name"),
                    root.getJSONObject("data").getJSONObject(symbol).getJSONObject("quote").getJSONObject("USD").getDouble("price"),
                    root.getJSONObject("data").getJSONObject(symbol).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"),
                    root.getJSONObject("data").getJSONObject(symbol).getJSONObject("quote").getJSONObject("USD").getInt("volume_24h")
            );

            cryptoToSave.add(newCrypto);


        });
        cryptoRepository.saveAll(cryptoToSave);
    }

    public List<Crypto> findCrypto(String request) {

        List<String> symbols = new ArrayList<>(Arrays.asList(request.split(",")));
        symbols.replaceAll(String::toUpperCase);
        List<String> symbolsToFind = new ArrayList<>(symbols);

        symbolsToFind.removeAll(cryptoRepository.findAllSymbols());
        if(!symbolsToFind.isEmpty()){
            getCryptoData(String.join(",",symbolsToFind));
        }

        return cryptoRepository.findAllById(symbols);
    }


    public void deleteCrypto(String request) {

        List<String> symbols = new ArrayList<>(Arrays.asList(request.split(",")));

        if(!symbols.isEmpty()){
            cryptoRepository.deleteAllById(symbols);
        }
    }

    @Scheduled(fixedDelayString = "${someJob.delay}")
    @Transient
    private void updateData(){

        String request = String.join(",", cryptoRepository.findAllSymbols());

        if(request.length()>0){
            getCryptoData(request);
        }

    }
}
