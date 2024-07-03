package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class SearchTutorRequest {
    private String name;
    private String subject;
    private String location;
    private String bio;
}
