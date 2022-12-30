package kw.ic.backend.domain.restaurant.dto.projection;

import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class RestaurantReviewRating {

    private Restaurant restaurant;
    private Double rating;
    private long reviewCount;

    @Builder
    public RestaurantReviewRating(Restaurant restaurant, Double rating, long reviewCount) {
        this.restaurant = restaurant;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}
