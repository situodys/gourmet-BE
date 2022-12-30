package kw.ic.backend.domain.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
public class NotificationResponse {

    private Long id;

    private Category category;

    private String previousContent;

    private String updatedContent;

    @JsonFormat(pattern = "YYYY-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Long restaurant_id;

    @Builder
    public NotificationResponse(Long id, Category category ,String previousContent, String updatedContent, LocalDateTime createdAt, Long restaurant_id) {
        Assert.hasText(updatedContent,"updated content must not be empty");

        this.id = id;
        this.category = category;
        this.previousContent = previousContent;
        this.updatedContent = updatedContent;
        this.createdAt = createdAt;
        this.restaurant_id = restaurant_id;
    }
}
