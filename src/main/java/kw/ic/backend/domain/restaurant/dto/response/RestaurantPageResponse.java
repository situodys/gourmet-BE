package kw.ic.backend.domain.restaurant.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantPageResponse {

    private List<RestaurantStaticResponse> data;

    private Long lastId;
}
