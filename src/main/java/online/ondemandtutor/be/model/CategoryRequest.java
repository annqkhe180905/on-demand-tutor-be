package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class CategoryRequest {
    long id;
    String subject;
    Integer number;
    boolean isDeleted;
}
