package kw.ic.backend.domain.restaurant.dto.projection;

import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantStatic {

    private Restaurant restaurant;
    private long likeCount;
    private Double rating;
    private long reviewCount;

    @Builder
    public RestaurantStatic(Restaurant restaurant, Long likeCount, Double rating, Long reviewCount) {
        this.restaurant = restaurant;
        this.likeCount = likeCount;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}
