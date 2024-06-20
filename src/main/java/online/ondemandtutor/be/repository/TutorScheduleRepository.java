package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.TutorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorScheduleRepository extends JpaRepository<TutorSchedule, Long> {
    List<TutorSchedule> findAll();
    TutorSchedule findTutorScheduleById(long id);
    List<TutorSchedule> findAllBySubjectId(long id);

}
