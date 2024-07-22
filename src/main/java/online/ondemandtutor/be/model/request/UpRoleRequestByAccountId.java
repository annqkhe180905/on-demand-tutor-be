package online.ondemandtutor.be.model.request;

import lombok.Data;
import online.ondemandtutor.be.service.AccountService;

@Data
public class UpRoleRequestByAccountId {
    private Long accountId;
}
