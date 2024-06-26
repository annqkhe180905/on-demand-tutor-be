package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at", columnDefinition = "datetime(6)")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int score;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

//    @OneToOne
//    @JoinColumn(name = "booking_id", referencedColumnName = "id")
//    private Booking booking;

}
