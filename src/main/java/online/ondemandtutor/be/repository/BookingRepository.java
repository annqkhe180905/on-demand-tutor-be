package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public Booking findBookingById(Long id);

}
