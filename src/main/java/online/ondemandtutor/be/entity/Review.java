package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.service.DateService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime uploadedDate;

    @PrePersist
    protected void onCreate() {
        uploadedDate = DateService.getCurrentLocalDateTime();
    }

    private float score;

    private String content;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnore
    private Account tutor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Account student;
}
