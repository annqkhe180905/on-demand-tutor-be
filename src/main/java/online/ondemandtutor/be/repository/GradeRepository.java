package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAll();
    Grade findGradeById(Long id);
}
