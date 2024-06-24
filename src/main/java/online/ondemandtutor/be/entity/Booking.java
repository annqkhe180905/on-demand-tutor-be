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

    private String bookingDetails;

    @OneToOne(mappedBy = "booking")
    private Complaint complaint;

    @OneToOne(mappedBy = "booking")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "tutorSchedule_id")
    @JsonIgnore
    private TutorSchedule tutorSchedule;

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

    // thêm status để lưu trạng thái của booking
    private String status;
}
