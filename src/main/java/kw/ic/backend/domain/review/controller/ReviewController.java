package kw.ic.backend.domain.review.controller;

import kw.ic.backend.domain.review.dto.request.ReviewPageRequest;
import kw.ic.backend.domain.review.dto.request.ReviewRequest;
import kw.ic.backend.domain.review.dto.response.ReviewPageResponse;
import kw.ic.backend.domain.review.dto.response.ReviewResponse;
import kw.ic.backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    ResponseEntity<ReviewPageResponse> findReviews(ReviewPageRequest request) {
        log.info("find reviews of restaurantId: {} with pagination", request);

        ReviewPageResponse response = reviewService.findReviews(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{review_id}")
    ResponseEntity<ReviewResponse> findReview(@PathVariable(name="review_id") Long reviewId) {
        log.info("find review by id: {}", reviewId);

        ReviewResponse response = reviewService.findReview(reviewId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    ResponseEntity<Long> register(@RequestBody ReviewRequest request) {
        log.info("register review of restaurantId: {}", request);

        Long reviewId = reviewService.register(request);

        return ResponseEntity.ok(reviewId);
    }

    @DeleteMapping("/{review_id}")
    ResponseEntity<Long> delete(@PathVariable(name = "review_id") Long reviewId) {
        log.info("delete reviewId: {}",reviewId);

        Long deletedId = reviewService.deleteById(reviewId);

        return ResponseEntity.ok(deletedId);
    }
}
