package online.ondemandtutor.be.api;

import online.ondemandtutor.be.entity.Review;
import online.ondemandtutor.be.model.ReviewRequest;
import online.ondemandtutor.be.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewAPI {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createReview(reviewRequest);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@RequestBody ReviewRequest reviewRequest) {
        List<Review> reviews = reviewService.getAllReviews(reviewRequest);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getById(id);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
        // noContent().build() trả về status 204. nghĩa là đã xóa và ko trả về thứ gì
    }
}
