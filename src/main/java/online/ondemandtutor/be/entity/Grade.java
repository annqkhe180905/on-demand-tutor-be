package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private long id;

    private String grade;

    @ManyToOne
    @JoinColumn(name = "subjectRegister_id")
    @JsonIgnore
    SubjectRegister subjectRegister;

}
