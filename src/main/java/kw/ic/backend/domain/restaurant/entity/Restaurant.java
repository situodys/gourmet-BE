package kw.ic.backend.domain.restaurant.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import kw.ic.backend.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantType type;

    @Embedded
    private Address address;

    @Embedded
    private RunningTime runningTime;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Notification> notifications = new ArrayList<>();

    @Builder
    public Restaurant(String name, String description, RestaurantType type, Address address, RunningTime runningTime,
                      List<Menu> menus, List<Notification> notifications) {
        Assert.notNull(type, "Restaurant type must not be empty");

        this.name = name;
        this.description = description;
        this.type = type;
        this.address = address;
        this.runningTime = runningTime;
        this.menus = menus;
        this.notifications = notifications;
    }

    public Restaurant(Long id) {
        this.id = id;
    }

    public void changeOpenAt(String[] hourAndMinute) {
        LocalDateTime updatedOpenAt = LocalDateTime.now()
                .withHour(Integer.parseInt(hourAndMinute[0]))
                .withMinute(Integer.parseInt(hourAndMinute[1]));
        this.runningTime.changeOpenAt(updatedOpenAt);
    }

    public void changeCloseAt(String[] hourAndMinute) {
        LocalDateTime updatedCloseAt = LocalDateTime.now()
                .withHour(Integer.parseInt(hourAndMinute[0]))
                .withMinute(Integer.parseInt(hourAndMinute[1]));
        this.runningTime.changeCloseAt(updatedCloseAt);
    }
}
