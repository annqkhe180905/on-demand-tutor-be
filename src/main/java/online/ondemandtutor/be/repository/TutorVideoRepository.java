package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.TutorVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorVideoRepository extends JpaRepository<TutorVideo, Long> {
//    TutorVideo findTutorVideoById(Long id);
    List<TutorVideo> findAll();

}
