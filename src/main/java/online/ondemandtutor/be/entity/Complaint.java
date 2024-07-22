package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.enums.ComplaintEnum;
import online.ondemandtutor.be.enums.ComplaintEnum;
import online.ondemandtutor.be.service.DateService;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = DateService.getCurrentLocalDateTime();
    }

    private String content;

    @ManyToOne
    @JoinColumn(name = "from_student_id")
            @JsonIgnore
    Account fromStudent;

    private String tutorEmail;

    @Enumerated(EnumType.STRING)
    private ComplaintEnum status;
}
