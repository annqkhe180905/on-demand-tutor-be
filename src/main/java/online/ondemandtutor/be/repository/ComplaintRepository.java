package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    long countByNguoiPhanNan(String nguoiPhanNan);
}
