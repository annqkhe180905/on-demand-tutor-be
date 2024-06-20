package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAll();
    List<Subject> findByName(String name);
    Subject findById(long id);

}
