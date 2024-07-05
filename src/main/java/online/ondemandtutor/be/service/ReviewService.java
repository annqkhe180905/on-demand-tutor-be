package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Review;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.BookingRequest;
import online.ondemandtutor.be.model.ReviewRequest;
import online.ondemandtutor.be.repository.ReviewRepository;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationService authenticationService;

//    @Autowired
//    BookingRequest bookingRequest;

    public Review createReview(ReviewRequest reviewRequest) {
        // check thông tin tutor
        Account student = authenticationService.getCurrentAccount();
        Account tutor = authenticationRepository.findAccountById(reviewRequest.getTutorId());

        if (tutor == null) {
            throw new BadRequestException("Tutor not found!");
        }
        if (student == null) {
            throw new BadRequestException("Student not found!");
        }


        // Kiểm tra số lần review của student
        long reviewCount = reviewRepository.countReviewByStudent(Long.parseLong(String.valueOf(student)));
        if (reviewCount >= 1) {
            throw new BadRequestException("You have review one time!");
        }

        // create review
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setCreatedAt(LocalDateTime.now());
        review.setScore(reviewRequest.getScore());
        review.setTutor(tutor);
        review.setStudent(student);

        // save review vào db
        return reviewRepository.save(review);
    }

    // sửa hàm này. getAll(), không phải tìm theo điều kiện
    public List<Review> getAllReviews(ReviewRequest reviewRequest) {
        Long tutorId = reviewRequest.getTutorId();
        Long studentId = reviewRequest.getStudentId();
        LocalDateTime start = reviewRequest.getCreatedAt();


        return reviewRepository.findByTutorIdAndStudentIdAndCreatedAt(tutorId, studentId, start);
    }

    public Review getById(Long id) {
        return reviewRepository.findReviewById(id).orElseThrow(() -> new BadRequestException("Review not found!"));
    }

    public Review deleteReview(Long id) {
        // lấy review từ repo
        Optional<Review> optionalReview = reviewRepository.findReviewById(id);

        // check review có tồn tại không
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.deleteById(id);
            return review;
        } else {
            throw new BadRequestException("Review not found!");
        }
    }

}
