package online.ondemandtutor.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import online.ondemandtutor.be.entity.Transaction;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.repository.TransactionRepository;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.model.TransactionRequest;

@Service
public class TransactionService {
    @Autowired
    static TransactionRepository transactionRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    static AuthenticationService authenticationService;

    public static Transaction createTransaction(TransactionRequest transactionRequest) {
        Account student = authenticationService.getCurrentAccount();
        Account tutor = AuthenticationRepository.findAccountById(Long.valueOf(transactionRequest.getTutorId()));

        // check student
        if (student == null) {
            throw new RuntimeException("Student not found with current account");
        }

        // check tutor
        if (tutor == null) {
            throw new RuntimeException("Tutor not found with id " + transactionRequest.getTutorId());
        }

        // Tạo đối tượng Transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionRequest.getTransactionId());
        transaction.setStudentId(String.valueOf(student.getId()));
        transaction.setTutorId(String.valueOf(tutor.getId()));
        transaction.setBookingId(transactionRequest.getBookingId());
        transaction.setAmount(transactionRequest.getAmount());

        // save Transaction vào db
        return transactionRepository.save(transaction);
    }
}
