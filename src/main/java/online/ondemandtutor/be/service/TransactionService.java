package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Transaction;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.model.TransactionResponse;
import online.ondemandtutor.be.repository.TransactionRepository;
import online.ondemandtutor.be.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletRepository walletRepository;


    public List<TransactionResponse> getTransactionById() {
        List<TransactionResponse> listTransactionResponse = new ArrayList<>();
        Account account = authenticationService.getCurrentAccount();
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        List<Transaction> transactions = transactionRepository.findTransactionsByFrom_IdOrTo_Id(wallet.getId());
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionID(transaction.getId());
            transactionResponse.setTransactionType(transaction.getTransactionType());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setDescription(transaction.getDescription());
            transactionResponse.setTransactionDate(transaction.getTransactionDate());
            transactionResponse.setFrom(transaction.getFrom());
            transactionResponse.setTo(transaction.getTo());
            if(transaction.getFrom() != null){
                transactionResponse.setUserFrom(transaction.getFrom().getAccount());
            }
            if(transaction.getTo() != null){
                transactionResponse.setUserTo(transaction.getTo().getAccount());
            }
            listTransactionResponse.add(transactionResponse);
        }
        listTransactionResponse = listTransactionResponse.stream()
                .sorted(Comparator.comparing(TransactionResponse::getTransactionDate).reversed())
                .collect(Collectors.toList());

        return listTransactionResponse;
    }

    public List<TransactionResponse> allTransactions() {
        List<TransactionResponse> listTransactionResponseDTO = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponseDTO = new TransactionResponse();
            transactionResponseDTO.setTransactionID(transaction.getId());
            transactionResponseDTO.setTransactionType(transaction.getTransactionType());
            transactionResponseDTO.setAmount(transaction.getAmount());
            transactionResponseDTO.setDescription(transaction.getDescription());
            transactionResponseDTO.setTransactionDate(transaction.getTransactionDate());
            transactionResponseDTO.setFrom(transaction.getFrom());
            transactionResponseDTO.setTo(transaction.getTo());
            if(transaction.getFrom() != null){
                transactionResponseDTO.setUserFrom(transaction.getFrom().getAccount());
            }
            if(transaction.getTo() != null){
                transactionResponseDTO.setUserTo(transaction.getTo().getAccount());
            }
            listTransactionResponseDTO.add(transactionResponseDTO);
        }
        listTransactionResponseDTO = listTransactionResponseDTO.stream()
                .sorted(Comparator.comparing(TransactionResponse::getTransactionDate).reversed())
                .collect(Collectors.toList());

        return listTransactionResponseDTO;
    }

}
