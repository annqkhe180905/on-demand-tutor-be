package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.enums.TransactionEnum;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    TransactionEnum transactionType;

    private double amount;

    private String description;

    private String transactionDate;

    String accountNumber;
    String accountName;
    String bankName;

    @ManyToOne
    @JoinColumn(name = "from_id")
    Wallet from;


    @ManyToOne
    @JoinColumn(name = "to_id")
    Wallet to;
}
