package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Complaint;
import online.ondemandtutor.be.enums.ComplaintEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{
    List<Complaint> findByFromStudentId(long id);

    List<Complaint> findByTutorEmailAndStatus(String tutorEmail, ComplaintEnum status);

    List<Complaint> findByStatus(ComplaintEnum status);

    Complaint findComplaintById(Long id);

    long countByFromStudentIdAndTutorEmailAndStatus(long fromStudentId, String tutorEmail, ComplaintEnum status);

    List<Complaint> findAll();
}
