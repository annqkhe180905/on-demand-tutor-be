package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.ondemandtutor.be.service.DateService;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutorCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private LocalDateTime uploadedDate;

    //xoa ngay`
//    private Date startAt;
//    private Date endAt;

    @PrePersist
    protected void onCreate() {
        uploadedDate = DateService.getCurrentLocalDateTime();
    }

    // many cer => one account
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

}
