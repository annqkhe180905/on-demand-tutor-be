package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Review;
import online.ondemandtutor.be.model.request.ReviewRequest;
import online.ondemandtutor.be.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class ReviewAPI {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createReview(reviewRequest);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // Method to get a review by review_id
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // Method to get reviews by tutor_id
    @GetMapping("/reviews/tutor/{tutorId}")
    public ResponseEntity<List<Review>> getReviewsByTutorId(@PathVariable Long tutorId) {
        List<Review> reviews = reviewService.getReviewByTutorId(tutorId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
        // noContent().build() trả về status 204. nghĩa là đã xóa và ko trả về thứ gì
    }
}
