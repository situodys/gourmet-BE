package kw.ic.backend.domain.notification.controller;

import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.notification.dto.request.NotificationPageRequest;
import kw.ic.backend.domain.notification.dto.request.NotificationRequest;
import kw.ic.backend.domain.notification.dto.response.NotificationPageResponse;
import kw.ic.backend.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<NotificationPageResponse> findNotificationsByRestaurant(
            NotificationPageRequest request) {
        log.info("find notifications of restaurantId: {} with pagination", request);

        NotificationPageResponse notificationPageResponse = notificationService.findNotifications(request);

        return ResponseEntity.ok(notificationPageResponse);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody NotificationRequest request) {
        log.info("Register notification of restaurant id: {}", request.getRestaurantId());

        Long notificationId = notificationService.register(request);

        return ResponseEntity.ok(notificationId);
    }

    @DeleteMapping("/{notification_id}")
    ResponseEntity<Long> delete(@PathVariable(name = "notification_id") Long notificationId) {
        log.info("delete notificationId");

        Long deletedId = notificationService.delete(notificationId);

        return ResponseEntity.ok(deletedId);
    }


}
