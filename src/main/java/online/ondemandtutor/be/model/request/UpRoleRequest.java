package online.ondemandtutor.be.model.request;

import lombok.Data;
import online.ondemandtutor.be.enums.StatusEnum;

@Data
public class UpRoleRequest {
    private String certificateUrl;
}
