package pl.nieckarz.userapi.transaction;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.nieckarz.userapi.crypto.Crypto;
import pl.nieckarz.userapi.user.AppUser;
import pl.nieckarz.userapi.user.AppUserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {

    private final AppUserRepository appUserRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;


    public void addTransaction(TransactionRequest request, String email) {

        AppUser appUser = appUserRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email));

        Transaction transaction = new Transaction(appUser, request.getSymbol(), request.getBoughtAt(), request.getVolume(), request.getType());

        transactionRepository.save(transaction);
    }

    public List<TransactionResponse> getUserTransactions(String email) {

        List<Transaction> userTransactions = transactionRepository.findByAppUser_Email(email);

        List<TransactionResponse> response = new ArrayList<>();

        if (userTransactions.isEmpty()) {
            return response;
        }

        Set<String> symbols = new HashSet<>();

        userTransactions.forEach(transaction -> symbols.add(transaction.getSymbol()));

        String params = String.join(",", symbols);

        ResponseEntity<Crypto[]> responseEntity = restTemplate.getForEntity("http://CRYPTOCURRENCY-API/api/v1/crypto/" + params, Crypto[].class);

        List<Crypto> cryptoList = Arrays.asList(responseEntity.getBody());

        userTransactions.forEach(transaction -> {

            Crypto crypto = cryptoList.stream().filter(o -> transaction.getSymbol().equals(o.getSymbol())).findAny().orElse(null);

            double profit = crypto.getPrice() - transaction.getBoughtAt() * transaction.getVolume();

            if (transaction.getType() == TransactionType.SELL) {
                profit *= -1;
            }
            response.add(new TransactionResponse(
                    transaction.getId(),
                    transaction.getBoughtAt(),
                    transaction.getSymbol(),
                    transaction.getVolume(),
                    transaction.getType(),
                    profit
            ));

        });
        return response;
    }

    @Transactional
    public void deleteTransaction(Long id, String email) {
        transactionRepository.deleteByIdAndAppUser_Email(id,email);
    }
}
