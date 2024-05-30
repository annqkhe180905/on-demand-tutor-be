package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
