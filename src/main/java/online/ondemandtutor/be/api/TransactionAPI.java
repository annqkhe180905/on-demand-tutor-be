package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.model.TransactionResponse;
import online.ondemandtutor.be.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class TransactionAPI {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactionsById")
    public ResponseEntity getTransactionById(){
        List<TransactionResponse> transaction = transactionService.getTransactionById();
        return  ResponseEntity.ok(transaction);
    }

    @GetMapping("/allTransactions")
    public ResponseEntity transactions(){
        List<TransactionResponse> transaction = transactionService.allTransactions();
        return  ResponseEntity.ok(transaction);
    }
}
