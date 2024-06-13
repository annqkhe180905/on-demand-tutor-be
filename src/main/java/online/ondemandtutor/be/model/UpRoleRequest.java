package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.enums.RoleEnum;

@Data
public class UpRoleRequest {
    private String email;
}
