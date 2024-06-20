package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.TutorVideo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorVideoRepository {
    TutorVideo findById(Long id);
    List<TutorVideo> findAll();

}
