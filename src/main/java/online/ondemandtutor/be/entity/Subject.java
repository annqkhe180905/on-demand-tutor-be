package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Subject {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    Category category;

    @OneToMany(mappedBy = "subject")
    List<TutorSchedule> tutorSchedules;

    private String name;
    private int grade;
    private boolean isDeleted = false;
}
