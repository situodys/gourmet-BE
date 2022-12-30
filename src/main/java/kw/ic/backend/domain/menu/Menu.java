package kw.ic.backend.domain.menu;

import kw.ic.backend.domain.menu.dto.response.MenuResponse;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Builder
    public Menu(String menuName, Integer price, String description, Restaurant restaurant) {
        Assert.hasText(menuName, "menuName must not be empty");
        Assert.notNull(price,"price must not be empty");

        this.menuName = menuName;
        this.price=price;
        this.description = description;
        this.restaurant = restaurant;
    }

    public void changeMenuName(String menuName){
        this.menuName = menuName;
    }

    public void changePrice(String price) {
        this.price = Integer.parseInt(price);
    }
}
