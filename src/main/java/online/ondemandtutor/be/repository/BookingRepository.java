package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {

}
