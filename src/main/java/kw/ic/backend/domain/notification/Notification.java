package kw.ic.backend.domain.notification;

import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.notification.dto.response.NotificationResponse;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

@Entity(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column
    private Category category;

    @Column(name = "previous_content")
    private String previousContent;

    @Column(name = "updated_content")
    private String updatedContent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Builder
    public Notification(Category category, String previousContent, String updatedContent, Restaurant restaurant) {
        Assert.hasText(updatedContent,"updated content must not be empty");

        this.category = category;
        this.previousContent = previousContent;
        this.updatedContent = updatedContent;
        this.restaurant = restaurant;
    }

    public NotificationResponse toResponse() {
        return NotificationResponse.builder()
                .id(this.id)
                .category(this.category)
                .previousContent(this.previousContent)
                .updatedContent(this.updatedContent)
                .restaurant_id(this.restaurant.getId())
                .build();
    }
}
