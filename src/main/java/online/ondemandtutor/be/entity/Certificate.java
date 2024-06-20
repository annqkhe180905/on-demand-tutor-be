package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String url;

    private Date startAt;

    private Date endAt;

    // many cer => one account
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

}
