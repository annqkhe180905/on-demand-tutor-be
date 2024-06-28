package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.TutorCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorCertificateRepository extends JpaRepository<TutorCertificate, Long>{
}
