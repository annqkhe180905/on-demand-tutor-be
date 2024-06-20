package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.service.AccountService;

@Data
public class UpRoleRequestByAccountId {
    private Long accountId;
}
