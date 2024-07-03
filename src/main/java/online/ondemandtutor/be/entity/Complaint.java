package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;

    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;
//    @OneToOne
//    @JoinColumn(name = "booking_id", referencedColumnName = "id")
//    private Booking booking;

}
