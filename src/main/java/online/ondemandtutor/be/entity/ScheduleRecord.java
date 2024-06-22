package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class ScheduleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weekDay_id")
    private WeekDay weekDay;

    @ManyToOne
    @JoinColumn(name = "teachingSlot_id")
    private TeachingSlot teachingSlot;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
