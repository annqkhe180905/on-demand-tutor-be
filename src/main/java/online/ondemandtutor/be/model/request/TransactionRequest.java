package online.ondemandtutor.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    private Long id;

    private String transactionId;
    private String studentId;
    private String tutorId;
    private String bookingId;
    private double amount;
}
