package online.ondemandtutor.be.model;
import lombok.Data;

@Data
public class AccountRequest {
    private String email;

    private String fullname;

    private String password;

    private String phone;
}
