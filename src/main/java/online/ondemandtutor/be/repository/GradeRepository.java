package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository {
    List<Grade> findAll();
    Grade findById(Long id);
}
