package kw.ic.backend.domain.notification.repository;

import kw.ic.backend.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    //@Query
    public Page<Notification> findAllByRestaurantId(Long restaurantId, Pageable pageable);
}
