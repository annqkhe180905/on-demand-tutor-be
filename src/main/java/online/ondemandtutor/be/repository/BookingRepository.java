package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.enums.BookingEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public Booking findBookingById(Long id);
    public Booking findBookingByStudentIdAndTutorIdAndStatus(Long studentId, Long tutorId, BookingEnum role);

    List<Booking> findBookingByStudentId(Long id);
    Booking findByStudentId(Long id);
    List<Booking> findBookingByTutorId(Long tutorId);
}
