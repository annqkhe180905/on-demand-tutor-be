package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Booking;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

public interface BookingRepository extends JpaAttributeConverter<Booking, Long> {
}
