package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.SubjectRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRegisterRepository extends JpaRepository<SubjectRegister, Long> {
}
