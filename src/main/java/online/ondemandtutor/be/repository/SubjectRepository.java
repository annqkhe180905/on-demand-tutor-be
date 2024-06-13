package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAll();
    List<Subject> findByName(String name);
    Subject findById(long id);

}
