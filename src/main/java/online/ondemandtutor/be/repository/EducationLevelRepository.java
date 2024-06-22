package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {
    EducationLevel findEducationLevelById(long id);

    List<EducationLevel> findAll();

}
