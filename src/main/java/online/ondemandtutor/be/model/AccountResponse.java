package online.ondemandtutor.be.model;

import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.entity.Account;

@Getter
@Setter
public class AccountResponse extends Account {

    private String token;
}
