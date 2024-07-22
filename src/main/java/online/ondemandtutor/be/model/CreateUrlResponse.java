package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class CreateUrlResponse {
    private String url;
    private Long transactionId;
}
