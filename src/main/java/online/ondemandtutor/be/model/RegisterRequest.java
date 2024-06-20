package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String phone;
    private String fullname;
    private String email;
    private String password;
}
