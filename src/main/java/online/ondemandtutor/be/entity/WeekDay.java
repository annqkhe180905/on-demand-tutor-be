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

    @JsonIgnore
    @OneToMany(mappedBy = "weekDay")
    List<ScheduleRecord> scheduleRecords;

}
