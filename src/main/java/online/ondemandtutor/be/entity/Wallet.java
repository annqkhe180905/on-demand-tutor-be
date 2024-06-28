package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private double balance;

//    @JsonIgnore
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", unique = true)
//    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private Set<Transaction> transactionsFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    private Set<Transaction> transactionsTo;

}
