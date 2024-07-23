//package online.ondemandtutor.be.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import online.ondemandtutor.be.entity.Transaction;
//import online.ondemandtutor.be.model.TransactionRequest;
//import online.ondemandtutor.be.service.TransactionService;
//
//@RestController
//@RequestMapping("/api/transactions")
//public class TransactionAPI {
//
//    @Autowired
//    TransactionService transactionService;
//
//    @PostMapping("/transaction")
//    public Transaction createTransaction(@RequestBody TransactionRequest transactionRequest) {
//        Transaction transaction = TransactionService.createTransaction(transactionRequest);
//        return ResponseEntity.ok(transaction).getBody();
//
//    }
//}
