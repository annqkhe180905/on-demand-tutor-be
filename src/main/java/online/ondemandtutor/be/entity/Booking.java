package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import online.ondemandtutor.be.enums.StatusEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @OneToOne(mappedBy = "booking")
//    private Complaint complaint;
//
//    @OneToOne(mappedBy = "booking")
//    private Review review;

    /////////////////////

    @ManyToMany
    @JoinTable(
            name = "booking_tutor",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    @JsonIgnore
    private List<Account> tutors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "booking_student",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    private List<Account> students = new ArrayList<>();

    /////////////////////

//    private String literacy;
//    private String desiredTutoringLocation;
//    private String tutoringClass;
//    private String subjectTaught;
//    private String brief;
//    private String subject;
//    private String description;
//    private int money;
//    private String location;
    // private String url;

    // thêm status để lưu trạng thái của booking
    private StatusEnum status;
}
