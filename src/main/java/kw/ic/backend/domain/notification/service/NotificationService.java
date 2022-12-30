package kw.ic.backend.domain.notification.service;

import java.util.stream.Collectors;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.notification.dto.request.NotificationPageRequest;
import kw.ic.backend.domain.notification.dto.request.NotificationRequest;
import kw.ic.backend.domain.notification.dto.response.NotificationPageResponse;
import kw.ic.backend.domain.notification.repository.NotificationRepository;
import kw.ic.backend.domain.notification.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationPageResponse findNotifications(NotificationPageRequest request) {
        System.out.println(request.getPageRequest());

        Page<Notification> result = notificationRepository.findAllByRestaurantId(request.getRestaurantId(), request.getPageRequest());

        return new NotificationPageResponse(result, result.getContent().stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .category(notification.getCategory())
                        .updatedContent(notification.getUpdatedContent())
                        .previousContent(notification.getPreviousContent())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .collect(Collectors.toUnmodifiableList()));
    }

    public Long register(NotificationRequest request) {
        Notification notification = notificationRepository.save(request.toNotification());

        return notification.getId();
    }

    public Long delete(Long notificationId) {
        notificationRepository.deleteById(notificationId);

        return notificationId;
    }
}
