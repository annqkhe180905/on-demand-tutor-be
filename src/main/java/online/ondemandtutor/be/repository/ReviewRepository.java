package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllReviewByTutorId(Long tutorId);

    List<Review> findAllReviewByStudentId(Long studentId);

    Review findReviewById(Long id);

    List<Review> findAll();

    Review findByStudentIdAndTutorId(long id, long tutorId);
}
