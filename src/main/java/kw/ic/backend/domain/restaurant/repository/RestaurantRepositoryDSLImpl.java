package kw.ic.backend.domain.restaurant.repository;

import static kw.ic.backend.domain.likes.QLikes.likes;
import static kw.ic.backend.domain.menu.QMenu.menu;
import static kw.ic.backend.domain.restaurant.entity.QRestaurant.restaurant;
import static kw.ic.backend.domain.review.entity.QReview.review;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantLikes;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantReviewRating;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantStatic;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantPageRequest;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantLikesResponse;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryDSLImpl implements RestaurantRepositoryDSL {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RestaurantStatic> findRestaurantsWithPagination(RestaurantPageRequest request) {

        List<RestaurantStatic> result = jpaQueryFactory.select(Projections.constructor(
                        RestaurantStatic.class,
                        restaurant,
                        likes.countDistinct(),
                        review.rating.avg().coalesce(0.0),
                        review.countDistinct()
                ))
                .from(restaurant)
                .leftJoin(likes).on(likes.restaurant.eq(restaurant))
                .leftJoin(review).on(review.restaurant.eq(restaurant))
                .where(
                        ltRestaurantId(request.getLastId()),
                        containsName(request.getName()),
                        eqType(request.getRestaurantType())
                )
                .orderBy(restaurant.id.desc())
                .limit(request.getSize())
                .groupBy(restaurant)
                .fetch();

        return result;
    }

    public List<RestaurantLikes> findRestaurantsByMostLikesLimit5() {
        List<RestaurantLikes> result = jpaQueryFactory.select(Projections.constructor(
                        RestaurantLikes.class,
                        restaurant,
                        likes.countDistinct()
                ))
                .from(restaurant)
                .leftJoin(likes).on(likes.restaurant.eq(restaurant))
                .orderBy(likes.countDistinct().desc())
                .limit(5)
                .groupBy(restaurant)
                .fetch();

        return result;
    }

    public List<RestaurantReviewRating> findRestaurantsByMostMostReviewRatingLimit5() {
        List<RestaurantReviewRating> result = jpaQueryFactory.select(Projections.constructor(
                        RestaurantReviewRating.class,
                        restaurant,
                        review.rating.avg().coalesce(0.0),
                        review.countDistinct()
                ))
                .from(restaurant)
                .leftJoin(review).on(review.restaurant.eq(restaurant))
                .having(review.countDistinct().goe(10))
                .orderBy(review.rating.avg().coalesce(0.0).desc())
                .limit(5)
                .groupBy(restaurant)
                .fetch();

        return result;
    }

    public RestaurantStatic findRestaurantByRestaurantId(Long restaurantId) {
        RestaurantStatic result = jpaQueryFactory.select(Projections.constructor(
                        RestaurantStatic.class,
                        restaurant,
                        likes.countDistinct(),
                        review.rating.avg().coalesce(0.0),
                        review.countDistinct()
                ))
                .from(restaurant)
                .leftJoin(likes).on(likes.restaurant.eq(restaurant))
                .leftJoin(review).on(review.restaurant.eq(restaurant))
                .where(
                        restaurant.id.eq(restaurantId)
                )
                .groupBy(restaurant)
                .fetchOne();
        return result;
    }


    private BooleanExpression ltRestaurantId(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return restaurant.id.lt(lastId);
    }

    private BooleanExpression containsName(String restaurantName) {
        if (StringUtils.isNullOrEmpty(restaurantName)) {
            return null;
        }
        return restaurant.name.contains(restaurantName);
    }

    private BooleanExpression eqType(RestaurantType type) {
        if (type == null) {
            return null;
        }
        return restaurant.type.eq(type);
    }
}
