package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.model.BookingDetailResponse;
import online.ondemandtutor.be.model.request.*;
import online.ondemandtutor.be.service.AuthenticationService;
import online.ondemandtutor.be.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
@SecurityRequirement(name = "api")
public class BookingAPI {

    @Autowired
    BookingService bookingService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/booking")
    public ResponseEntity createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/approved-booking")
//    @PreAuthorize("hasAuthority('TUTOR')")
    public void approvedBooking(@RequestBody BookingRequestById id){
        bookingService.approveBookingRequest(id);
    }

    @PostMapping("/rejected-booking")
//    @PreAuthorize("hasAuthority('TUTOR')")
    public void rejectedBooking(@RequestBody BookingRequestById id){
        bookingService.rejectBookingRequest(id);
    }

    @PostMapping("/passed-course")
//    @PreAuthorize("hasAuthority('TUTOR')")
    public void passedCourse(@RequestBody BookingRequestById id){
        bookingService.passedCourse(id);
    }

    @PostMapping("/failed-course")
//    @PreAuthorize("hasAuthority('TUTOR')")
    public void failedCourse(@RequestBody BookingRequestById id){
        bookingService.failedCourse(id);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAll(){
        return ResponseEntity.ok(bookingService.getAllBooking());
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity getBookingById(@PathVariable Long id){
        BookingDetailResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student-bookings/{id}")
    public ResponseEntity<List<BookingDetailResponse>> getAllBookingsByStudentId(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.studentListOfBookingsById(id));
    }

    @GetMapping("/tutor-bookings/{id}")
    public ResponseEntity<List<BookingDetailResponse>> getAllBookingsByTutorId(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.tutorListOfBookingsById(id));
    }
}
