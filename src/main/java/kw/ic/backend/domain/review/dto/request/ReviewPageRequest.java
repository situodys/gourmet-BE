package kw.ic.backend.domain.review.dto.request;

import java.util.List;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Setter
public class ReviewPageRequest {

    private Long restaurantId;

    private Integer size;

    private Sort sort;

    private List<Long> menuIds;

    private Long lastId;

    public ReviewPageRequest(Long restaurantId, Integer size, Sort sort, List<Long> menuIds, Long lastId) {
        this.restaurantId = restaurantId;
        this.size = size;
        this.sort = sort;
        this.menuIds = menuIds;
        this.lastId = lastId;
    }

    public Long getRestaurantId() {
        return this.restaurantId;
    }

    public Integer getSize() {
        return size == null ? 9 : size;
    }

    public Sort getSort() {

        return sort ==null ? Sort.by(Direction.DESC, "id") : sort;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public Long getLastId() {
        return lastId;
    }
}
