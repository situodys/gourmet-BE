package kw.ic.backend.domain.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantReviewRatingResponse {

    private SimpleRestaurantResponse simpleRestaurantResponse;
    private Double rating;
    private long reviewCount;

    @Builder
    public RestaurantReviewRatingResponse(
            SimpleRestaurantResponse simpleRestaurantResponse, Double rating, long reviewCount) {
        this.simpleRestaurantResponse = simpleRestaurantResponse;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}
