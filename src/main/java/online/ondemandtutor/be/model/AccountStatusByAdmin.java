package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class AccountStatusByAdmin {

        private long id;

        private boolean isDeleted = false;
}
