package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Review;
import online.ondemandtutor.be.model.ReviewRequest;
import online.ondemandtutor.be.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setCreatedAt(reviewRequest.getCreatedAt());
        review.setScore(reviewRequest.getScore());

        Optional<Review> account = reviewRepository.findById(reviewRequest.getAccountId());
        if (account.isPresent()) {
            review.setAccount(account.get().getAccount());
        } else {
            throw new RuntimeException("Account not found with id: " + reviewRequest.getAccountId());
        }

        return reviewRepository.save(review);
    }

    public Review updateReview(long id, ReviewRequest reviewRequest) {
        Optional<Review> existingReviewOptional = reviewRepository.findById(id);
        if (existingReviewOptional.isPresent()) {
            Review existingReview = existingReviewOptional.get();
            existingReview.setContent(reviewRequest.getContent());
            existingReview.setCreatedAt(reviewRequest.getCreatedAt());
            existingReview.setScore(reviewRequest.getScore());

            Optional<Review> accountOptional = reviewRepository.findById(reviewRequest.getAccountId());
            if (accountOptional.isPresent()) {
                existingReview.setAccount(accountOptional.get().getAccount());
            } else {
                throw new RuntimeException("Account not found with id: " + reviewRequest.getAccountId());
            }
            return reviewRepository.save(existingReview);
        } else {
            throw new RuntimeException("Review not found with id: " + id);
        }
    }

    public void deleteReview(long id) {
        Optional<Review> existingReviewOptional = reviewRepository.findById(id);
        if (existingReviewOptional.isPresent()) {
            reviewRepository.deleteById(id);
        } else {
            throw new RuntimeException("Review not found with id: " + id);
        }
    }

}
