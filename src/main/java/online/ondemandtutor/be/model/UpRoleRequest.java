package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;

@Data
public class UpRoleRequest {
    private String certificateUrl;
}
