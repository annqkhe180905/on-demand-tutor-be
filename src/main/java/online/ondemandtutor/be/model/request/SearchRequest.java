package online.ondemandtutor.be.model.request;

import lombok.Data;

@Data
public class SearchRequest {
    private String name;
    private String subject;
    private String location;
    private String bio;
}
