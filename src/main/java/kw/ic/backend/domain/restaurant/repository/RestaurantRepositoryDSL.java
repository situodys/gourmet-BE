package kw.ic.backend.domain.restaurant.repository;

import java.util.List;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantLikes;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantReviewRating;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantStatic;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantPageRequest;
import kw.ic.backend.domain.restaurant.entity.Restaurant;

public interface RestaurantRepositoryDSL {

    List<RestaurantStatic> findRestaurantsWithPagination(RestaurantPageRequest request);

    List<RestaurantLikes> findRestaurantsByMostLikesLimit5();

    List<RestaurantReviewRating> findRestaurantsByMostMostReviewRatingLimit5();

    RestaurantStatic findRestaurantByRestaurantId(Long restaurantId);
}


