package online.ondemandtutor.be.model;
import lombok.Data;

@Data
public class AccountRequest {
    private long id;

    private String email;

    private String fullname;

    private String password;

    private String phone;
}
