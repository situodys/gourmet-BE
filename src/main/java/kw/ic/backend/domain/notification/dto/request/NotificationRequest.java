package kw.ic.backend.domain.notification.dto.request;

import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String previousContent;

    @NotNull
    private String updatedContent;

    @NotNull
    private Long restaurantId;

    public Notification toNotification() {
        return Notification.builder()
                .previousContent(this.previousContent)
                .updatedContent(this.updatedContent)
                .restaurant(new Restaurant(restaurantId))
                .build();
    }
}
