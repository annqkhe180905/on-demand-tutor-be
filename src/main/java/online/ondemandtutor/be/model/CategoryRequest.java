package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class CategoryRequest {
    private long id;

    private int subjectLevel;
    private boolean isDeleted;
}
