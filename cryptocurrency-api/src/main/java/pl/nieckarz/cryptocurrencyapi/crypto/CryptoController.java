package pl.nieckarz.cryptocurrencyapi.crypto;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;



    @GetMapping("/{request}")
    public ResponseEntity<List<Crypto>> getCryptoData(@PathVariable(name = "request") String request) {
        return ResponseEntity.ok(cryptoService.findCrypto(request));
    }

    @DeleteMapping("/{request}")

    public ResponseEntity<Void> deleteCrypto(@PathVariable(name = "request") String request) {
        cryptoService.deleteCrypto(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
