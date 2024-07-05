package online.ondemandtutor.be.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ComplaintRequest {
    private String content;
    private long reveiverId;
    private String nguoiPhanNan;
    private String nguoiBiPhanNan;
    private Date createdAt;
}
