package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countReviewByStudent(long student);

    List<Review> findByTutorIdAndStudentIdAndCreatedAt(
            Long tutorId, Long studentId, LocalDateTime start
    );
    // sửa thành getAll(), không phải tìm theo điều kiện

    Optional<Review> findReviewById(Long id);
}
