package online.ondemandtutor.be.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.entity.Booking;

import java.util.List;

@Getter
@Setter
public class BookingRequest {
    private Long tutorId;
}
