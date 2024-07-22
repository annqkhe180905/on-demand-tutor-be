package online.ondemandtutor.be.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Booking;

@Getter
@Setter
public class BookingDetailResponse {
    private Booking booking;
    private Account student;
    private Account tutor;
}
