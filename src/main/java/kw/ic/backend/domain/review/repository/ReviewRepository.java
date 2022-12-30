package kw.ic.backend.domain.review.repository;

import kw.ic.backend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryDSL {
}
