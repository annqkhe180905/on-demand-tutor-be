package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EducationLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String educationLevel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_register_id", referencedColumnName = "id")
    SubjectRegister subjectRegister;
}
