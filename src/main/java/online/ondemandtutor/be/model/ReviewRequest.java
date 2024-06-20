package online.ondemandtutor.be.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewRequest {
    private String content;
    private LocalDateTime createdAt;
    private int score;
    private long accountId;
}
