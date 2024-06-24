package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TeachingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String time;

//    @ManyToOne
//    @JoinColumn(name = "weekday_id")
//    @JsonIgnore
//    WeekDay weekDay;

    @OneToMany(mappedBy = "teachingSlot")
    List<ScheduleRecord> scheduleRecords;

    @ManyToMany
    @JoinTable
            (
                    joinColumns = @JoinColumn(name = "teaching_slot_id"),
                    inverseJoinColumns = @JoinColumn(name = "weekDays")
            )
    @JsonIgnore
    List<WeekDay> weekDay;

}
