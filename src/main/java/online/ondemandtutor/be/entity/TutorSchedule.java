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

    @OneToMany(mappedBy = "tutorSchedule")
    List<Subject> subjects;

    @OneToMany(mappedBy = "tutorSchedule")
    List<Booking> bookings;

    @ElementCollection
    @CollectionTable(name = "tutor_schedule_weekdays", joinColumns = @JoinColumn(name = "tutor_schedule_id"))
    @Column(name = "week_day")
    private List<String> weekDays;

    @ElementCollection
    @CollectionTable(name = "tutor_schedule_teaching_times", joinColumns = @JoinColumn(name = "tutor_schedule_id"))
    @Column(name = "teaching_time")
    private List<String> teachingTimes;
}
