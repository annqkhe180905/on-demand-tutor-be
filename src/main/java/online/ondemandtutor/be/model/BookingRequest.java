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
        booking.setTutoringClass(this.classTaught);  // Đảm bảo các tên thuộc tính khớp với Booking
        booking.setSubjectTaught(this.subjectTaught);
        booking.setBrief(this.brief);
        return booking;
    }
}
