package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class WeekDay {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    String day;

//    @ManyToOne
//    @JoinColumn(name = "tutorSchedule_id")
//    @JsonIgnore
//    TutorSchedule tutorSchedule;

//    @OneToMany(mappedBy = "weekDay")
//    List<TeachingSlot> teachingSlots;

    @OneToMany(mappedBy = "weekDay")
    List<ScheduleRecord> scheduleRecords;

    @ManyToMany
    @JoinTable
            (
                    joinColumns = @JoinColumn(name = "week_day_id"),
                    inverseJoinColumns = @JoinColumn(name = "account_id")
            )
    @JsonIgnore
    List<Account> account;

    @ManyToMany(mappedBy = "weekDay")
    List<TeachingSlot> teachingSlots;


}
