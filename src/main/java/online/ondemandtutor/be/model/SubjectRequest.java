package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.entity.Category;

import java.util.List;

@Data
public class SubjectRequest {
    private long id;

    private String name;
    private int grade;
    private boolean isDeleted;
    private long categoryId;
    private long scheduleId;
    private String Location;
    private String educationLevel;
}
