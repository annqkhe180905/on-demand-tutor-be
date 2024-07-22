package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Booking;
import online.ondemandtutor.be.entity.Review;
import online.ondemandtutor.be.enums.BookingEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.request.ReviewRequest;
import online.ondemandtutor.be.repository.BookingRepository;
import online.ondemandtutor.be.repository.ReviewRepository;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private BookingRepository bookingRepository;

//    @Autowired
//    BookingRequest bookingRequest;

    //hoc xong roi moi duoc review
    public Review createReview(ReviewRequest reviewRequest) {
        // Create a review after the student has completed the booking
            Account student = authenticationService.getCurrentAccount();

            // Check if the student has already reviewed this tutor
            Booking existingBooking = bookingRepository.findBookingByStudentIdAndTutorIdAndStatus(
                    student.getId(), reviewRequest.getTutorId(), BookingEnum.PASSED
            );

            if (existingBooking == null) {
                throw new BadRequestException("You must complete a booking with this tutor before leaving a review!");
            }

            Review existingReview = reviewRepository.findByStudentIdAndTutorId(student.getId(), reviewRequest.getTutorId());
            if (existingReview != null) {
                throw new BadRequestException("You have already reviewed this tutor!");
            }

            Review review = new Review();
            review.setStudent(student);
            review.setContent(reviewRequest.getContent());
            review.setScore(reviewRequest.getScore());

            Account tutor = authenticationRepository.findAccountById(reviewRequest.getTutorId());
            if (tutor == null) {
                throw new BadRequestException("Tutor not found with id " + reviewRequest.getTutorId());
            }
            review.setTutor(tutor);

            // Save the review to the database
            return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

//    public Review getReviewById(Long id) {
//        Review review = reviewRepository.findReviewById(id);
//        if(review != null) return review;
//        else throw new BadRequestException("Review is not found!");
//    }
//
//    public List<Review> getReviewByTutorId(Long id) {
//        Account tutor = authenticationRepository.findAccountById(id);
//        if(tutor == null) {
//            throw new BadRequestException("Tutor is not found!");
//        }
//        return reviewRepository.findAllReviewByAccountId(tutor);
//    }

    public Review getReviewById(Long id) {
        return reviewRepository.findReviewById(id);
    }

    public List<Review> getReviewByTutorId(Long tutorId) {
        Account tutor = authenticationRepository.findAccountById(tutorId);
        if (tutor == null) {
            throw new BadRequestException("Tutor is not found!");
        }
        return reviewRepository.findAllReviewByTutorId(tutor.getId());
    }

    public Review deleteReview(Long id) {
        // lấy review từ repo
        Review review = reviewRepository.findById(id).orElseThrow(() -> new BadRequestException("Review not found!"));

        Account account = authenticationService.getCurrentAccount();

        Long reviewStudentId = review.getStudent().getId();

        // check if the current account is the owner of the review
        if (!reviewStudentId.equals(account.getId())) {
            throw new BadRequestException("You are not authorized to delete this review!");
        }

        // Remove review from the student's review list
        review.getStudent().getStudentReview().remove(review);

        // Remove review from the tutor's review list
        review.getTutor().getTutorGotReview().remove(review);

        // Delete the review
        reviewRepository.delete(review);

        return review;
    }

}
