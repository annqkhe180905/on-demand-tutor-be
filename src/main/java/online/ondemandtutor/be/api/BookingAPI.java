package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookings")
@SecurityRequirement(name = "api")
public class BookingAPI {

    @Autowired
    BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

}
