package online.ondemandtutor.be.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.entity.Booking;

import java.util.List;


@Getter
@Setter
public class BookingRequest {
    private Long tutorId;

//    public Booking toBooking() {
//        Booking booking = new Booking();
//        booking.setLiteracy(this.literacy);
//        booking.setDesiredTutoringLocation(this.desiredTutoringLocation);
//        booking.setTutoringClass(this.classTaught);
//        booking.setSubjectTaught(this.subjectTaught);
//        booking.setBrief(this.brief);
//        // set status của booking = ok
//        booking.setStatus("OK");
//        return booking;
//    }


}
