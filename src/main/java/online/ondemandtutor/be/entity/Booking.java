package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tutorSchedule_id")
    @JsonIgnore
    private TutorSchedule tutorSchedule;

    @OneToOne
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;

    @OneToOne
    @JoinColumn(name = "complaint_id")
    @JsonIgnore
    private Complaint complaint;

    private String literacy;
    private String desiredTutoringLocation;
    private String tutoringClass;
    private String subjectTaught;
    private String brief;
    private String subject;
    private String description;
    private int money;
    private String location;

    // private String url;


}
