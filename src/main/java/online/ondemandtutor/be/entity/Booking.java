package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bookingDetails;

    @OneToOne(mappedBy = "booking")
    private Complaint complaint;

    @OneToOne(mappedBy = "booking")
    private Review review;
}
