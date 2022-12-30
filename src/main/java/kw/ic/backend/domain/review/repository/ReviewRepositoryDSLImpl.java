package kw.ic.backend.domain.review.repository;

import static kw.ic.backend.domain.member.QMember.member;
import static kw.ic.backend.domain.menu.QReviewedMenu.reviewedMenu;
import static kw.ic.backend.domain.review.entity.QReview.review;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.ReviewedMenu;
import kw.ic.backend.domain.review.dto.request.ReviewPageRequest;
import kw.ic.backend.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryDSLImpl extends QuerydslRepositorySupport implements ReviewRepositoryDSL {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositoryDSLImpl(JPAQueryFactory jpaQueryFactory) {
        super(Review.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Review> findReviewsWithPagination(ReviewPageRequest request) {

        JPAQuery<Long> idQuery = jpaQueryFactory.selectDistinct(review.id)
                .from(review)
                .leftJoin(review.reviewedMenus,reviewedMenu)
                .where(
                        ltReviewId(request.getLastId()),
                        review.restaurant.id.eq(request.getRestaurantId()),
                        isMenuReviewed(request.getMenuIds())
                )
                .limit(request.getSize());

        List<Long> idResult = getQuerydsl().applySorting(request.getSort(), idQuery).fetch();

        if (idResult.isEmpty() || idResult == null) {
            return List.of();
        }

        List<Review> result = jpaQueryFactory.selectDistinct(review)
                .from(review)
                .leftJoin(review.reviewedMenus, reviewedMenu)
                .fetchJoin()
                .leftJoin(review.member, member)
                .fetchJoin()
                .where(
                        review.id.in(idResult)
                )
                .orderBy(review.id.desc())
                .fetch();

        return result;
    }

    private BooleanExpression ltReviewId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return review.id.lt(lastId);
    }

    private BooleanExpression isMenuReviewed(List<Long> menuIds) {
        if (menuIds == null) {
            return null;
        }
        return reviewedMenu.menu.id.in(menuIds);
    }
}
