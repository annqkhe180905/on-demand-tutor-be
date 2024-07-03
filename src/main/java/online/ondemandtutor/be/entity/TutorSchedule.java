package online.ondemandtutor.be.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.model.SubjectRequest;

import java.util.List;

@Entity
@Getter
@Setter
public class TutorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    @OneToOne(mappedBy = "tutorSchedules")
    Account account;

//    @OneToMany(mappedBy = "tutorSchedule")
//    List<Subject> subjects;

    @OneToMany(mappedBy = "tutorSchedule")
    List<Booking> bookings;

    @OneToMany(mappedBy = "tutorSchedule")
    List<WeekDay> weekDay;


//    private String weekDays;
//
//    @ElementCollection
//    @CollectionTable(name = "tutor_schedule_teaching_times", joinColumns = @JoinColumn(name = "tutor_schedule_id"))
//    @Column(name = "teaching_time")
//    private List<String> teachingTimes;
}
