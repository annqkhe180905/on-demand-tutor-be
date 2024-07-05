package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private LocalDateTime createdAt;
    private float score;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Account tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Account student;
}
