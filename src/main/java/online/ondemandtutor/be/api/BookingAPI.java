package online.ondemandtutor.be.api;


import online.ondemandtutor.be.model.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BookingAPI {

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookingAPI {

    private final BookingService bookingService;

    @Autowired
    public BookingAPI(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody BookingRequest bookingRequest) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, bookingRequest);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
