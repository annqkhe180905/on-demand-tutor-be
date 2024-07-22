package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import online.ondemandtutor.be.enums.BookingEnum;
import online.ondemandtutor.be.enums.StatusEnum;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnore
    private Account tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Account student;

    // thêm status để lưu trạng thái của booking
    @Enumerated(EnumType.STRING)
    BookingEnum status;
}
