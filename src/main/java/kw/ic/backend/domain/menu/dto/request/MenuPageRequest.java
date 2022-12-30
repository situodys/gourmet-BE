package kw.ic.backend.domain.menu.dto.request;

import java.util.List;
import kw.ic.backend.global.dto.BasePageRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class MenuPageRequest extends BasePageRequest {

    private Long restaurantId;

    private String menuName;

    private Integer price;

    @Builder
    public MenuPageRequest(Integer page, Integer size, Sort sort, Long totalCount,
                           Long restaurantId, String menuName, Integer price) {
        super(page, size, sort, totalCount);
        this.restaurantId = restaurantId;
        this.menuName = menuName;
        this.price = price;
    }

}
