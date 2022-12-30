package kw.ic.backend.notification.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.notification.dto.request.NotificationRequest;
import kw.ic.backend.domain.notification.repository.NotificationRepository;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import kw.ic.backend.global.config.JpaConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    private void init() {
        restaurantRepository.save(Restaurant.builder().type(RestaurantType.KOREAN).build());
    }

    @Test
    @DisplayName("알림 정상 등록 확인 ")
    public void register() throws Exception {
        //given
        NotificationRequest notificationRequest = new NotificationRequest("previousContent", "updatedContent", 1L);
        //when
        Notification notification = notificationRepository.save(notificationRequest.toNotification());
        //then
        assertThat(notification.getId()).isEqualTo(notification.getId());
    }

    @Test
    @DisplayName("알림 정상 삭제 확인 ")
    public void delete() throws Exception {
        //given
        NotificationRequest notificationRequest = new NotificationRequest("previousContent", "updatedContent", 1L);
        //when
        Notification notification = notificationRepository.save(notificationRequest.toNotification());
        notificationRepository.deleteById(notification.getId());

        Optional<Notification> result = notificationRepository.findById(notification.getId());
        //then
        assertThat(result.isPresent()).isEqualTo(false);
    }


}
