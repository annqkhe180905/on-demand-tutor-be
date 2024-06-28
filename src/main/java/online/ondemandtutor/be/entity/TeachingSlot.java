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

    @JsonIgnore
    @OneToMany(mappedBy = "teachingSlot")
    List<ScheduleRecord> scheduleRecords;


}
