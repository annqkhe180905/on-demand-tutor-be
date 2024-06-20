package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class TutorCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String url;

    //xoa ngay`
//    private Date startAt;
//    private Date endAt;

    private Date uploadedDate;

    // many cer => one account
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

}
