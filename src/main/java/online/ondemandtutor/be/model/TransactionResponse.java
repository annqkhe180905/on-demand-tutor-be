package online.ondemandtutor.be.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.enums.TransactionEnum;

import java.util.UUID;
@Data
public class TransactionResponse {

    private Long transactionID;

    @Enumerated(EnumType.STRING)
    TransactionEnum transactionType;

    private double amount;

    private String description;

    private String transactionDate;

    Wallet from;

    Wallet to;

    Account userFrom;

    Account userTo;

}
