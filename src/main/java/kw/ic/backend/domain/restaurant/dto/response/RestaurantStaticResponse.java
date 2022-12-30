package kw.ic.backend.domain.restaurant.dto.response;

import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantStaticResponse {

    private SimpleRestaurantResponse simpleRestaurantResponse;
    private Long likeCount;
    private Double rating;
    private Long reviewCount;

    @Builder
    public RestaurantStaticResponse(SimpleRestaurantResponse simpleRestaurantResponse, Long likeCount, Double rating,
                                    Long reviewCount) {
        this.simpleRestaurantResponse = simpleRestaurantResponse;
        this.likeCount = likeCount;
        this.rating =rating;
        this.reviewCount = reviewCount;
    }
}
