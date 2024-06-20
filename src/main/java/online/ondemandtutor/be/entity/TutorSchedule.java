package online.ondemandtutor.be.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import online.ondemandtutor.be.model.SubjectRequest;

import java.util.List;

@Entity
@Data
public class TutorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonIgnore
    Subject subject;

    @OneToMany(mappedBy = "tutorSchedule")
    List<Booking> bookings;

    String weekDay;
    String teachingTime;
}
