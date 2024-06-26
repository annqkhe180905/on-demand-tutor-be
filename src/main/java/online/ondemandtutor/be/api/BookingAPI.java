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

//    @GetMapping
//    public ResponseEntity<List<Booking>> getAllBookings() {
//        List<Booking> bookings = bookingService.getAllBookings();
//        return ResponseEntity.ok(bookings);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
//        Optional<Booking> booking = bookingService.getBookingById(id);
//        return booking.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody BookingRequest bookingRequest) {
//        try {
//            Booking updatedBooking = bookingService.updateBooking(id, bookingRequest);
//            return ResponseEntity.ok(updatedBooking);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
//        try {
//            bookingService.deleteBooking(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
