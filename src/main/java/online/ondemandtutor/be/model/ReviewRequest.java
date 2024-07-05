package online.ondemandtutor.be.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewRequest {
    private String content;
    private LocalDateTime createdAt;
    private float score;
    private long tutorId;
    private long studentId;

}
