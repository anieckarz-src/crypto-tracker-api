package pl.nieckarz.userapi.transaction;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody TransactionRequest request, Principal principal){
        transactionService.addTransaction(request,principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> userTransaction(Principal principal) {
        return new ResponseEntity<>(transactionService.getUserTransactions(principal.getName()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable(name = "id") Long id,Principal principal){
        transactionService.deleteTransaction(id,principal.getName());
        return new ResponseEntity<>(HttpStatus.resolve(204));
    }

}
