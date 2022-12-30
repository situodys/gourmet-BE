package kw.ic.backend.domain.notification.dto.request;

import javax.validation.constraints.Min;
import kw.ic.backend.global.dto.BasePageRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class NotificationPageRequest extends BasePageRequest{

    private Long restaurantId;

    public NotificationPageRequest(@Min(value = 0L) Integer page,
                                   @Min(value = 1L) Integer size,
                                   Sort sort, Long totalCount, Long restaurantId) {
        super(page, size, sort, totalCount);
        this.restaurantId = restaurantId;
    }
}
