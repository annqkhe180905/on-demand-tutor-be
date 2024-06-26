package online.ondemandtutor.be.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long tutorId;
//    private Long literacy;
//    private List<Long> desiredTutoringLocation;
//    private List<Long> classTaught;
//    private List<Long> subjectTaught;
//    private String brief;

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
