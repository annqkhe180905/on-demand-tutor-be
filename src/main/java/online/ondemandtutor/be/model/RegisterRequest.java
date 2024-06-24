package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class RegisterRequest {
    String email;
    String password;
    String phone;
    String fullname;

    //test
    public RegisterRequest(String s, String s1, String number, String mail) {
    }

}
