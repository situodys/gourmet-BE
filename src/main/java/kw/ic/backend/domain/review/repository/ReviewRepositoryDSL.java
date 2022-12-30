package kw.ic.backend.domain.review.repository;

import java.util.List;
import kw.ic.backend.domain.review.dto.request.ReviewPageRequest;
import kw.ic.backend.domain.review.entity.Review;

public interface ReviewRepositoryDSL {

    List<Review> findReviewsWithPagination(ReviewPageRequest request);
}
