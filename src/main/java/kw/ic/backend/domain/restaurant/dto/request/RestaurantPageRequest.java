package kw.ic.backend.domain.restaurant.dto.request;

import java.util.List;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import lombok.Setter;

@Setter
public class RestaurantPageRequest {

    private Integer size;

    private List<String> sorts;

    private String name;

    private RestaurantType restaurantType;

    private Long lastId;


    public RestaurantPageRequest(Integer size, List<String> sorts, String name, RestaurantType restaurantType,
                                 Long lastId) {
        this.size = size;
        this.sorts = sorts;
        this.name = name;
        this.restaurantType = restaurantType;
        this.lastId = lastId;
    }

    public Integer getSize() {
        return size == null ? 9:size;
    }

    public List<String> getSorts() {
        return sorts;
    }

    public String getName() {
        return name;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public Long getLastId() {
        return lastId;
    }
}
