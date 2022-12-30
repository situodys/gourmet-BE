package kw.ic.backend.domain.review.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.service.ReviewedMenuService;
import kw.ic.backend.domain.review.dto.ReviewResponseAssembler;
import kw.ic.backend.domain.review.dto.request.ReviewPageRequest;
import kw.ic.backend.domain.review.dto.request.ReviewRequest;
import kw.ic.backend.domain.review.dto.response.ReviewPageResponse;
import kw.ic.backend.domain.review.dto.response.ReviewResponse;
import kw.ic.backend.domain.review.dto.response.SimpleReviewResponse;
import kw.ic.backend.domain.review.entity.Review;
import kw.ic.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewedMenuService reviewedMenuService;
    private final ReviewRepository reviewRepository;
    private final ReviewResponseAssembler responseAssembler;

    public ReviewPageResponse findReviews(ReviewPageRequest request) {
        List<SimpleReviewResponse> result = reviewRepository.findReviewsWithPagination(request)
                .stream()
                .map(review -> responseAssembler.simpleReviewResponse(review))
                .collect(Collectors.toUnmodifiableList());

        return responseAssembler.reviewPageResponse(result);
    }

    public ReviewResponse findReview(Long reviewId) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(IllegalArgumentException::new);

        return responseAssembler.reviewResponse(review);
    }

    public Long register(ReviewRequest request) {
        final Review review = reviewRepository.save(request.toReview());

        reviewedMenuService.registerAll(request.getSimpleMenus(),review);

        return review.getId();
    }

    public Long deleteById(Long reviewId) {

        reviewRepository.deleteById(reviewId);

        return reviewId;
    }
}
