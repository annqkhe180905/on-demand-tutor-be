package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.entity.Booking;

@Data
public class BookingRequest {
    private String literacy;
    private String desiredTutoringLocation;
    private String classTaught;
    private String subjectTaught;
    private String brief;

    public Booking toBooking() {
        Booking booking = new Booking();
        booking.setLiteracy(this.literacy);
        booking.setDesiredTutoringLocation(this.desiredTutoringLocation);
        booking.setTutoringClass(this.classTaught);
        booking.setSubjectTaught(this.subjectTaught);
        booking.setBrief(this.brief);
        // set status của booking = ok
        booking.setStatus("OK");
        return booking;
    }
}
