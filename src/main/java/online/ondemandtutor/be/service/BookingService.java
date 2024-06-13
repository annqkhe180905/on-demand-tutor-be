package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

}
