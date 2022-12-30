package kw.ic.backend.domain.restaurant.dto.projection;

import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantLikes {

    private Restaurant restaurant;
    private Long likeCount;

    @Builder
    public RestaurantLikes(Restaurant restaurant, Long likeCount) {
        this.restaurant = restaurant;
        this.likeCount = likeCount;
    }
}
