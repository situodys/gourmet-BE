package kw.ic.backend.domain.notification.dto.response;

import java.util.List;
import kw.ic.backend.global.dto.BasePageResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class NotificationPageResponse extends BasePageResponse {

    private List<NotificationResponse> notifications;

    public NotificationPageResponse(Page result,
                                    List<NotificationResponse> notifications) {
        super(result);
        this.notifications = notifications;
    }
}
