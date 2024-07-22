package online.ondemandtutor.be.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintRequest {
    String content;
    String tutorEmail;
}
