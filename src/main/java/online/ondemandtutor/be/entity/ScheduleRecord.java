package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "scheduleRecord_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Account> account =  new ArrayList<>();
}
