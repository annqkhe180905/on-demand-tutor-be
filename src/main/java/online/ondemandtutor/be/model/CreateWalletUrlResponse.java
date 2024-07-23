package online.ondemandtutor.be.model;

import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class CreateWalletUrlResponse {

    private String url;
    private long accId;

    public CreateWalletUrlResponse (String url, long id){
        this.url = url;
        this.accId = id;
    }
}
