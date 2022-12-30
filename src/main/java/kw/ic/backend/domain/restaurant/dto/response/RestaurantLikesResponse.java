package kw.ic.backend.domain.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantLikesResponse {

    private SimpleRestaurantResponse simpleRestaurantResponse;
    private Long likeCount;

    @Builder
    public RestaurantLikesResponse(
            SimpleRestaurantResponse simpleRestaurantResponse, Long likeCount) {
        this.simpleRestaurantResponse = simpleRestaurantResponse;
        this.likeCount = likeCount;
    }
}
