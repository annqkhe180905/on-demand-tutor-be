package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = bookingRequest.toBooking();


        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(String.valueOf(id));
    }

    public Booking updateBooking(Long id, BookingRequest bookingRequest) {
        Optional<Booking> optionalBooking = bookingRepository.findById(String.valueOf(id));
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setLiteracy(bookingRequest.getLiteracy());
            booking.setDesiredTutoringLocation(bookingRequest.getDesiredTutoringLocation());
            booking.setTutoringClass(bookingRequest.getClassTaught());
            booking.setSubjectTaught(bookingRequest.getSubjectTaught());
            booking.setBrief(bookingRequest.getBrief());
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found with id " + id);
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(String.valueOf(id));
    }
}
