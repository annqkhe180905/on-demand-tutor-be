package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class SubjectRequest {
    private long id;

    private String name;
    private int grade;
    private boolean isDeleted;
}
