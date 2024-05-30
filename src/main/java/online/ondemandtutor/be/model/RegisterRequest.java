package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class RegisterRequest {
    long idCategory;
    String phone;
    String password;
    boolean isDeleted;;
}
