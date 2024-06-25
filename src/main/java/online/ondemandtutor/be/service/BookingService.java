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

    @Autowired
    BookingRepository bookingRepository;

    // tạo mới booking dựa trên booking request
    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = bookingRequest.toBooking();
        // status booking = ok
        booking.setStatus("OK");
        return bookingRepository.save(booking);
    }

    // lấy ds tất cả booking
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // tìm 1 booking theo id
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(Long.valueOf(String.valueOf(id)));

    }

    // update booking dựa trên booking request và id
    public Booking updateBooking(Long id, BookingRequest bookingRequest) {
        Optional<Booking> optionalBooking = bookingRepository.findById(Long.valueOf(String.valueOf(id)));
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setLiteracy(bookingRequest.getLiteracy());
            booking.setDesiredTutoringLocation(bookingRequest.getDesiredTutoringLocation());
            booking.setTutoringClass(bookingRequest.getClassTaught());
            booking.setSubjectTaught(bookingRequest.getSubjectTaught());
            booking.setBrief(bookingRequest.getBrief());
            // Đảm bảo trạng thái của booking vẫn là OK
            booking.setStatus("OK");
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found with id " + id);
        }
    }

    // xóa booking
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(Long.valueOf(String.valueOf(id)));

    }
}
